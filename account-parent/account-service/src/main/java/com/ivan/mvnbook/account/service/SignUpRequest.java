package com.ivan.mvnbook.account.service;


public class SignUpRequest {
	
	private String id;
	
	private String email;
	
	private String username;
	
	private String password;
	
	private String captchaKey;
	
	private String captchaValue;
	
	private String confirmPassword;
	
	private String activateServcieUrl;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getCaptchaKey() {
		return captchaKey;
	}

	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}

	public String getCaptchaValue() {
		return captchaValue;
	}

	public void setCaptchaValue(String captchaValue) {
		this.captchaValue = captchaValue;
	}
	
	public void setConfirmPassword(String confirmPassword){
		this.confirmPassword = confirmPassword;
	}
	
	public String getConfirmPassword(){
		return confirmPassword;
	}

	public String getActivateServcieUrl() {
		return activateServcieUrl;
	}

	public void setActivateServcieUrl(String activateServcieUrl) {
		this.activateServcieUrl = activateServcieUrl;
	}
	
	
}
