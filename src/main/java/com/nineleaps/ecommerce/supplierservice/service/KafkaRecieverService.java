package com.nineleaps.ecommerce.supplierservice.service;

import java.text.ParseException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.nineleaps.ecommerce.supplierservice.exception.SupplierNotFoundException;
import com.nineleaps.ecommerce.supplierservice.model.Items;
import com.nineleaps.ecommerce.supplierservice.model.Supplier;
import com.nineleaps.ecommerce.supplierservice.model.SupplierConsumer;
import com.nineleaps.ecommerce.supplierservice.util.JsonUtil;

@Service
public class KafkaRecieverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRecieverService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SupplierService supplierService;

//	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
//	public void recieveData(ConsumerRecord<?, ?> consumerRecord) throws SupplierNotFoundException {
//
//		SupplierConsumer supplierConsumer = null;
//		try {
//			supplierConsumer = JsonUtil.INSTANCE.getObject(consumerRecord.value().toString(),
//					SupplierConsumer.class);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println(supplierConsumer.toString());
//	}

	@Payload
	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void recieveData(SupplierConsumer supplier) throws SupplierNotFoundException {
		LOGGER.info("Product ID: " + supplier.toString() + " recieved");
		LOGGER.info("Supplier Data Consumed from Kafka Broker: " + supplier.toString() + " recieved");

		try {

			for (Items item : supplier.getItems()) {
				Supplier supplierObject = null;
				try {
					supplierObject = supplierService.getSupplierByProductId(item.getProductId());

					LOGGER.info("Customer Email id: " + supplierObject.getSupplierEmail());

				} catch (SupplierNotFoundException e) {
					LOGGER.error("Get Supplier by product failed");
				}

				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(supplierObject.getSupplierEmail());
				msg.setSubject("Order Notification Mail");
				msg.setText(item.getQuantity() + " orders has been recived for product ID " + item.getProductId()
						+ " And Order Id is " + supplier.getOrderId());

				javaMailSender.send(msg);

				LOGGER.info("Email Notification sent for: " + supplierObject.getSupplierEmail());

			}

//			supplier.getItems().stream().filter(f -> f != null).map(item -> {
//
//				Supplier emaild = null;
//				try {
//					emaild = supplierService.getSupplierByProductId(item.getProductId());
//				} catch (SupplierNotFoundException e) {
//					LOGGER.error("Get Supplier by product failed");
//				}
//
//				SimpleMailMessage msg = new SimpleMailMessage();
//				msg.setTo(emaild.getSupplierEmail());
//				msg.setSubject("Order Notification Mail");
//				msg.setText(item.getQuantity() + " orders has been recived for product ID " + item.getProductId()
//						+ " And Order Id is " + supplier.getOrderId());
//				javaMailSender.send(msg);
//
//				return item;
//			});

			LOGGER.info("Email Notification sent Successfully");

		} catch (Exception e) {
			LOGGER.error("Email Notification failed");
		}
	}
}
