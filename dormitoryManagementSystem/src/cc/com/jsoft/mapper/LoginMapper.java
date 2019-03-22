package cc.com.jsoft.mapper;

import org.apache.ibatis.annotations.Param;

import cc.com.jsoft.pojo.PO.UserPO;

public interface LoginMapper {

	public UserPO doLogin (@Param("username") String username, @Param("pwd") String pwd);
}
