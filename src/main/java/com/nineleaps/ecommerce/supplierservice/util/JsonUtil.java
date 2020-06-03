package com.nineleaps.ecommerce.supplierservice.util;

import java.text.ParseException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public enum JsonUtil {

	INSTANCE;
	
	public String getJsonString(Object object)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			return mapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T getObject(String jsonString, Class<T> clazz) throws ParseException {
		
		if(StringUtils.isEmpty(jsonString))
		{
			throw new IllegalArgumentException("Input Argument can not be Empty!!!");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		Gson gson = new Gson();
		
		try    
		{
			T readValueFromGson = gson.fromJson(jsonString, clazz);
			
//			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//            mapper.registerModule(new Jdk8Module());
//            mapper.registerModule(new JSR310Module());
//			mapper.registerModule(new JavaTimeModule());
//			T readValue = mapper.readValue(jsonString, clazz);
			
			return readValueFromGson;
			
		}catch (JsonSyntaxException e) {
			
			throw new RuntimeException(e);
		}
	}
}
