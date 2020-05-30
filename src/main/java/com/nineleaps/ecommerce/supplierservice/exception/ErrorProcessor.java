package com.nineleaps.ecommerce.supplierservice.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Component
public class ErrorProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorProcessor.class);
	
	private static final String APPLICATION_EXCEPTION = "ApplicationException";
	
	private static final String VALIDATION_EXCEPTION = "ValidationException";
	
	
	private static final String SYSTEM_EXCEPTION = "SystemException";
	
	@Autowired
	@Qualifier(value = "ErrorMessageSource")
	private MessageSource messageSource;
	
	public ErrorResource getError(final Throwable ex)
	{
		ErrorResource error = null;
		
		if(ex instanceof ValidationException)
		{
			error = getValidationError(ex);
		}
		else if(ex instanceof ApplicationException)
		{
			error = getApplicationError(ex);
		} else 
		{
			error = getSystemException(ex);
		}
		
		return error;
	}

	private ErrorResource getValidationError(Throwable ex) {
		
		ValidationException validationException = (ValidationException) ex;
		
		List<FieldErrorResource> fieldErrorResources = new ArrayList<>();
		
		setGlobalErrors(validationException, fieldErrorResources, Locale.ENGLISH);
		
		for(FieldError fieldError : validationException.getErrors().getFieldErrors())
		{
			String errorMessage = messageSource.getMessage(fieldError.getCode(), fieldError.getArguments(), Locale.ENGLISH);
			
			FieldErrorResource errorResource = new FieldErrorResource(fieldError.getObjectName(), fieldError.getField(), fieldError.getCode(), errorMessage);
			
			fieldErrorResources.add(errorResource);
		}
		ErrorResource errorResource = new ErrorResource(VALIDATION_EXCEPTION, validationException.getMessage());
		errorResource.setFieldErrors(fieldErrorResources);
		
		LOGGER.error(VALIDATION_EXCEPTION, " '{}' ", errorResource.toString(), validationException);
		
		return errorResource;
	}

	private void setGlobalErrors(ValidationException validationException, List<FieldErrorResource> fieldErrorResources,
			Locale locale) {
		
		if(validationException.getErrors().getGlobalErrors() != null)
		{
			for(ObjectError error : validationException.getErrors().getGlobalErrors())
			{
				String errorMessage = messageSource.getMessage(error.getCode(), error.getArguments(), locale);
				
				FieldErrorResource fieldErrorResource = new FieldErrorResource(error.getObjectName(), null, error.getCode(), errorMessage);
				
				fieldErrorResources.add(fieldErrorResource);
			}
		}
	}

	private ErrorResource getSystemException(Throwable ex) {
		
		ErrorResource errorResource = new ErrorResource(SYSTEM_EXCEPTION, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		
		LOGGER.error(SYSTEM_EXCEPTION, ex);
		return errorResource;
	}

	private ErrorResource getApplicationError(Throwable ex) {
		
		ApplicationException applicationException = (ApplicationException) ex;
		
		String errorMesaage = applicationException.getLocalizedMessage();
		
		if(!StringUtils.isEmpty(applicationException.getErrorCode()))
		{
			errorMesaage = messageSource.getMessage(applicationException.getErrorCode(), applicationException.getArgs(), Locale.ENGLISH); 
		}
		
		ErrorResource errorResource = new ErrorResource(APPLICATION_EXCEPTION, applicationException.getErrorCode(), errorMesaage);
		
		LOGGER.error("[" + applicationException.getErrorCode() + "]" + errorMesaage, applicationException);
		
		return errorResource;
	}
}
