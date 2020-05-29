package com.nineleaps.ecommerce.supplierservice.model;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.driver.core.DataType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "This model is to create a Supplier")
@Table(value = "supplier_by_id")
public class Supplier implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -375602903733093311L;

	@ApiModelProperty(notes = "Auto generated unique id", required = true, position = 1)
	@PrimaryKey
	@CassandraType(type = DataType.Name.UUID)
	private UUID supplierId = UUID.randomUUID();

	@ApiModelProperty(notes = "Supplier Name should be Valid Name", example = "Xyz", required = true, position = 2)
	@CassandraType(type = DataType.Name.TEXT)
	@Column(value = "supplier_name")
	@NotNull(message = "Supplier Name can not be null")
	private String supplierName;
	
	@ApiModelProperty(notes = "Supplier Email Id should be Valid", example = "xyz@gmail.com", required = true, position = 3)
	@CassandraType(type = DataType.Name.TEXT)
	@Column(value = "supplier_email")
	@NotNull(message = "Supplier email can not be null")
	private String supplierEmail;

	@ApiModelProperty(notes = "Supplier Product Id should be Valid", required = true, position = 4)
	@CassandraType(type = DataType.Name.UUID)
	@Column(value = "product_id")
	@NotNull(message = "Product Id can not be null")
	private UUID productId;

	public UUID getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(UUID supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierEmail() {
		return supplierEmail;
	}

	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}

	public UUID getProductId() {
		return productId;
	}

	public void setProductId(UUID productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((supplierEmail == null) ? 0 : supplierEmail.hashCode());
		result = prime * result + ((supplierId == null) ? 0 : supplierId.hashCode());
		result = prime * result + ((supplierName == null) ? 0 : supplierName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (supplierEmail == null) {
			if (other.supplierEmail != null)
				return false;
		} else if (!supplierEmail.equals(other.supplierEmail))
			return false;
		if (supplierId == null) {
			if (other.supplierId != null)
				return false;
		} else if (!supplierId.equals(other.supplierId))
			return false;
		if (supplierName == null) {
			if (other.supplierName != null)
				return false;
		} else if (!supplierName.equals(other.supplierName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", supplierEmail="
				+ supplierEmail + ", productId=" + productId + "]";
	}
	
}
