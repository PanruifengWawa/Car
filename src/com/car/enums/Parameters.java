package com.car.enums;

public class Parameters {
	public static final String salt = "car";
	public static final String filepath = "";
	
	public static final Integer unPassed = 0;
	public static final Integer passed = 1;
	public static final Integer toBePassed = 2;
	public static final Integer user = 1;
	public static final Integer admin = 0;
	
	public static final Integer stateUncommit = -1;
	public static final Integer stateToVerify = 0;
	public static final Integer statePassed = 1;
	public static final Integer stateOverdue = -2;
	public static final Integer stateUnpassed = 2;
}
