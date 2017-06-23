package com.car.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.car.dao.UserDao;
import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.models.User;
import com.car.service.UserService;
import com.car.utils.CheckUtil;
import com.car.utils.DataWrapper;
import com.car.utils.FileUtils;
import com.car.utils.MD5Util;
import com.car.utils.SessionManager;



@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	@Override
	public DataWrapper<Void> register(User user,MultipartFile file,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		if (CheckUtil.checkNull(user.getUserName()) || CheckUtil.checkNull(user.getPassword()) || CheckUtil.checkNull(user.getName()) || 
				CheckUtil.checkNull(user.getEmail()) || CheckUtil.checkNull(user.getSchoolYear())) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Null_Input_Error);
		}  else {
			user.setState(Parameters.toBePassed);//0-not pass,1-pass,2-to be passed
			user.setType(Parameters.user);//0-admin, 1-user
			user.setRegisterDate(new Timestamp(System.currentTimeMillis()));
			user.setId(null);
			user.setCareerCount(0);
			user.setPassword(MD5Util.getMD5String(Parameters.salt + MD5Util.getMD5String(user.getPassword())));
			
			if (file != null) {
				String filePath = FileUtils.saveFile(file, "personalPhoto", request);
				user.setPhoto(Parameters.fileSrc + filePath);
			}
			
			
			if (!userDao.saveUser(user)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<User> login(String userName, String password) {
		// TODO Auto-generated method stub
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		User user = userDao.getByUserName(userName);
		if (user != null && user.getPassword().equals(MD5Util.getMD5String(Parameters.salt + MD5Util.getMD5String(password)))) {
			
//			if (user.getState() == Parameters.unPassed) {
//				dataWrapper.setErrorCode(ErrorCodeEnum.User_UnPassed);
//			} else if(user.getState() == Parameters.toBePassed) {
//				dataWrapper.setErrorCode(ErrorCodeEnum.User_To_Be_Passed);
//			} else {
//				SessionManager.removeSessionByUserId(user.getId());
//				String token = SessionManager.newSession(user);
//				dataWrapper.setToken(token);
//			}
			SessionManager.removeSessionByUserId(user.getId());
			String token = SessionManager.newSession(user);
			dataWrapper.setToken(token);
			user.setPassword(null);
			dataWrapper.setData(user);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<User> getByToken(String token) {
		// TODO Auto-generated method stub
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		User user = SessionManager.getSession(token);
		if (user != null) {
			User userInDB = userDao.getById(user.getId());
			userInDB.setPassword(null);
			dataWrapper.setData(userInDB);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> updateUser(User user,MultipartFile file,String token,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		
		String name = user.getName();
		String email = user.getEmail();
		String schoolYear = user.getSchoolYear();
		
		String school = user.getSchool();
		String contact = user.getContact();
		String idNumber = user.getIdNumber();
		String mentor = user.getMentor();
		String degreeType = user.getDegreeType();
		
		if (CheckUtil.checkNull(name) || CheckUtil.checkNull(email) || CheckUtil.checkNull(schoolYear)) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Null_Input_Error);
		} else if (!CheckUtil.checkEmail(email)) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Email_Format_Error);
		} else {
			User userInMemmory = SessionManager.getSession(token);
			
			
			if (userInMemmory != null) {
				User userInDB = userDao.getById(userInMemmory.getId());
				if (userInDB != null) {
					
					userInDB.setName(name);
					userInDB.setEmail(email);
					userInDB.setSchoolYear(schoolYear);
					
					if (file != null) {
						String filePath = FileUtils.saveFile(file, "personalPhoto", request);
						userInDB.setPhoto(Parameters.fileSrc + filePath);
					}
					if (school != null) {
						userInDB.setSchool(school);
					}
					if (contact != null) {
						userInDB.setContact(contact);
					}
					if (idNumber != null) {
						userInDB.setIdNumber(idNumber);
					}
					if (mentor != null) {
						userInDB.setMentor(mentor);
					}
					
					if (degreeType != null) {
						userInDB.setDegreeType(degreeType);
					}
					
					
					if (!userDao.updateUser(userInDB)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
					
				}
				
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
			
		}
		
		
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> changePwd(String oldPassword, String newPassword, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User userInMemmory = SessionManager.getSession(token);
		if (userInMemmory != null) {
			User userInDB = userDao.getById(userInMemmory.getId());
			if (userInDB != null) {
				if (userInDB.getPassword().equals(MD5Util.getMD5String(Parameters.salt + MD5Util.getMD5String(oldPassword)))) {
					
					if (CheckUtil.checkNull(newPassword)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Null_Input_Error);
					} else {
						userInDB.setPassword(MD5Util.getMD5String(Parameters.salt + MD5Util.getMD5String(newPassword)));
						if (!userDao.updateUser(userInDB)) {
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
						
					}
					
				} else {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<List<User>> getUserList(String keywords,Integer state, String schoolYear,Integer careerCount,Integer numberPerPage,
			Integer currentPage, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
		User user = SessionManager.getSession(token);
		if (user != null) {
			dataWrapper = userDao.getUserList(keywords,state, schoolYear, careerCount, numberPerPage, currentPage);
			for(User oneUser : dataWrapper.getData()) {
				oneUser.setPassword(null);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<User> getUserDetails(Long userId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<User> dataWrapper = new DataWrapper<User>();
		User admin = SessionManager.getSession(token);
		if (admin != null) {
			User user = userDao.getById(userId);
			user.setPassword(null);
			dataWrapper.setData(user);
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> verify(Long userId, Integer state, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			User user = userDao.getById(userId);
			if (user != null) {
				user.setState(state);
				if (!userDao.updateUser(user)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	@Override
	public DataWrapper<Void> deleteUser(Long userId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			User user = userDao.getById(userId);
			if (user != null) {
				if (!userDao.deleteUser(user)) {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}
	

}
