package com.nineleaps.ecommerce.supplierservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.nineleaps.ecommerce.supplierservice.exception.SupplierNotFoundException;
import com.nineleaps.ecommerce.supplierservice.model.Supplier;
import com.nineleaps.ecommerce.supplierservice.model.SupplierConsumer;

@Service
public class KafkaRecieverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRecieverService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SupplierService supplierService;

	@Payload
	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void recieveData(SupplierConsumer supplier) throws SupplierNotFoundException {
		LOGGER.info("Product ID: " + supplier.toString() + " recieved");
		LOGGER.info("Supplier Data Consumed from Kafka Broker: " + supplier.toString() + " recieved");
		
		try {
			System.out.println("product id=::"+supplier.items.getProductId());
			Supplier emaild = supplierService.getSupplierByProductId(supplier.items.getProductId());
			
			LOGGER.info("Email Id: "+emaild.getSupplierEmail());
			
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(emaild.getSupplierEmail());
			msg.setSubject("Order Notification Mail");
			msg.setText(supplier.items.getQuantity()+" orders has been recived for product ID "+supplier.items.getProductId()+" And Order Id is "+supplier.getOrderId());
			javaMailSender.send(msg);
			
			LOGGER.info("Email Notification sent Successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
