package com.ivan.mvnbook.account.web;

import java.io.IOException;
import java.util.function.Supplier;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ivan.mvnbook.account.service.AccountService;
import com.ivan.mvnbook.account.service.AccountServiceException;
import com.ivan.mvnbook.account.service.SignUpRequest;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private ApplicationContext context;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUpServlet()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException
	{
		super.init();
		context = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ")
				.append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirm_password");
		String captchaKey = request.getParameter("captcha_key");
		String captchaValue = request.getParameter("captcha_value");

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(email)
				|| StringUtils.isEmpty(username))
		{
			response.sendError(400,"Parameter Incomplete.");
			return;
		}
		
		AccountService service = (AccountService) context.getBean("accountService");
		
		SignUpRequest req = new SignUpRequest();
		req.setId(id);
		req.setEmail(email);
		req.setUsername(username);
		req.setPassword(password);
		req.setConfirmPassword(confirmPassword);
		req.setCaptchaKey(captchaKey);
		req.setCaptchaValue(captchaValue);
		
		req.setActivateServcieUrl(getServletContext().getRealPath("/")+"activate");
		
		try
		{
			service.signUp(req);
			response.getWriter().print("Account is created,please check your mail box for activation link.");

		} catch (AccountServiceException e)
		{
			response.sendError(500, e.getMessage());
			return;
		}
		
		
	}

}
