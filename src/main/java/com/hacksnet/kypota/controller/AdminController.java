package com.hacksnet.kypota.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hacksnet.kypota.dao.UserRepository;
import com.hacksnet.kypota.model.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private UserRepository userRepo;
	
	@Autowired
	public AdminController (UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	
	@RequestMapping(method=RequestMethod.GET)
	public String admin(Map<String,Object> model) {
		List<User> users = userRepo.getAllUsers();
		model.put("users",  users);
		return "admin";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String update(@RequestParam("user") String user,
					     @RequestParam("email") String email,
			             @RequestParam("admin") String admin,
					     @RequestParam("delete") String delete,
			             RedirectAttributes redirectAttributes) {
		if (delete.equals("deleteYes")) {
			userRepo.deleteUser(user);
			redirectAttributes.addFlashAttribute("message",
					"You have sucessfully Deleted User " + user + "!");
		}
		else {
			User userObj = new User(user, email, admin);

			userRepo.updateUser(userObj);
			redirectAttributes.addFlashAttribute("message",
					"You have sucessfully User " + user + "!");

		}
		return "redirect:/admin";
	}

}
