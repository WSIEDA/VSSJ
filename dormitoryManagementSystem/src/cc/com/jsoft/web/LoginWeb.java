package cc.com.jsoft.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.com.jsoft.pojo.ResponseManager;
import cc.com.jsoft.pojo.PO.UserPO;
import cc.com.jsoft.service.LoginService;
import cc.com.jsoft.utils.JWT;
import cc.com.jsoft.utils.TokenUtil;
import freemarker.template.utility.SecurityUtilities;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONArray;

/**
 * @author Administrator
 *
 */
@Controller
public class LoginWeb {
	
	private static HttpServletRequest request;
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * TODO
	 * @{param}
	 * @{return}
	 * @author zhouhongwei
	 */
	/*@RequestMapping(value = "/doLogin.action", method = RequestMethod.POST)
	@ResponseBody
	public Map<?, ?> login() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("1", "hello world");
		return map;
	}*/
	
	
	/**
	 * TODO
	 * @{param}
	 * @{return}
	 * @author zhouhongwei
	 */
	@RequestMapping(value = "/doLogin.action", method = RequestMethod.POST)
	@SuppressWarnings({ "unchecked", "static-access", "finally" })
	@ResponseBody
	public Map<String,Object> doLogin (@RequestBody String username, 
			@RequestBody String pwd) {
		ResponseManager result = new ResponseManager();
		/*
		 * 下面的是基于shiro的认证过程
		 */
		// stup 1. 获取主体
		Subject subject = SecurityUtils.getSubject();
		// stup 2. 获取UsernamePasswordToken  获取token
		UsernamePasswordToken shiroToken = new UsernamePasswordToken(username,pwd);
		// stup 3. 执行登录  这里会跳转到MyRealm的  用户认证的方法中 doGetAuthenticationInfo
		subject.login(shiroToken);
		
		/*
		 * 下面的是基于JWT的基础 token的用户认证机制
		 */
		// 验证用户登录信息
		UserPO list = loginService.doLogin(username,pwd);
		String token = null;
		if (list != null) {
			// 生成JWT
			try {
				// 将JSON字符串转为String类型的json串     jsonstr这个就是解密的sub
				JSONArray array = JSONArray.fromObject(list);
			    String jsonstr = array.toString();
				token = JWT.createJWT("jwt", jsonstr, 60000);
//				result.success(token);
//				HttpSession session = request.getSession();
//				session.setAttribute("Authorization", token);
				// session会话持续的时间  3600：一个小时
//				session.setMaxInactiveInterval(3600);
				// 解密
				/**
				 * 解密打印结果： 
				 * {uid=DSSFAWDWADAS..., 
				 * sub=[{"password":"123","leval":"1","id":"1","username":"king"}], 
				 * user_name=admin, nick_name=DASDA121, exp=1552823884, iat=1552823824, jti=jwt}
				 */
				Claims parseJWT = JWT.parseJWT(token);
				System.out.println(parseJWT);
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				System.out.println("========="+token);
				return  result.success(token);
			}
		}else {
			return result.error("请重新登录");
		}
		
	}
	
	/**
	 * 	获取Token，自定义的TOKEN在登录之前获取，可以放在URL，基于cookie+session机制（这个不能跨域实现） 
	 * 	JWT——token的认证机制，在登录之后，获取到用户信息之后，生成JWT-TOKEN信息，存放在Header请求头中（token不存在跨域问题）
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/doToken.action", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doToken(HttpServletRequest request) {
		ResponseManager result = new ResponseManager();
		String token = TokenUtil.getInstance().makeToken();
		request.getSession().setAttribute("Authorization", token);
		return result.success(token);
	}

}
