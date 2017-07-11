package com.car.dao;

import java.util.List;

import com.car.models.User;
import com.car.utils.DataWrapper;

public interface UserDao {
	boolean saveUser(User user);
	User getByUserName(String userName);
	User getById(Long id);
	boolean updateUser(User user);
	DataWrapper<List<User>> getUserList(String degreeType, String school, String keywords,Integer state, String schoolYear, Integer careerCount,Integer numberPerPage,Integer currentPage);
	
	DataWrapper<List<User>> getProjectMember(Long projectId);
	boolean deleteUser(User user);
	
}
