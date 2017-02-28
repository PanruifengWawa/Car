package com.car.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.models.User;
import com.car.service.UserService;
import com.car.utils.DataWrapper;



@Controller
@RequestMapping(value="/api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	
	/**
	* @api {post} api/user/register 注册 -用户用
	* @apiName user_regist
	* @apiGroup user
	*
	* @apiParam {String} userName * 学号（必须）
	* @apiParam {String} password * 密码（必须）
	* @apiParam {String} name * 姓名（必须）
	* @apiParam {String} email * 邮箱（必须）
	* @apiParam {String} schoolYear * 学籍（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "SUCCEED",
	*  		"errorCode": "No_Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "Error",//还有Null_Input_Error，Email_Format_Error
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="register", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> register(
    		@ModelAttribute User user
    		) {
		
    	return userService.register(user);
    }

	/**
	* @api {post} api/user/login 登录 -用户，管理员用
	* @apiName user_login
	* @apiGroup user
	*
	* @apiParam {String} userName * 用户名（必须）
	* @apiParam {String} password * 密码（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*		"callStatus": "SUCCEED",
	*		"errorCode": "No_Error",
	*  		"data": null,
	*  		"token": "SK1d7a4fe3-c2cd-417f-8f6f-bf7412592996",
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",//还有User_To_Be_Passed和User_UnPassed分别对应正在审核和审核没通过的用户，Error是用户名或密码错误
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="login", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> login(
    		@RequestParam(value = "userName", required = true) String userName,
            @RequestParam(value = "password",required = true) String password
    		) {
		
    	return userService.login(userName, password);
    }
	
	
	/**
	* @api {get} api/user/details 获取个人详情 -用户用
	* @apiName user_details
	* @apiGroup user
	*
	* @apiParam {String} token * 用户凭证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "SUCCEED",
	* 		"errorCode": "No_Error",
	* 		"data": {
	* 			"id": 1,
	* 			"userName": "1253024",
	* 			"password": "null",
	* 			"name": "潘瑞峰",
	* 			"state": 2,
	* 			"email": "123@qq.com",
	* 			"registerDate": 1488279855000,
	* 			"schoolYear": "2014",
	* 			"type": 1
	* 		},
	* 		"token": null,
	* 		"numberPerPage": 0,
	* 		"currentPage": 0,
	* 		"totalNumber": 0,
	* 		"totalPage": 0
	* 	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="details", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<User> getDetails(
    		@RequestParam(value = "token", required = true) String token
    		) {
		
    	return userService.getByToken(token);
    }
	
	
	
	/**
	* @api {post} api/user/update 用户修改个人信息 -用户用
	* @apiName user_update
	* @apiGroup user
	*
	* @apiParam {String} name * 用户名（必须）
	* @apiParam {String} email * 邮箱（必须）
	* @apiParam {String} schoolYear * 学籍（必须）
	* @apiParam {String} token * 用户凭证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "SUCCEED",
	* 		"errorCode": "No_Error",
	* 		"data": null,
	* 		"token": null,
	* 		"numberPerPage": 0,
	* 		"currentPage": 0,
	* 		"totalNumber": 0,
	* 		"totalPage": 0
	* 	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",//还有Null_Input_Error和Email_Format_Error
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="update", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateUser(
    		@RequestParam(value = "name", required = true) String name,
    		@RequestParam(value = "email", required = true) String email,
    		@RequestParam(value = "schoolYear", required = true) String schoolYear,
    		@RequestParam(value = "token", required = true) String token
    		) {
		
    	return userService.updateUser(name, email, schoolYear, token);
    }
	
	/**
	* @api {post} api/user/changePwd 用户修改密码 -用户和管理员管理员用
	* @apiName user_changePwd
	* @apiGroup user
	*
	* @apiParam {String} oldPassword * 旧密码（必须）
	* @apiParam {String} newPassword * 新密码（必须）
	* @apiParam {String} token * 用户凭证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "SUCCEED",
	* 		"errorCode": "No_Error",
	* 		"data": null,
	* 		"token": null,
	* 		"numberPerPage": 0,
	* 		"currentPage": 0,
	* 		"totalNumber": 0,
	* 		"totalPage": 0
	* 	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",//Null_Input_Error新密码为空，Error旧密码不正确或者token错误
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="changePwd", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> changePwd(
    		@RequestParam(value = "oldPassword", required = true) String oldPassword,
    		@RequestParam(value = "newPassword", required = true) String newPassword,
    		@RequestParam(value = "token", required = true) String token
    		) {
		
    	return userService.changePwd(oldPassword, newPassword, token);
    }
	
	/**
	* @api {get} api/user/getUserList 获取用户列表 -管理员用
	* @apiName user_getUserList
	* @apiGroup user
	*
	* @apiParam {int} state * 筛选参数，用户状态0-未通过，1-通过，2-正在审核（可选）
	* @apiParam {String} schoolYear * 学籍 类似2016（可选）
	* @apiParam {int} numberPerPage * 分页大小 （可选）
	* @apiParam {int} currentPage * 当前页（可选，不分页的话，取出所有的数据）
	* @apiParam {String} token *（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
    *		"callStatus": "SUCCEED",
  	*		"errorCode": "No_Error",
  	*		"data": [
    *			{
    *  			"id": 1,
    *  			"userName": "1253024",
    * 			"password": null,
    *  			"name": "潘瑞峰",
    *  			"state": 2,
    *  			"email": "123@qq.com",
    *  			"registerDate": 1488279855000,
    * 	 		"schoolYear": "2014",
    *  			"type": 1 //user-1,admin-0
    *			},
    *			{
    *  			"id": 2,
    *  			"userName": "12530241",
    *  			"password": null,
    *  			"name": "潘瑞峰",
    *  			"state": 2,
    *  			"email": "123@qq.com",
    *  			"registerDate": 1488279875000,
    *  			"schoolYear": "2014",
    *  			"type": 1
    *			}
  	*		],
  	*		"token": null,
  	*		"numberPerPage": -1,
  	*		"currentPage": -1,
  	*		"totalNumber": 2,
  	*		"totalPage": -2
	*		}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="getUserList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<User>> getUserList(
    		@RequestParam(value = "state", required = false) Integer state,
    		@RequestParam(value = "schoolYear", required = false) String schoolYear,
    		@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
    		@RequestParam(value = "token", required = true) String token
    		) {
		
    	return userService.getUserList(state, schoolYear, numberPerPage, currentPage, token);
    }
	
	
	/**
	* @api {get} api/user/getUserDetails 获取其他用户详情 -管理员用
	* @apiName user_getUserDetails
	* @apiGroup user
	*
	* @apiParam {long} userId * 用户id（必须）
	* @apiParam {String} token * 用户凭证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "SUCCEED",
	* 		"errorCode": "No_Error",
	* 		"data": {
	* 			"id": 1,
	* 			"userName": "1253024",
	* 			"password": "null",
	* 			"name": "潘瑞峰",
	* 			"state": 2,
	* 			"email": "123@qq.com",
	* 			"registerDate": 1488279855000,
	* 			"schoolYear": "2014",
	* 			"type": 1
	* 		},
	* 		"token": null,
	* 		"numberPerPage": 0,
	* 		"currentPage": 0,
	* 		"totalNumber": 0,
	* 		"totalPage": 0
	* 	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="getUserDetails", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<User> getUserDetails(
    		@RequestParam(value = "userId", required = true) Long userId,
    		@RequestParam(value = "token", required = true) String token
    		) {
		
    	return userService.getUserDetails(userId, token);
    }
	
	/**
	* @api {post} api/user/verify 审核注册的用户 -管理员用
	* @apiName user_verify
	* @apiGroup user
	*
	* @apiParam {long} userId * 用户id（必须）
	* @apiParam {int} state * 用户状态0-未通过，1-通过，2-正在审核（必须）
	* @apiParam {String} token * 用户凭证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "SUCCEED",
	* 		"errorCode": "No_Error",
	* 		"data": null,
	* 		"token": null,
	* 		"numberPerPage": 0,
	* 		"currentPage": 0,
	* 		"totalNumber": 0,
	* 		"totalPage": 0
	* 	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	* 		"callStatus": "FAILED",
	*		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	* 		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="verify", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> verify(
    		@RequestParam(value = "userId", required = true) Long userId,
    		@RequestParam(value = "state", required = true) Integer state,
    		@RequestParam(value = "token", required = true) String token
    		) {
		
    	return userService.verify(userId, state, token);
    }
}