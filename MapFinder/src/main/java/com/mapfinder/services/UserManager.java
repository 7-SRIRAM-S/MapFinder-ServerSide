package com.mapfinder.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mapfinder.dao.UserDAOImpl;
import com.mapfinder.modal.User;

public class UserManager {
	private static UserDAOImpl userDAO=new UserDAOImpl();
    private static final Logger LOGGER=LogManager.getLogger(UserManager.class.getName());

	public static long addUser(String username,String password) {
		LOGGER.trace(new StringBuilder("::: Add User into DB :::  Creating Object for User ::: ").toString());
		User user=null;
		try {
			 user=new User(username,password);
			 return userDAO.insertUser(user);
		}
		catch(Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			return -1L;
		}

	}
	
	public static boolean isValidUser(String username, String password) {
		return userDAO.isUserExist(username,password);
	}
	
	public static boolean isUserNameExist(String username) {
		return userDAO.isUserNameExist(username);
	}

	public static long getIdByName(String username) {
		return userDAO.getUserIdByName(username);
	}
	
	public static String getUsernameById(int userId) {
		return userDAO.getUsernameById(userId);
	}
	
	public static long increaseHint(int userId) {
		return userDAO.addHint(userId);
	}
	
	public static long getHint(int userId) {
		return userDAO.getHint(userId);
	}
}
