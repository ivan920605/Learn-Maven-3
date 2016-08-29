package com.ivan.mvnbook.account.persist.service;

import com.ivan.mvnbook.account.persist.bean.Account;
import com.ivan.mvnbook.common.exception.AccountPersistException;

public interface AccountPersistService {

	Account createAccount(Account account) throws AccountPersistException;

	Account readAccount(String id) throws AccountPersistException;

	Account updateAccount(Account account) throws AccountPersistException;

	void deleteAccount(String id) throws AccountPersistException;
}
