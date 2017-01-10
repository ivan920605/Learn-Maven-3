<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import ="com.ivan.mvnbook.account.service.*" %>
<%@ page import ="org.springframework.context.ApplicationContext,
	org.springframework.web.context.support.WebApplicationContextUtils,
	com.ivan.mvnbook.account.captcha.*,
	com.ivan.mvnbook.account.email.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% 
	ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
	AccountService accountService = (AccountService) context.getBean("accountService");
	AccountCaptchaService accountCaptchaService = (AccountCaptchaService) context.getBean("accountCaptchaService");
	AccountEmailService accountEmailService = (AccountEmailService) context.getBean("accountEmailService");
	
	String captchaKey = accountService.generateCaptchaKey();
	%>
	
	<div>
		<h2>注册新用户</h2>
		<form action="signup" method="post">
			<label>账户ID:</label><input type="text" name="id"></input><br/>
			<label> Email: </label><input type="text" name="email"></input><br/>
			<label> 名称:</label><input type="text" name="username"></input><br/>
			<label>密码:</label><input type="text" name="password"></input><br/>
			<label>确认密码:</label><input type="text" name="confirm_password"></input><br/>
			<label>验证码:</label><input type="text" name="captcha_value"></input><br/>
			
			<input type="hidden" name="captcha_key" value="<%=captchaKey%>"/>
			<img alt="" src="<%=request.getContextPath()%>/captcha_image?key=<%=captchaKey%>"/><br/>
			<button>确认</button>
		</form>
	</div>
</body>
</html>