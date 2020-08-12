package com.hacksnet.kypota.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.UserRepository;
import com.hacksnet.kypota.model.NewUser;

@Controller
@RequestMapping("/sign_up")
public class SignUpController {
	private UserRepository userRepo;
	
	@Autowired
	public SignUpController (UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String signup(Map<String,Object> model) {
//		List<Contact> contacts = contactRepo.findAll();
//		model.put("contacts",  contacts);
		return "sign_up";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String submit(@RequestParam("user") String user, 
			             @RequestParam("email") String email,
			             @RequestParam("password") String password,
			             RedirectAttributes redirectAttributes) {
		NewUser newUser = new NewUser();
		newUser.setUser(user);
		newUser.setEmail(email);
		newUser.setPassword(password);
		if (userRepo.storeNewUser(newUser)) {
			redirectAttributes.addFlashAttribute("message",
					"You have sucessfully registered new user " + user + "!");
		}
		else {
			redirectAttributes.addFlashAttribute("message",
					"Error adding new user " + user + "!");
		}
		
		return "redirect:/";
	}
	

}
