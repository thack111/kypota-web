package com.hacksnet.kypota.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController {

	
	@RequestMapping(method=RequestMethod.GET)
	public String homeGet(Map<String,Object> model) {
		return "login";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String homePost(Map<String,Object> model) {
		return "login";
	}
}
