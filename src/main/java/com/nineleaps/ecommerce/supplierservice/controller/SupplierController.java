package com.nineleaps.ecommerce.supplierservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.nineleaps.ecommerce.supplierservice.exception.SupplierNotFoundException;
import com.nineleaps.ecommerce.supplierservice.model.Supplier;
import com.nineleaps.ecommerce.supplierservice.service.SupplierService;
import com.nineleaps.ecommerce.supplierservice.util.ValidatorUtil;
import com.nineleaps.ecommerce.supplierservice.validators.SupplierValidator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/v1/api/suppliers")
@Api(tags = "Suppliers Management RESTful Services", value = "Suppliers services", description = "Controller for Suppliers Management Service")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private SupplierValidator supplierValidator;

	@Autowired
	private ValidatorUtil validationUtils;

	@ApiOperation(value = "Created new Supplier", response = Supplier.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully Created Supplier"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

	})
	@PostMapping
	public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplierRequest) {

		validationUtils.validate(supplierValidator, supplierRequest, "supplier");

		Supplier createSupplier = supplierService.createSupplier(supplierRequest);

		return new ResponseEntity<Supplier>(createSupplier, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Get All Suppliers", response = Supplier.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

	})
	@GetMapping
	public List<Supplier> getAllSuppliers() {
		return supplierService.getAllSuppliers();
	}

	@ApiOperation(value = "Get Suppliers by id", response = Supplier.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

	})
	@GetMapping("/{supplierId}")
	@Cacheable(key = "#supplierId", value = "suppliers")
	public Supplier getSupplierById(@PathVariable UUID supplierId) {
		Supplier supplierResponse = null;
		try {
			supplierResponse = supplierService.getSupplierById(supplierId);
			return supplierResponse;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@ApiOperation(value = "Update the Supplier", response = Supplier.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Update Supplier by id"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

	})
	@PutMapping("/{supplierId}")
	@CachePut(key = "#supplierId", value = "suppliers")
	public Supplier updateSupllierById(@PathVariable UUID supplierId, @RequestBody Supplier supplierRequest) {
		try {
			validationUtils.validate(supplierValidator, supplierRequest, "supplier");

			Supplier supplierResponse = supplierService.updateSupllierById(supplierId, supplierRequest);
			return supplierResponse;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Delete the Supplier", response = Supplier.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully Delete Supplier by Id"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),

	})
	@DeleteMapping("/{supplierId}")
	@CacheEvict(key = "#supplierId", value = "suppliers")
	public void deleteSupplierById(@PathVariable UUID supplierId) {
		try {
			supplierService.deleteSupplierById(supplierId);
		} catch (SupplierNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

}