package com.ivan.mvnbook.account.service;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
	
	private Integer id;
	
	private String email;
	
	private String username;
	
	private String password;
	
	private String captchaKey;
	
	private String captchaValue;
}
