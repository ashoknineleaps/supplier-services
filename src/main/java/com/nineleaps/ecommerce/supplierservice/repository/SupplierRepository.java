package com.nineleaps.ecommerce.supplierservice.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.nineleaps.ecommerce.supplierservice.model.Supplier;

@Repository
public interface SupplierRepository extends CassandraRepository<Supplier, UUID> {

}
