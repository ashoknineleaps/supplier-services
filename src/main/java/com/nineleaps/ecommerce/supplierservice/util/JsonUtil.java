package com.nineleaps.ecommerce.supplierservice.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
		//	T readValueFromGson = gson.fromJson(jsonString, clazz);
			
			JSONParser parser = new JSONParser();
			
			JSONObject json = (JSONObject) parser.parse(jsonString);
			
			String object = (String)json.get("date");
			
			String format = "yyyy-MM-dd'T'HH:mm:ss.SSS";
			
		    DateFormat df4 = new SimpleDateFormat(format);

		    String format2 = df4.format(object);
		    
		    System.out.println(format2);
		    
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            mapper.registerModule(new Jdk8Module());
            mapper.registerModule(new JSR310Module());
			mapper.registerModule(new JavaTimeModule());
			T readValue = mapper.readValue(jsonString, clazz);
			
			return readValue;
			
		}catch (JsonSyntaxException | JsonProcessingException | org.json.simple.parser.ParseException e) {
			
			throw new RuntimeException(e);
		}
	}
}
