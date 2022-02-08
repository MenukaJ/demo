package com.fusionx.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionx.demo.model.Test;
import com.fusionx.demo.resource.TestRequestResource;
import com.fusionx.demo.service.TestService;

@RestController
@RequestMapping("/api/v1")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping
	@RequestMapping("/test1")
	private String fistApi() {
		return testService.fistApi();
	}
	
	@PostMapping
	@RequestMapping("/save")
	private Test saveStudent(@RequestBody TestRequestResource testRequestResource) {
		return testService.saveStudent(testRequestResource);
	}

}
