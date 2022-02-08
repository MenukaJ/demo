package com.fusionx.demo.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fusionx.demo.enums.CommonStatus;
import com.fusionx.demo.exception.ValidateRecordException;
import com.fusionx.demo.model.Brand;
import com.fusionx.demo.repository.BrandRepository;
import com.fusionx.demo.resource.CommonRequestResource;
import com.fusionx.demo.service.BrandService;



/**
 * Brand Service Impl
 * 
 ********************************************************************************************************
 *  ###   Date         Story Point   Task No    Author       Description
 *-------------------------------------------------------------------------------------------------------
 *    1   09-07-2020       						  MenukaJ        Created
 *    
 ********************************************************************************************************
 */

@Component
@Transactional(rollbackFor=Exception.class)
public class BrandServiceImpl implements BrandService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BrandRepository brandRepository;

	@Override
	public List<Brand> getAll() {
		return brandRepository.findAll();
	}

	@Override
	public Optional<Brand> getById(Long id) {
		Optional<Brand> isPresentBrand = brandRepository.findById(id);
		if (isPresentBrand.isPresent()) {
			return Optional.ofNullable(isPresentBrand.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Brand> getByName(String name) {
		Optional<Brand> isPresentBrand = brandRepository.findByName(name);
		if (isPresentBrand.isPresent()) {
			return Optional.ofNullable(isPresentBrand.get());
		}
		else {
			return Optional.empty();
		}
	}

	@Override
	public List<Brand> getByStatus(String status) {
		return brandRepository.findByStatus(CommonStatus.valueOf(status));
	}

	@Override
	public Brand addBrand(CommonRequestResource commonAddResource) {
		Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        
        Optional<Brand> isPresentBrand = brandRepository.findByName(commonAddResource.getName());
        if (isPresentBrand.isPresent()) {
        	throw new ValidateRecordException(environment.getProperty("common.duplicate"), "name");
		}
        
        Brand brand = new Brand();
        brand.setName(commonAddResource.getName());
        brand.setStatus(CommonStatus.valueOf(commonAddResource.getStatus()));
        brand.setCreatedDate(currentTimestamp);
        brand.setCreatedUser("menuka");
        brand = brandRepository.save(brand);
    	return brand;
	}

	@Override
	public Brand updateBrand(CommonRequestResource commonUpdateResource) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date now = calendar.getTime();
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
		
		Optional<Brand> isPresentBrand = brandRepository.findById(Long.parseLong(commonUpdateResource.getId()));
		if (!isPresentBrand.isPresent()) 
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		
		Optional<Brand> isPresentBrandName = brandRepository.findByName(commonUpdateResource.getName());
		if (isPresentBrandName.isPresent() && isPresentBrandName.get().getId() != isPresentBrand.get().getId())			
			throw new ValidateRecordException(environment.getProperty("common.duplicate"), "name");
		
		Brand brand = isPresentBrand.get();
		brand.setName(commonUpdateResource.getName());
		brand.setStatus(CommonStatus.valueOf(commonUpdateResource.getStatus()));
		brand.setModifiedDate(currentTimestamp);
		brand.setModifiedUser("menuka");
		brand = brandRepository.saveAndFlush(brand);
    	return brand;
	}

	@Override
	public void deleteBrand(Long id) {
		Optional<Brand> isPresentBrand = brandRepository.findById(id);
		if (!isPresentBrand.isPresent()) 
			throw new ValidateRecordException(environment.getProperty("common.record-not-found"), "message");
		else
			brandRepository.deleteById(id);	
	}
	
}
