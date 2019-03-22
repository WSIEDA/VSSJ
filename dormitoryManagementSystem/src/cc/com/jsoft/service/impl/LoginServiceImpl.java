package cc.com.jsoft.service.impl;

import org.springframework.stereotype.Service;

import cc.com.jsoft.pojo.PO.UserPO;
import cc.com.jsoft.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Override
	public UserPO doLogin(String username, String pwd) {
		UserPO user = new UserPO();
		user.setId("1");
		user.setLeval("2");
		user.setPassword("123");
		user.setUsername("king");
		return user;
	}

	
	@Override
	public UserPO doAuthenticationInfoLogin(String principal) {
		// TODO Auto-generated method stub
		return null;
	}

}
