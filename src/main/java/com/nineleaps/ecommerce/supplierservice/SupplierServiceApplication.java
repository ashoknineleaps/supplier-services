package com.nineleaps.ecommerce.supplierservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SupplierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupplierServiceApplication.class, args);
	}

}
