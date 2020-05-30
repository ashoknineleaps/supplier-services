package com.nineleaps.ecommerce.supplierservice.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfig {

	@Bean(name = "ErrorMessageSource")
	public MessageSource getMessageSource()
	{
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		
		messageSource.setBasename("error_messages/err_message_en");
		
		return messageSource;
	}
}
