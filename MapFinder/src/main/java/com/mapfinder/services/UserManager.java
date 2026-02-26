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
		long userId=-1;
		try {
			 user=new User(username,password);
			 userId=userDAO.insertUser(user);
			 LeaderBoardManager.insertLeaderboard((int)userId);
		}
		catch(Exception e) {
			LOGGER.warn(new StringBuilder("::: Problem in Creating Object :::  "+e.getMessage()+" ::: ").toString());
			userId=-1L;
		}
		return userId;
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
	
	public static boolean updateHint(int hints,int userId) {
		return userDAO.updateHint(hints,userId);
	}
	
	public static boolean increaseHints(int attemptId) {
		return userDAO.increaseHint(attemptId);
	}
	
	public static boolean makeActiveUser(int userId) {
		return userDAO.makeActive(userId);
	}
	
	public static boolean makeDeactiveUser(int userId) {
		return userDAO.makeDeactive(userId);
	}
	
	public static boolean isUserActive(int userId) {
		return userDAO.isUserActive(userId);
	}
	
	
	
	
	
	
	
	
}
