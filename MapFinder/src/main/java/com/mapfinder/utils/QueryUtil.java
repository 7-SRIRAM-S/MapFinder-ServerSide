package com.mapfinder.utils;

public class QueryUtil {
	public static final String CHECK_USERNAME = "SELECT * FROM users WHERE USERNAME=?";
	public static String INSERT_USER = "INSERT INTO users (USERNAME, PASSWORD) VALUES(?, AES_ENCRYPT(?, ?))";
	public static String VIEW_USER = "SELECT * FROM users WHERE USERNAME = ? AND PASSWORD = AES_ENCRYPT(?, ?)";
}
