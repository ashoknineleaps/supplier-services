package com.nineleaps.ecommerce.supplierservice.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.nineleaps.ecommerce.supplierservice.model.Supplier;

@Component
public class SupplierValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Supplier.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplierName", "supplierName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplierEmail", "supplierEmail.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productId", "productId.required");
	}

	
}
