package com.fusionx.demo.service.impl;

import org.springframework.stereotype.Component;

import com.fusionx.demo.model.Test;
import com.fusionx.demo.resource.TestRequestResource;
import com.fusionx.demo.service.TestService;

@Component
public class TestServiceImpl implements TestService {

	@Override
	public String fistApi() {
	
		return "Hello world";
	}

	@Override
	public Test saveStudent(TestRequestResource testRequestResource) {
		Test test = new Test();	
		
		test.setId(Long.parseLong("1"));
		test.setName(testRequestResource.getName());
		test.setAge(Integer.parseInt(testRequestResource.getAge()));
		
		return test;
	}

}
