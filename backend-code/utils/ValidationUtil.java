package com.mapfinder.utils;

public class ValidationUtil {
	
	public static boolean isNotEmpty(String input) {
		return input!=null&&input.trim().length()!=0;
	}
	
	public static boolean isValidEmail(String email) {
		return false;
	}
	
	public static boolean isValidPassword(String password) {
		return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,20}$");
	}
}
