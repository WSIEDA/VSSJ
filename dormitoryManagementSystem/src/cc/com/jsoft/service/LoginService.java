package cc.com.jsoft.service;


import cc.com.jsoft.pojo.PO.UserPO;

public interface LoginService {

	UserPO doLogin(String username, String pwd);
	
	/**
	 * shiro��֤�ĵ�½����
	 * @param principal
	 * @return
	 */
	UserPO doAuthenticationInfoLogin (String principal);

}
