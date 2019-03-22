package cc.com.jsoft.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cc.com.jsoft.pojo.PO.UserPO;
import cc.com.jsoft.service.LoginService;
import cc.com.jsoft.utils.MdTools;
import cc.com.jsoft.utils.exception.IsNOTExistUsername;

public class MyRealm extends AuthorizingRealm{

	UserPO userPO = new UserPO();
	
	@Autowired
	private LoginService loginService;
	
	/* (non-Javadoc)
	 * 用户授权方法
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// stup 1. 获取登录的用户名
		Object principal = principalCollection.getPrimaryPrincipal();
		
		// stup 2. 简单的判断用户的权限信息
		if("admin".equals(principal)) {
			info.addRole("admin");
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * 用户认证方法
	 * 有一个报错问题：Authentication failed for token submission  获取的密码与数据库中的密码不一致（ 一个加密一个未加密）
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		// 这个目前我还不清楚，这么转换的具体的作用是什么？？？
		UsernamePasswordToken usertoken = (UsernamePasswordToken)token;
		// stup1. token中获取用户登录的username
		String principal = (String) token.getPrincipal();
		
		// stup2. 利用获取的username查询数据库中的 用户的信息
		//（这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法）
		UserPO userPO = loginService.doAuthenticationInfoLogin(principal);
		if (userPO == null) {
			try {
				throw new IsNOTExistUsername("用户名不存在！");
			} catch (IsNOTExistUsername e) {
				e.printStackTrace();
			}
		}
		String password = userPO.getPassword();
		
		// stup4. 获取当前realm的name
		String realmName = getName();
		
		// stup5. 返回值的实例化
		/**
		 * java.lang.IllegalArgumentException: Odd number of characters.这个异常错误  因为我将密码的明文放在了SimpleAuthenticationInfo中了
		 */
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(
						principal,  		// 用户名
						password,		// 密码
						ByteSource.Util.bytes(userPO.getSalt()),	// 加盐
						realmName			// realm的名称
						);
		return info;
	}

	
	/**
	 * realm的初始化加载方法
	 * init-method 配置.     
	 *  对应的applicationContext-shiro.xml中的配置realm中 注册的 init-method对应的
	 *  
	 *  问题来了： 在注册时候，我对密码进行了解密操作，并且使用同样的规则进行的加密
	 *  在shiro的SimpleAuthenticationInfo对象中，我是用了同样的规则对密码进行了加密，但是shiro是怎么处理密码是否一致的呢？
	 *  
	 *  密码匹配通过HashedCredentialsMatcher类的
	 *  doCredentialsMatch()方法  有两个参数： AuthenticationToken token, AuthenticationInfo info
	 *  下面一段代码解释密码验证过程
	 *
	 */
	public void setCredentialMatcher () {
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("MD5"); // MD5算法加密
		hcm.setHashIterations(1024);     // 1024次循环加密
		hcm.setStoredCredentialsHexEncoded(true);   // 是否散列
		setCredentialsMatcher(hcm);
	}
	
	/*
	 * public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) { 
         Object tokenHashedCredentials = hashProvidedCredentials(token, info);  
         Object accountCredentials = getCredentials(info); 
        return equals(tokenHashedCredentials, accountCredentials);
       } 
    */
}
