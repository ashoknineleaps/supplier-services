package com.nineleaps.ecommerce.supplierservice.model;

import java.util.UUID;

public class Items {

	private UUID productId;
	
	private int quantity;

	private int price;
	
 
	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Items [productId=" + productId + ", quantity=" + quantity + ", price=" + price + "]";
	}


	
}
