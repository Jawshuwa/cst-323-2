package com.gcu.data;
import com.gcu.model.LoginModel;
import com.gcu.model.UserDetails;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

@Service
public class UserDataService implements DataAccessInterface<LoginModel>
{
	@Autowired
	UserDetails userDetails;
	// Inject a DataSource and JbdcTemplate beans
	@Autowired
	DataSource dataSource;
	@Autowired
	JdbcTemplate jdbcTemplateObject;
	
	/**
	 * non-default constructor
	 */
	public UserDataService(DataSource dataSource)
	{
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	// It's called finyById but it literally does not that. It searches for a username password match
	@Override
	public LoginModel findByMatch(LoginModel t) 
	{
		// Get username and password
		String username = t.getUsername();
		String password = t.getPassword();
		
		// Predefine SQL statement
		String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
		
		// Create list to store user
		List<LoginModel> user = new ArrayList<LoginModel>();
		try
		{
			// Get response from database
			SqlRowSet srs = jdbcTemplateObject.queryForRowSet(sql);
			while (srs.next())
			{
				// Parse response for data
				user.add(new LoginModel(srs.getString("USERNAME"), srs.getString("PASSWORD"), srs.getString("FIRSTNAME"), srs.getString("LASTNAME")));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// If response was empty, no account with username and password that matches, set loginModel to null
		if (user.isEmpty())
		{
			t = null;
		}
		
		// Set name properties of UserDetails
		userDetails.setFirstName(user.get(0).getFirstName());
		userDetails.setLastName(user.get(0).getLastName());
		
		return t;
	}

	@Override
	public boolean create(LoginModel t) 
	{
		// Get username and password
		String username = t.getUsername();
		String password = t.getPassword();
		String firstName = t.getFirstName();
		String lastName = t.getLastName();

		// Define predetermined SQL statement
		String sql = "INSERT INTO USERS (firstName, lastName, username, password) VALUES(?, ?, ?, ?)";
		try
		{
			// Execute SQL insert
			jdbcTemplateObject.update(sql, firstName, lastName, username, password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		// Set name properties of UserDetails
		userDetails.setFirstName(firstName);
		userDetails.setLastName(lastName);

		return true;
	}
	
	@Override
	public boolean update(LoginModel t) 
	{
		String sql = "UPDATE USERS SET ";
		// Determine what is being updated
		String fnStr = "";
		String lnStr = "";
		String pwStr = "";
		
		
		// Not empty
		if (!t.getFirstName().isEmpty())
		{
			fnStr = "firstName = '" + t.getFirstName() + "', ";
			userDetails.setFirstName(t.getFirstName());
		}
		// Is empty
		else
		{
			fnStr = "firstName = '" + userDetails.getFirstName() + "', ";
		}
			
		if (!t.getLastName().isEmpty())
		{
			lnStr = "lastName = '" + t.getLastName() + "', ";
			userDetails.setLastName(t.getLastName());
		}
		else
		{
			lnStr = "lastName = '" + userDetails.getLastName() + "', ";
		}
			
		if (!t.getPassword().isEmpty()) 
		{
			pwStr = "password = '" + t.getPassword() + "'";
			userDetails.setPassword(t.getPassword());
		}
		else
		{
			pwStr = "password = '" + userDetails.getPassword() + "'";
		}
		
		// Combine all string fragments into one SQL statement
		sql = sql + fnStr + lnStr + pwStr + " WHERE username = '" + userDetails.getUsername() + "'";
		
		try
		{
			// Execute SQL statement
			jdbcTemplateObject.update(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean delete(LoginModel t) 
	{
		String sql = "DELETE FROM users WHERE username = '" + userDetails.getUsername() + "'";
		
		try
		{
			// Execute SQL statement
			jdbcTemplateObject.update(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public List<LoginModel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	


	
}
