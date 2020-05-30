package com.nineleaps.ecommerce.supplierservice.exception;

public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6179750885036018232L;

	private String errorCode;
	
	private Object[] args;

	public ApplicationException() {
	}
	
	public ApplicationException(String errorCode, Object... args) {
		this.errorCode = errorCode;
		this.args = args;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
	
}
