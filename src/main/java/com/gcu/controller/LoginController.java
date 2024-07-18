package com.gcu.controller;

import com.gcu.business.UserBusinessService;
import com.gcu.model.LoginModel;
import com.gcu.model.UserDetails;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController 
{
	// SLF4J Logger
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	// Inject the LoginBusinessService Bean
	@Autowired
	private UserBusinessService UserBusinessService;
	
	// Get UserDetails Bean
	@Autowired
	private UserDetails userDetails;
	
	@GetMapping("login")
	public String display(Model model)
	{
		model.addAttribute("title", "Login Form");
		model.addAttribute("loginModel", new LoginModel());
		model.addAttribute("loggedIn", userDetails.isLoggedIn());
		
		// User is already logged in
		if (userDetails.isLoggedIn())
		{
			model.addAttribute("title", "Home");
			return "redirect:/";
		}
			
		return "login";
	}
	
	@PostMapping("/doLogin")
	public String doLogin(@Valid LoginModel loginModel, BindingResult bindingResult, Model model)
	{
		logger.info("Login attempt");
		
		// Check for validation errors or if validateLogin() returns false
		boolean loginState = UserBusinessService.validateLogin(loginModel);
						
		if (userDetails.getFirstName() == null)
		{
			logger.info("Login failure: username - " + loginModel.getUsername() + ", password - " + loginModel.getPassword());
			model.addAttribute("title", "Login Form");
			model.addAttribute("failReason", "Username and Password were invalid");
			
			return "login";
		}
		
		;
		if (bindingResult.hasErrors() || !loginState)
		{
			logger.info("Login failure: username - " + loginModel.getUsername() + ", password - " + loginModel.getPassword());
			
			model.addAttribute("title", "Login Form");
			
			// If validateLogin() returned false, add a attribute informing the user of the reason
			if (!loginState)
			{
				model.addAttribute("failReason", "Username and Password were invalid");
			}
			
			return "login";
		}
		
		// Successful login attempt, assign userDetails information and redirect to another page
		logger.info("Login success: username - " + loginModel.getUsername() + ", password - " + loginModel.getPassword());
		userDetails.setLoggedIn(true);
		userDetails.setUsername(loginModel.getUsername());
		userDetails.setPassword(loginModel.getPassword());
		model.addAttribute("loggedIn", userDetails.isLoggedIn());
		model.addAttribute("title", "User Details");
		return "redirect:/user";
	}
	
	@GetMapping("/logout")
	public String logOut(Model model)
	{
		logger.info("Logged out");
		// remove user traces from application
		userDetails.Clear();
		
		model.addAttribute("title", "Home");
		return "redirect:/";
	}
	
}
