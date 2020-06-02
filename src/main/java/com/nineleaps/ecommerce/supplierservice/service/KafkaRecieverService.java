package com.nineleaps.ecommerce.supplierservice.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nineleaps.ecommerce.supplierservice.exception.SupplierNotFoundException;
import com.nineleaps.ecommerce.supplierservice.model.SupplierConsumer;
import com.nineleaps.ecommerce.supplierservice.util.JsonUtil;

@Service
public class KafkaRecieverService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRecieverService.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private SupplierService supplierService;

	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
	public void recieveData(ConsumerRecord<?, ?> consumerRecord) throws SupplierNotFoundException {

		SupplierConsumer supplierConsumer = JsonUtil.INSTANCE.getObject(consumerRecord.value().toString(),
				SupplierConsumer.class);

		System.out.println(supplierConsumer.toString());
	}

	/*
	 * @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId =
	 * "${spring.kafka.consumer.group-id}") public void recieveData(@Payload
	 * SupplierConsumer supplier) throws SupplierNotFoundException {
	 * LOGGER.info("Product ID: " + supplier.toString() + " recieved");
	 * LOGGER.info("Supplier Data Consumed from Kafka Broker: " +
	 * supplier.toString() + " recieved");
	 * 
	 * ObjectMapper objectMapper = new ObjectMapper(); String invEventInString =
	 * null; try { invEventInString = objectMapper.writeValueAsString(supplier);
	 * System.out.println(invEventInString);
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * // supplier.getItems().stream().filter(f -> f != null).map(m -> { // Supplier
	 * emailId = null; // // try { // emailId =
	 * supplierService.getSupplierByProductId(m.getProductId()); // // } catch
	 * (SupplierNotFoundException e) { //
	 * LOGGER.error("Get Supplier by product details failed: "+e.getMessage()); // }
	 * // SimpleMailMessage msg = new SimpleMailMessage(); //
	 * msg.setTo(emailId.getSupplierEmail()); //
	 * msg.setSubject("Order Notification Mail"); // msg.setText(m.getQuantity() +
	 * " orders has been recived for product ID " + m.getProductId() // +
	 * " And Order Id is " + supplier.getOrderId()); // // javaMailSender.send(msg);
	 * // // return m; // });
	 * 
	 * LOGGER.info("Email Notification sent Successfully");
	 * 
	 * }
	 */

}
