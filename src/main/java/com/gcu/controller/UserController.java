package com.gcu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gcu.business.UserBusinessService;
import com.gcu.model.LoginModel;
import com.gcu.model.UserDetails;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class UserController 
{
	// SLF4J Logger
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	// Inject the LoginBusinessService Bean
	@Autowired
	private UserBusinessService UserBusinessService;

	@Autowired
	UserDetails userDetails;
	
	@GetMapping("user")
	public String displayUser(Model model)
	{
		model.addAttribute("userDetails", userDetails);
		
		// If user is not logged in
		if (!userDetails.isLoggedIn())
		{
			model.addAttribute("title", "Home");
			return "redirect:/";
		}
		
		model.addAttribute("loggedIn", userDetails.isLoggedIn());
		model.addAttribute("loginModel", new LoginModel());
		model.addAttribute("title", "User Details");
		return "user";
	}
	
	@PostMapping("/doUpdate")
	public String doUpdate(@Valid LoginModel loginModel, BindingResult bindingResult, Model model)
	{
		logger.info("Attempt to update to user information");
		loginModel.setUsername(userDetails.getUsername());
		model.addAttribute("loggedIn", userDetails.isLoggedIn());
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("title", "User Details");
		
		if (!UserBusinessService.updateUser(loginModel))
		{
			return "user";
		}
		
		return "user";
	}
	
	@GetMapping("delete")
	public String displayDelete(Model model)
	{
		model.addAttribute("loggedIn", userDetails.isLoggedIn());
		model.addAttribute("title", "Delete User Account");
		return "delete";
	}
	
	@GetMapping("/doDelete")
	public String doDelete(Model model)
	{
		logger.info("Deletetion of user account");
		
		UserBusinessService.deleteUser();
		
		userDetails.Clear();
		model.addAttribute("title", "Home");
		return "redirect:/";
	}
}
