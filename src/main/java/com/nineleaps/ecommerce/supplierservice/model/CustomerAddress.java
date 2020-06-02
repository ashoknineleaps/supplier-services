package com.nineleaps.ecommerce.supplierservice.model;

public class CustomerAddress {
	
	private String addressLine1;
	
	private String city;
	
	private String state;
	
	private String country;

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "CustomerAddress [addressLine1=" + addressLine1 + ", city=" + city + ", state=" + state + ", country="
				+ country + "]";
	} 
	
	

}
