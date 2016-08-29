package com.ivan.mvnbook.common.exception;

public class AccountPersistException extends Exception {

	public AccountPersistException(String message) {
		super(message);
	}
	
	public AccountPersistException(String message, Throwable nestedException) {
        super(message);
    }
}
