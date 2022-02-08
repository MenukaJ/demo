package com.fusionx.demo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.demo.enums.CommonStatus;
import com.fusionx.demo.model.Brand;
import com.fusionx.demo.resource.CommonRequestResource;
import com.fusionx.demo.resource.MessageResponseResource;
import com.fusionx.demo.service.BrandService;



/**
 * Brand Controller
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   01-08-2020                             MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@RestController
@RequestMapping(value = "/brand")
@CrossOrigin(origins = "*")
public class BrandController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BrandService brandService;
	
	/*
	 * get all Brand
	 * @param @PathVariable{tenantId}
	 * @param @PathVariable{all}
	 * @return List
	 **/
	@GetMapping("/all")
	public ResponseEntity<Object> getAllBrand(){
		MessageResponseResource responseMessage = new MessageResponseResource();
		List<Brand>isPresentBrand = brandService.getAll();
		if(!isPresentBrand.isEmpty()) {
			return new ResponseEntity<>((Collection<Brand>)isPresentBrand,HttpStatus.OK);
		}
		else {
			responseMessage.setMessage(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Brand by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 * @return Optional
	 **/
	@GetMapping(value = "/id/{id}")
	public ResponseEntity<Object> getBrandById(@PathVariable(value = "id", required = true) Long id){
		MessageResponseResource responseMessage = new MessageResponseResource();
		Optional<Brand> isPresentBrand = brandService.getById(id);
		if(isPresentBrand.isPresent()) {
			return new ResponseEntity<>(isPresentBrand.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessage(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Brand by name
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {name}
	 * @return List
	 **/
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<Object> getBrandByName(@PathVariable(value = "name", required = true) String name){
		MessageResponseResource responseMessage = new MessageResponseResource();
		Optional<Brand> isPresentBrand = brandService.getByName(name);
		if(isPresentBrand.isPresent()) {
			return new ResponseEntity<>(isPresentBrand.get(), HttpStatus.OK);
		}
		else {
			responseMessage.setMessage(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
	 * get Brand by status
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {status}
	 * @return List
	 **/
	@GetMapping(value = "/status/{status}")
	public ResponseEntity<Object> getBrandByStatus(@PathVariable(value = "status", required = true) String status){
		MessageResponseResource responseMessage = new MessageResponseResource();
		if(status.equals(CommonStatus.ACTIVE.toString()) || status.equals(CommonStatus.INACTIVE.toString())) {
			List<Brand> isPresentBrand = brandService.getByStatus(status);
			if(!isPresentBrand.isEmpty()) {
				return new ResponseEntity<>(isPresentBrand, HttpStatus.OK);
			}
			else {
				responseMessage.setMessage(environment.getProperty("common.record-not-found"));
				return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
			}
		}
		else {
			responseMessage.setMessage(environment.getProperty("common-status.pattern"));
			return new ResponseEntity<>(responseMessage, HttpStatus.NO_CONTENT);
		}
	}
	
	/**
     * save Brand
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonAddResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PostMapping("/add")
	public ResponseEntity<Object> addBrand(@Valid @RequestBody CommonRequestResource commonAddResource){
		Brand Brand = brandService.addBrand(commonAddResource);
		MessageResponseResource responseMessage = new MessageResponseResource(environment.getProperty("common.saved"), Long.toString(Brand.getId()));
		return new ResponseEntity<>(responseMessage,HttpStatus.CREATED);
	}
	
	/**
     * update Brand
     * @param @PathVariable{tenantId}
     * @param @RequestBody{CommonUpdateResource}
     * @return SuccessAndErrorDetailsDto
     */
	@PutMapping(value = "update/{id}")
	public ResponseEntity<Object> updateBrand(@PathVariable(value = "id", required = true) Long id, 
												                 @Valid @RequestBody CommonRequestResource commonUpdateResource){
		MessageResponseResource MessageResponseResource=new MessageResponseResource();
		Optional<Brand>isPresentBrand = brandService.getById(id);		
		if(isPresentBrand.isPresent()) {
			commonUpdateResource.setId(id.toString());
			Brand Brand = brandService.updateBrand(commonUpdateResource);
			MessageResponseResource = new MessageResponseResource(environment.getProperty("common.updated"), Brand.getId().toString());
			return new ResponseEntity<>(MessageResponseResource,HttpStatus.OK);
		}
		else {
			MessageResponseResource.setMessage(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(MessageResponseResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
	/**
	 * get Brand by ID
	 * @param @pathVariable{tenantId}
	 * @param @pathVariable {id}
	 **/
	@DeleteMapping(value = "/id/{id}")
	public ResponseEntity<Object> deleteBrandById(@PathVariable(value = "id", required = true) Long id){
		MessageResponseResource MessageResponseResource=new MessageResponseResource();
		Optional<Brand>isPresentBrand = brandService.getById(id);		
		if(isPresentBrand.isPresent()) {
			brandService.deleteBrand(id);
			MessageResponseResource = new MessageResponseResource(environment.getProperty("common.deleted"), id.toString());
			return new ResponseEntity<>(MessageResponseResource,HttpStatus.OK);
		}
		else {
			MessageResponseResource.setMessage(environment.getProperty("common.record-not-found"));
			return new ResponseEntity<>(MessageResponseResource, HttpStatus.UNPROCESSABLE_ENTITY);
		}
	}
	
}
