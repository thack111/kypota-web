package com.hacksnet.kypota.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
//	private ContactRepository contactRepo;
//	
//	@Autowired
//	public HomeController (SurveyRepository contactRepo) {
//		this.contactRepo = contactRepo;
//	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String admin(Map<String,Object> model) {
//		List<Contact> contacts = contactRepo.findAll();
//		model.put("contacts",  contacts);
		return "admin";
	}
	
//	@RequestMapping(method=RequestMethod.POST)
//	public String submit(Contact contact) {
//		contactRepo.save(contact);
//		return "redirect:/";
//	}
	

}
