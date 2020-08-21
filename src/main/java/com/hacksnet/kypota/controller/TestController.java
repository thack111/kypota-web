package com.hacksnet.kypota.controller;

import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hacksnet.kypota.dao.TestRepository;

@RestController
@RequestMapping("/test/teststr")
public class TestController {
	private TestRepository testRepo;
	
	@Autowired
	public TestController (TestRepository testRepo) {
		this.testRepo = testRepo;
	}
	
	@RequestMapping(method=RequestMethod.GET )
	@Produces({MediaType.TEXT_PLAIN})
	public String test(Map<String,Object> model) {
		
		return testRepo.getDbTest();
	}
	


}
