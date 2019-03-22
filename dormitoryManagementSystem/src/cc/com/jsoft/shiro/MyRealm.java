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
	 * �û���Ȩ����
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// stup 1. ��ȡ��¼���û���
		Object principal = principalCollection.getPrimaryPrincipal();
		
		// stup 2. �򵥵��ж��û���Ȩ����Ϣ
		if("admin".equals(principal)) {
			info.addRole("admin");
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * �û���֤����
	 * ��һ���������⣺Authentication failed for token submission  ��ȡ�����������ݿ��е����벻һ�£� һ������һ��δ���ܣ�
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// TODO Auto-generated method stub
		// ���Ŀǰ�һ����������ôת���ľ����������ʲô������
		UsernamePasswordToken usertoken = (UsernamePasswordToken)token;
		// stup1. token�л�ȡ�û���¼��username
		String principal = (String) token.getPrincipal();
		
		// stup2. ���û�ȡ��username��ѯ���ݿ��е� �û�����Ϣ
		//��������Ը���ʵ����������棬���������Shiro�Լ�Ҳ����ʱ�������ƣ�2�����ڲ����ظ�ִ�и÷�����
		UserPO userPO = loginService.doAuthenticationInfoLogin(principal);
		if (userPO == null) {
			try {
				throw new IsNOTExistUsername("�û��������ڣ�");
			} catch (IsNOTExistUsername e) {
				e.printStackTrace();
			}
		}
		String password = userPO.getPassword();
		
		// stup4. ��ȡ��ǰrealm��name
		String realmName = getName();
		
		// stup5. ����ֵ��ʵ����
		/**
		 * java.lang.IllegalArgumentException: Odd number of characters.����쳣����  ��Ϊ�ҽ���������ķ�����SimpleAuthenticationInfo����
		 */
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(
						principal,  		// �û���
						password,		// ����
						ByteSource.Util.bytes(userPO.getSalt()),	// ����
						realmName			// realm������
						);
		return info;
	}

	
	/**
	 * realm�ĳ�ʼ�����ط���
	 * init-method ����.     
	 *  ��Ӧ��applicationContext-shiro.xml�е�����realm�� ע��� init-method��Ӧ��
	 *  
	 *  �������ˣ� ��ע��ʱ���Ҷ���������˽��ܲ���������ʹ��ͬ���Ĺ�����еļ���
	 *  ��shiro��SimpleAuthenticationInfo�����У���������ͬ���Ĺ������������˼��ܣ�����shiro����ô���������Ƿ�һ�µ��أ�
	 *  
	 *  ����ƥ��ͨ��HashedCredentialsMatcher���
	 *  doCredentialsMatch()����  ������������ AuthenticationToken token, AuthenticationInfo info
	 *  ����һ�δ������������֤����
	 *
	 */
	public void setCredentialMatcher () {
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("MD5"); // MD5�㷨����
		hcm.setHashIterations(1024);     // 1024��ѭ������
		hcm.setStoredCredentialsHexEncoded(true);   // �Ƿ�ɢ��
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
