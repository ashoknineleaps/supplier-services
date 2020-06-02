package com.nineleaps.ecommerce.supplierservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.nineleaps.ecommerce.supplierservice.exception.SupplierNotFoundException;
import com.nineleaps.ecommerce.supplierservice.model.Supplier;
import com.nineleaps.ecommerce.supplierservice.repository.SupplierRepository;

/**
 * @name Ashok Kumar
 * @author nineleaps
 * @email ashok.kumar@nineleaps.com
 *
 */
@Service
public class SupplierService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierService.class);

	@Autowired
	private SupplierRepository supplierRepository;
	


	// Redis
	private static final String TABLE_NALE = "SUPPLIER";

	private RedisTemplate<String, Object> redisTemplate;

	private HashOperations<String, UUID, Supplier> hashOperations;

	@Autowired
	public SupplierService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * initilize Hash Operation for Redis Operation
	 */
	@PostConstruct
	public void initilizeHashOperation() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * Create new Supplier
	 * @param supplier
	 * @return
	 */
	public Supplier createSupplier(Supplier supplier) {
		Supplier supplierSaved = supplierRepository.save(supplier);
		hashOperations.put(TABLE_NALE, supplierSaved.getSupplierId(), supplierSaved);

		LOGGER.info("Successfuly Save Supplier in Supplier Repository: " + supplierSaved);

		return supplierSaved;
	}

	/**
	 * Update Supplier by Id 
	 * @param supplierId
	 * @param supplierRequest
	 * @return
	 * @throws SupplierNotFoundException
	 */
	public Supplier updateSupllierById(UUID supplierId, Supplier supplierRequest) throws SupplierNotFoundException {

		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);

		if (!supplierOptional.isPresent()) {
			LOGGER.error("Supplier not found in Supplier respository while updateSupllierById");
			throw new SupplierNotFoundException("Supplier not found in Supplier respository");
		}

		supplierRequest.setSupplierId(supplierId);

		Supplier supplierUpdate = supplierRepository.save(supplierRequest);

		hashOperations.put(TABLE_NALE, supplierId, supplierUpdate);

		LOGGER.info("Successfuly Updated Supplier in Supplier Repository: " + supplierUpdate);

		return supplierUpdate;
	}

	/**
	 * Get All the Supplier
	 * @return
	 */
	public List<Supplier> getAllSuppliers() {
		List<Supplier> suppliers = supplierRepository.findAll();

		LOGGER.info("Get All the Supplier: " + suppliers);
		return suppliers;
	}

	/**
	 * Get Supplier by Id
	 * @param supplierId
	 * @return
	 * @throws SupplierNotFoundException
	 */
	public Supplier getSupplierById(UUID supplierId) throws SupplierNotFoundException {

		Supplier supplier = hashOperations.get(TABLE_NALE, supplierId);

		if (supplier == null) {
			Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);

			if (!supplierOptional.isPresent()) {
				LOGGER.error("Supplier not found in Supplier respository while getSupplierById");
				throw new SupplierNotFoundException("Supplier not found in Supplier respository");
			}

			LOGGER.info("Get the Supplier by Id: " + supplierOptional);

			return supplierOptional.get();
		}

		LOGGER.info("Get the Supplier by Id from Cache " + supplier);

		return supplier;
	}

	/**
	 * Get Supplier By Product Id
	 * @param productId
	 * @return
	 * @throws SupplierNotFoundException
	 */
	public Supplier getSupplierByProductId(UUID productId) throws SupplierNotFoundException {

		Optional<Supplier> supplierOptional = supplierRepository.getSupplierByProductId(productId);

		if (!supplierOptional.isPresent()) {
			LOGGER.error("Supplier not found in Supplier respository while getSupplierById");
			throw new SupplierNotFoundException("Supplier not found in Supplier respository");
		}

		LOGGER.info("Get the Supplier by Id: " + supplierOptional);

		return supplierOptional.get();

	}

	/**
	 * Delete Supplier by Id
	 * @param supplierId
	 * @throws SupplierNotFoundException
	 */
	public void deleteSupplierById(UUID supplierId) throws SupplierNotFoundException {

		Long supplierDeleteById = hashOperations.delete(TABLE_NALE, supplierId);

		Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);

		if (!supplierOptional.isPresent()) {
			LOGGER.error("Supplier not found in Supplier respository while deleting");
			throw new SupplierNotFoundException("Supplier not found in Supplier respository");
		}

		supplierRepository.deleteById(supplierId);

		LOGGER.info("Supplier Deteled by id from Cache: " + supplierDeleteById);
	}
}
