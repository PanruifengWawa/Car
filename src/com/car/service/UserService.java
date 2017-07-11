package com.car.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.car.models.User;
import com.car.utils.DataWrapper;

public interface UserService {
	DataWrapper<Void> register(User user,MultipartFile file,HttpServletRequest request);
	DataWrapper<User> login(String userName,String password);
	DataWrapper<User> getByToken(String token);
	DataWrapper<Void> updateUser(User user,MultipartFile file,String token,HttpServletRequest request);
	DataWrapper<Void> changePwd(String oldPassword,String newPassword,String token);
	DataWrapper<List<User>> getUserList(String degreeType, String school, String keywords,Integer state,String schoolYear,Integer careerCount,Integer numberPerPage,Integer currentPage,String token); 
	
	DataWrapper<User> getUserDetails(Long userId,String token);
	DataWrapper<Void> verify(Long userId,Integer state,String token);
	DataWrapper<Void> deleteUser(Long userId,String token);
	
}
