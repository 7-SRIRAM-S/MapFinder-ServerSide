package com.mapfinder.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.controller.SignUpServlet;
import com.mapfinder.dao.UserDAOImpl;
import com.mapfinder.modal.User;

public class UserManager {
	private static UserDAOImpl userDAO=new UserDAOImpl();
    private static final Logger LOGGER=LogManager.getLogger(UserManager.class.getName());

	public static boolean addUser(String username,String password) {
		LOGGER.trace(new StringBuilder("::: Add User into DB :::  Creating Object for User ::: ").toString());
		User user=null;
		try {
			 user=new User(username,password);
			 if(!userDAO.insertUser(user)) {
				 return false;
			 }
		}
		catch(Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());

			return false;
		}
		return user!=null;
	}
	
	public static boolean isValidUser(String username, String password) {
		return userDAO.isUserExist(username,password);
	}
	
	public static boolean isUserNameExist(String username) {
		return userDAO.isUserNameExist(username);
	}
}
