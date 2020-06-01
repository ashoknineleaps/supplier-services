package com.nineleaps.ecommerce.supplierservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nineleaps.ecommerce.supplierservice.exception.SupplierNotFoundException;
import com.nineleaps.ecommerce.supplierservice.model.Supplier;

@Service
public class KafkaRecieverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRecieverService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SupplierService supplierService;

	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void recieveData(Supplier supplier) throws SupplierNotFoundException {
		
		System.out.println("getProductId:::" + supplier.getProductId());
		System.out.println("Data:::" + supplier.toString() + " recieved");
		
		LOGGER.info("Supplier Data Consumed from Kafka Broker: " + supplier.toString() + " recieved");
		
		try {
			Supplier email = supplierService.getSupplierByProductId(supplier.getProductId());
			
			System.out.println("EMAILID:::" + email.getSupplierEmail());
			LOGGER.info("Email Id: "+email.getSupplierEmail());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(supplier.getSupplierEmail());
		msg.setSubject("order notification mail");
		msg.setText("order has been recived");
		
		javaMailSender.send(msg);
		
		LOGGER.info("Email Notification sent Successfully");
	}

}
