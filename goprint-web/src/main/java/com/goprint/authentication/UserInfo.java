package com.goprint.authentication;

public class UserInfo {
	
	public UserInfo(Long id,String userName){
		this.id = id;
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String userName;
	private Long id;
	

}
