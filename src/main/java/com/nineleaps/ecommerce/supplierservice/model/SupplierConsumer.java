package com.nineleaps.ecommerce.supplierservice.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class SupplierConsumer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6883367072300867929L;
	
	private UUID orderId;
	private Date date;
	private String customerName;
	private String customerEmail;
	private int total;
	private List<Items> items;
	private CustomerAddress customerAddress;
	private String supplierEmail;

	public UUID getOrderId() {
		return orderId;
	}

	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	public CustomerAddress getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(CustomerAddress customerAddress) {
		this.customerAddress = customerAddress;
	}

	@Override
	public String toString() {
		return "SupplierConsumer [orderId=" + orderId + ", date=" + date + ", customerName=" + customerName
				+ ", customerEmail=" + customerEmail + ", total=" + total + ", items=" + items + ", customerAddress="
				+ customerAddress + ", supplierEmail=" + supplierEmail + "]";
	}
	
}
