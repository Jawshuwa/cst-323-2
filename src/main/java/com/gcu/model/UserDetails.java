package com.gcu.model;


public class UserDetails 
{
	private boolean LoggedIn;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	public UserDetails()
	{
		LoggedIn = false;
		username = null;
		password = null;
		firstName = null;
		lastName = null;
	}
	
	public UserDetails(boolean LoggedIn, String username, String password, String firstName, String lastName) 
	{
		this.LoggedIn = LoggedIn;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public boolean isLoggedIn() {
		return LoggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		LoggedIn = loggedIn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	// Assigns properties to null or false
	public void Clear()
	{
		LoggedIn = false;
		username = null;
		password = null;
		firstName = null;
		lastName = null;
	}
	
}
