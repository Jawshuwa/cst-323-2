package com.gcu;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gcu.business.UserBusinessService;
import com.gcu.model.UserDetails;

@Configuration
public class SpringConfig 
{
	// This is where Beans will be instantiated and injected into controllers
	@Bean(name="UserBusinessService")
	UserBusinessService getUserBusinessService()
	{
		return new UserBusinessService();
	}

    @Bean(name = "UserDetails")
    UserDetails getUserDetails()
	{
		return new UserDetails();
	}
}
