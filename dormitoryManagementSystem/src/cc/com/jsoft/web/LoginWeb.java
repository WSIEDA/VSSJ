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
		 * ������ǻ���shiro����֤����
		 */
		// stup 1. ��ȡ����
		Subject subject = SecurityUtils.getSubject();
		// stup 2. ��ȡUsernamePasswordToken  ��ȡtoken
		UsernamePasswordToken shiroToken = new UsernamePasswordToken(username,pwd);
		// stup 3. ִ�е�¼  �������ת��MyRealm��  �û���֤�ķ����� doGetAuthenticationInfo
		subject.login(shiroToken);
		
		/*
		 * ������ǻ���JWT�Ļ��� token���û���֤����
		 */
		// ��֤�û���¼��Ϣ
		UserPO list = loginService.doLogin(username,pwd);
		String token = null;
		if (list != null) {
			// ����JWT
			try {
				// ��JSON�ַ���תΪString���͵�json��     jsonstr������ǽ��ܵ�sub
				JSONArray array = JSONArray.fromObject(list);
			    String jsonstr = array.toString();
				token = JWT.createJWT("jwt", jsonstr, 60000);
//				result.success(token);
//				HttpSession session = request.getSession();
//				session.setAttribute("Authorization", token);
				// session�Ự������ʱ��  3600��һ��Сʱ
//				session.setMaxInactiveInterval(3600);
				// ����
				/**
				 * ���ܴ�ӡ����� 
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
			return result.error("�����µ�¼");
		}
		
	}
	
	/**
	 * 	��ȡToken���Զ����TOKEN�ڵ�¼֮ǰ��ȡ�����Է���URL������cookie+session���ƣ�������ܿ���ʵ�֣� 
	 * 	JWT����token����֤���ƣ��ڵ�¼֮�󣬻�ȡ���û���Ϣ֮������JWT-TOKEN��Ϣ�������Header����ͷ�У�token�����ڿ������⣩
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
