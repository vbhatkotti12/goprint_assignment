package com.goprint.exception;
/**
 * Custom Exception for Service Layer
 * @author vinod Bhatkotti
 *
 */
public class ServiceException extends RuntimeException {


	public ServiceException() {
		super();
	}
	public ServiceException(String s) {
		super(s);
	}
	public ServiceException(String s, Throwable throwable) {
		super(s, throwable);
	}
	public ServiceException(Throwable throwable) {
		super(throwable);
	}

}
