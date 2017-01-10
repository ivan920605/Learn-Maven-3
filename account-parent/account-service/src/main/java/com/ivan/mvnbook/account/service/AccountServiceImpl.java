package com.ivan.mvnbook.account.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ivan.mvnbook.account.captcha.AccountCaptchaException;
import com.ivan.mvnbook.account.captcha.AccountCaptchaService;
import com.ivan.mvnbook.account.email.AccountEmailException;
import com.ivan.mvnbook.account.email.AccountEmailService;
import com.ivan.mvnbook.account.persist.bean.Account;
import com.ivan.mvnbook.account.persist.service.AccountPersistService;
import com.ivan.mvnbook.common.exception.AccountPersistException;

public class AccountServiceImpl implements AccountService
{
	private AccountPersistService accountPersistService;

	private AccountEmailService accountEmailService;

	private AccountCaptchaService accountCaptchaService;

	private Map<String, String> activationMap = new HashMap<String, String>();

	public AccountPersistService getAccountPersistService()
	{
		return accountPersistService;
	}

	public void setAccountPersistService(
			AccountPersistService accountPersistService)
	{
		this.accountPersistService = accountPersistService;
	}

	public AccountEmailService getAccountEmailService()
	{
		return accountEmailService;
	}

	public void setAccountEmailService(AccountEmailService accountEmailService)
	{
		this.accountEmailService = accountEmailService;
	}

	public AccountCaptchaService getAccountCaptchaService()
	{
		return accountCaptchaService;
	}

	public void setAccountCaptchaService(
			AccountCaptchaService accountCaptchaService)
	{
		this.accountCaptchaService = accountCaptchaService;
	}

	public Map<String, String> getActivationMap()
	{
		return activationMap;
	}

	public void setActivationMap(Map<String, String> activationMap)
	{
		this.activationMap = activationMap;
	}

	public String generateCaptchaKey() throws AccountServiceException
	{
		try
		{
			return accountCaptchaService.generateCaptchaKey();
		} catch (AccountCaptchaException e)
		{
			throw new AccountServiceException();
		}
	}

	public byte[] generateCaptchaImage(String captchaKey)
			throws AccountServiceException
	{
		try
		{
			return accountCaptchaService.generateCaptchaImage(captchaKey);
		} catch (AccountCaptchaException e)
		{
			throw new AccountServiceException();
		}
	}

	public void signUp(SignUpRequest signUpRequest)
			throws AccountServiceException
	{

		try
		{

			if (!signUpRequest.getPassword()
					.equals(signUpRequest.getConfirmPassword()))
			{
				throw new AccountServiceException("2 passwords do not match. ");
			}

			if (!accountCaptchaService.validateCaptcha(
					signUpRequest.getCaptchaKey(),
					signUpRequest.getCaptchaValue()))
			{
				throw new AccountCaptchaException("Incorrect Captcha.");
			}

			Account account = new Account();
			account.setId(signUpRequest.getId());
			account.setEmail(signUpRequest.getEmail());
			account.setName(signUpRequest.getUsername());
			account.setPassword(signUpRequest.getPassword());
			account.setActivated(false);

			accountPersistService.createAccount(account);

			String activationId = RandomStringUtils.random(8, true, true);

			activationMap.put(activationId, account.getId());

			String link = signUpRequest.getActivateServcieUrl().endsWith("/")
					? signUpRequest.getActivateServcieUrl() + activationId
					: signUpRequest.getActivateServcieUrl() + "?key = "
							+ activationId;

			accountEmailService.sendMail(account.getEmail(),
					"Please Activate Your Account.", link);

		} catch (AccountCaptchaException | AccountPersistException
				| AccountEmailException e)
		{
			throw new AccountServiceException(e.getMessage());
		}

	}

	public void activate(String activationNumber) throws AccountServiceException
	{

		try
		{
			String accountId = activationMap.get(activationNumber);
			if (accountId == null)
			{
				throw new AccountServiceException(
						"Invalid account activation ID.");
			}

			Account account = accountPersistService.readAccount(accountId);
			account.setActivated(true);
			accountPersistService.updateAccount(account);
		} catch (AccountPersistException e)
		{
			throw new AccountServiceException(e.getMessage());
		}
	}

	public void login(String id, String password) throws AccountServiceException
	{
		try
		{
			Account account = accountPersistService.readAccount(id);

			if (account == null)
			{
				throw new AccountServiceException("Account does not exist.");
			}

			if (!account.isActivated())
			{
				throw new AccountServiceException("Account is disabled");
			}

			if (!account.getPassword().equals(password))
			{
				throw new AccountServiceException("Incorrect password");
			}
		} catch (AccountPersistException e)
		{
			throw new AccountServiceException();
		}

	}

}
