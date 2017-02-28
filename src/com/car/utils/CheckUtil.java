package com.car.utils;

import java.util.regex.Pattern;

public class CheckUtil {
	public static boolean checkUserName(String userName) {
		String regex = "[a-zA-z]{1}[a-zA-Z0-9]{7,15}";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(userName).matches();
	}
	
	public static boolean checkEmail(String email) {
		String regex = "[a-zA-Z0-9]+[@]{1}[a-zA-Z0-9]+[.]+[a-z]+";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(email).matches();
	}
	
	public static boolean checkTel(String tel) {
		String regex = "^[1][0-9]{10}$";
		Pattern pattern = Pattern.compile(regex);
		return pattern.matcher(tel).matches();
	}
	
	public static boolean checkNull(String str) {
		if (str == null || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
		
	}

}
