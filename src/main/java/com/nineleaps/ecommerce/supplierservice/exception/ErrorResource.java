package com.nineleaps.ecommerce.supplierservice.exception;

import java.util.List;

public class ErrorResource {

	private String code;
	
	private String errorCode;
	
	private String message;
	
	private List<FieldErrorResource> fieldErrors;
	
	public ErrorResource() {
	}

	public ErrorResource(String code, String errorCode, String message) {
		super();
		this.code = code;
		this.errorCode = errorCode;
		this.message = message;
	}

	public ErrorResource(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<FieldErrorResource> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldErrorResource> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	@Override
	public String toString() {
		return "ErrorResource [code=" + code + ", errorCode=" + errorCode + ", message=" + message + ", fieldErrors="
				+ fieldErrors + "]";
	}
	
}
