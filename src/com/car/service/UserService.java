package com.car.service;

import java.util.List;

import com.car.models.User;
import com.car.utils.DataWrapper;

public interface UserService {
	DataWrapper<Void> register(User user);
	DataWrapper<Void> login(String userName,String password);
	DataWrapper<User> getByToken(String token);
	DataWrapper<Void> updateUser(String name,String email,String schoolYear,String token);
	DataWrapper<Void> changePwd(String oldPassword,String newPassword,String token);
	DataWrapper<List<User>> getUserList(Integer state,String schoolYear,Integer numberPerPage,Integer currentPage,String token); 
	
	DataWrapper<User> getUserDetails(Long userId,String token);
	DataWrapper<Void> verify(Long userId,Integer state,String token);
}
