package com.nineleaps.ecommerce.supplierservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public JedisConnectionFactory getConnectionFactory()
	{
		return new JedisConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate()
	{
		final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		
		redisTemplate.setConnectionFactory(getConnectionFactory());
		
		redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Object.class));
		
		return redisTemplate;
	}
}
