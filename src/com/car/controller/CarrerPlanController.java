package com.car.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.car.models.CareerPlan;
import com.car.service.CareerPlanService;
import com.car.utils.CareerPlanWrapper;
import com.car.utils.DataWrapper;

@Controller
@RequestMapping(value="/api/careerPlan")
public class CarrerPlanController {
	
	@Autowired
	CareerPlanService careerPlanService;
	
	
	
	/**
	* @api {post} api/careerPlan/add  制定个人计划-管理员用
	* @apiName careerPlan_add
	* @apiGroup careerPlan
	*
	* @apiParam {Long[]} userIds * 学生Id数组（必须）
	* @apiParam {String} title * 个人生涯title（必须）
	* @apiParam {String} content * 个人生涯content（必须）
	* @apiParam {String} plandateStr * 个人生涯plandate的字符串，格式:"yyyy-mm-dd"（必须）
	* @apiParam {String} token * 身份验证（必须）
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
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="add", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addCareerPlan(
    		@RequestParam(value = "userIds", required = true) Long[] userIds,
    		@RequestParam(value = "title",required = true) String title,
    		@RequestParam(value = "content",required = true) String content,
    		@RequestParam(value = "plandateStr",required = true) String plandateStr,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.addCareerPlan(userIds, title, content, plandateStr, token);
    }
	
	
	/**
	* @api {post} api/careerPlan/delete  删除个人计划-管理员用
	* @apiName careerPlan_delete
	* @apiGroup careerPlan
	*
	* @apiParam {Long[]} careerPlanIds * 个人生涯Id的数组（必须）
	* @apiParam {String} token * 身份验证（必须）
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
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="delete", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteCareerPlan(
    		@RequestParam(value = "careerPlanIds", required = true) Long[] careerPlanIds,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.deleteCareerPlan(careerPlanIds,  token);
    }
	
	
	
	/**
	* @api {get} api/careerPlan/getCareePlanList  获取个人计划列表-管理员，用户用
	* @apiName careerPlan_getCareePlanList
	* @apiGroup careerPlan
	*
	* @apiParam {Long} userId * 用户id（非必须。对于用户，不需要此参数，普通用户只能获取个人的生涯计划列表；对于管理员，可有此参数，若无，则获取所有）
	* @apiParam {Integer} state * 状态,未提交-1，待审核0，已通过1，逾期-2，未通过2（非必须）
	* @apiParam {String} schoolYear * 个人生涯的年级（非必须）
	* @apiParam {String} token * 身份验证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "No_Error",
  	*		"data": {
    *			"careerPlanForYears": [
    *		 	{
    *    				"year": 2017,
    *     				"careerPlans": [
    *      				{
    *        					"id": 16,
    *        					"userId": 5,
    *        					"title": "年计划",
    *        					"content": "计划啊",
    *        					"file": {
    *          						"id": 1,
    *          						"title": "无文件",
    *          						"content": null,
    *          						"url": null,
    *          						"owner": null,
    *          						"date": null
    *        					},
    *        					"state": -1,
    *        					"response": null,
    *        					"plandate": 1483200000000,
    *        					"comdate": null,
    *        					"resdate": null
    *      				},
    *      				{
    *        					"id": 16,
    *        					"userId": 5,
    *        					"title": "年计划",
    *        					"content": "计划啊",
    *        					"file": {
    *          						"id": 1,
    *          						"title": "无文件",
    *          						"content": null,
    *          						"url": null,
    *          						"owner": null,
    *          						"date": null
    *        					},
    *        					"state": -1,
    *        					"response": null,
    *        					"plandate": 1483200000000,
    *        					"comdate": null,
    *        					"resdate": null
    *      				}
    *    		 		]
    *  		  	},
    *  		  	{
    *    				"year": 2016,
    *   				"careerPlans": [
    *      				{
    *        					"id": 12,
    *        					"userId": 5,
    *        					"title": "年计划",
    *        					"content": "计划啊",
    *        					"file": {
    *          						"id": 1,
    *          						"title": "无文件",
    *          						"content": null,
    *          						"url": null,
    *          						"owner": null,
    *          						"date": null
    *        				 	},
    *        					"state": -1,
    *        					"response": null,
    *        					"plandate": 1451577600000,
    *        					"comdate": null,
    *        					"resdate": null
    *      				}
    *    				]
    *  			}
    *  			],
    *			"all": 4,
    *			"unCommit": 4,
    *			"toVerify": 0,
    *			"passed": 0,
    *			"overdue": 0,
    *			"unPassed": 0
    *		},
    *		"token": null,
    *		"numberPerPage": 0,
    *		"currentPage": 0,
    *		"totalNumber": 0,
    *		"totalPage": 0
    *	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="getCareePlanList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<CareerPlanWrapper> getCareePlan(
    		@RequestParam(value = "userId", required = false) Long userId,
    		@RequestParam(value = "state", required = false) Integer state,
    		@RequestParam(value = "schoolYear", required = false) String schoolYear,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.getCareerPlanList(userId, state, schoolYear,token);
    }
	
	
	/**
	* @api {post} api/careerPlan/commit  提交个人生涯文件 - 用户用
	* @apiName careerPlan_commit
	* @apiGroup careerPlan
	*
	* @apiParam {Long} careerPlanId * 个人生涯Id（必须）
	* @apiParam {file} file * 文件（必须）
	* @apiParam {String} token * 身份验证（必须）
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
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="commit", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> commit(
    		@RequestParam(value = "careerPlanId", required = true) Long careerPlanId,
    		@RequestParam(value = "file", required = true) MultipartFile file,
    		@RequestParam(value = "token",required = true) String token,
    		HttpServletRequest request
    		) {
		
    	return  careerPlanService.commit(careerPlanId,file, token,request);
    }
	
	
	/**
	* @api {post} api/careerPlan/verify  审核个人生涯文件 - 管理员用
	* @apiName careerPlan_verify
	* @apiGroup careerPlan
	*
	* @apiParam {Long} careerPlanId * 个人生涯Id（必须）
	* @apiParam {Integer} state * 审核状态 1-通过，2-未通过；只接受1和2（必须）
	* @apiParam {String} response * 未通过情况下的适用（非必须）
	* @apiParam {String} token * 身份验证（必须）
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
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="verify", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> verify(
    		@RequestParam(value = "careerPlanId", required = true) Long careerPlanId,
    		@RequestParam(value = "state", required = true) Integer state,
    		@RequestParam(value = "response",required = false) String response,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.verify(careerPlanId, state, response, token);
    }
	
	
	/**
	* @api {get} api/careerPlan/getById  根据id获取个人生涯 - 管理员，用户用
	* @apiName careerPlan_getById
	* @apiGroup careerPlan
	*
	* @apiParam {Long} careerPlanId * 个人生涯Id（必须，在权限方面，用户只能获取自己的个人生涯,如果企图获取他人的个人生涯，返回errorcode；而管理员可以获取任意）
	* @apiParam {String} token * 身份验证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "No_Error",
  	*		"data": {
    *			"id": 1,
    *			"userId": 1,
    *			"title": "年计划",
    *			"content": "计划啊",
    *			"file": {
    * 	 			"id": 2,
    *  				"title": "1253024_潘瑞峰1_1",
    *  				"content": null,
    *  				"url": "/career/b7b96eb1a49ff9fd43bb91b844b0e2c8.png",
    *  				"owner": "潘瑞峰1",
    *  				"date": 1488782152000
    *			},
    *			"state": 0,
    *			"response": null,
    *			"plandate": 1388505600000,
    *			"comdate": 1488729600000,
    *			"resdate": null
  	*		},
  	*		"token": null,
  	*		"numberPerPage": 0,
  	*		"currentPage": 0,
  	*		"totalNumber": 0,
  	*		"totalPage": 0
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "Error",
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="getById", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<CareerPlan> getById(
    		@RequestParam(value = "careerPlanId", required = true) Long careerPlanId,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.getById(careerPlanId, token);
    }

}
