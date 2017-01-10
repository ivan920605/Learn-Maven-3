package com.ivan.mvnbook.account.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ivan.mvnbook.account.service.AccountService;
import com.ivan.mvnbook.account.service.AccountServiceException;

/**
 * Servlet implementation class CaptchaImageServlet
 */
public class CaptchaImageServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private ApplicationContext context;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CaptchaImageServlet()
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
		final String key = request.getParameter("key");

		if (StringUtils.isEmpty(key))
		{
			response.sendError(400, "No captcha key found");
		}
		else
		{
			AccountService service = (AccountService) context.getBean("accountService");
			
			
			try
			{	
				response.setContentType("image/jpeg");
				OutputStream out = response.getOutputStream();
				out.write(service.generateCaptchaImage(key));
				out.close();
			} catch (AccountServiceException e)
			{
				response.sendError(500,e.getMessage());
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
