package com.ivan.mvnbook.account.persist;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ivan.mvnbook.account.persist.bean.Account;
import com.ivan.mvnbook.account.persist.service.AccountPersistService;

public class AccountPersistServiceTest {

	private AccountPersistService service;

	@Before
	public void prepare() throws Exception {

		File persistDataFile = new File("src/test/resources/persist-data.xml");
		if (persistDataFile.exists()) {
			persistDataFile.delete();
		}

		ApplicationContext ctx = new ClassPathXmlApplicationContext("account-persist.xml");

		service = (AccountPersistService) ctx.getBean("accountPersistService");

		Account account = new Account();
		account.setId("1");
		account.setName("ivan");
		account.setEmail("137053476@qq.com");
		account.setPassword("1234");
		account.setActivated(true);

		service.createAccount(account);
	}
	
	@Test
	public void testReadAccount() throws Exception {
		
		Account account = service.readAccount("1");
		assertNotNull(account);
		assertNotNull("1",account.getId());
	}
}
