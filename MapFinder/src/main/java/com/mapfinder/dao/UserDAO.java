package com.mapfinder.dao;

import com.mapfinder.modal.User;

public interface UserDAO {
	long insertUser(User user);
	boolean updateUser(User user);
	boolean removeUser(User user);
	User getUser(int userId);
	long getUserIdByName(String username);
	long addHint(int userId);
	String getUsernameById(int userId);
}
