package cc.com.jsoft.pojo.PO;

import java.io.Serializable;

public class UserPO implements Serializable {
	
	/**
	 *  ���л�
	 */
	private static final long serialVersionUID = -6347388645163298668L;
	
	private String id;
	private String username;
	private String password;
	private String leval;
	private String salt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLeval() {
		return leval;
	}
	public void setLeval(String leval) {
		this.leval = leval;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	

}
