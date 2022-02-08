package com.fusionx.demo.service;

import org.springframework.stereotype.Service;

import com.fusionx.demo.model.Test;
import com.fusionx.demo.resource.TestRequestResource;

@Service
public interface TestService {
	
	public String fistApi();
	
	public Test saveStudent(TestRequestResource testRequestResource);

}
