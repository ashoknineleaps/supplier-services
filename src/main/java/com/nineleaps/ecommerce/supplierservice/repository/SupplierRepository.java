package com.nineleaps.ecommerce.supplierservice.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import com.nineleaps.ecommerce.supplierservice.model.Supplier;

@Repository
public interface SupplierRepository extends CassandraRepository<Supplier, UUID> {
	@Query(value = "select supplier_email from supplier_by_id where product_id=?0")
	public Optional<Supplier> getSupplierByProductId(UUID productId);

}


