package com.car.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.car.models.ProjectPlan;
import com.car.service.ProjectPlanService;
import com.car.utils.DataWrapper;
import com.car.utils.ProjectPlanWrapper;

@Controller
@RequestMapping(value="/api/projectPlan")
public class ProjectPlanController {
	
	@Autowired
	ProjectPlanService projectPlanService;
	
	
	/**
	* @api {post} api/projectPlan/add  制定项目规划-管理员用
	* @apiName projectPlan_add
	* @apiGroup projectPlan
	*
	* @apiParam {Long} projectId * 项目Id（必须）
	* @apiParam {String} title * 项目计划title（必须）
	* @apiParam {String} content * 项目计划content（必须）
	* @apiParam {String} plandateStr * 项目plandate的字符串，格式:"yyyy-mm-dd"（必须）
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
    public DataWrapper<Void> addProjectPlan(
    		@RequestParam(value = "projectId", required = true) Long projectId,
    		@RequestParam(value = "title", required = true) String title,
    		@RequestParam(value = "content", required = true) String content,
    		@RequestParam(value = "plandateStr", required = true) String plandateStr,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectPlanService.addProjectPlan(projectId, title, content, plandateStr, token);
    }
	
	/**
	* @api {post} api/projectPlan/delete  删除项目规划-管理员用
	* @apiName projectPlan_delete
	* @apiGroup projectPlan
	*
	* @apiParam {Long} projectPlanId * 项目规划Id（必须）
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
    public DataWrapper<Void> deleteProjectPlan(
    		@RequestParam(value = "projectPlanId", required = true) Long projectPlanId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectPlanService.deleteProjectPlan(projectPlanId, token);
    }
	
	
	/**
	* @api {get} api/projectPlan/getById  根据id获取项目规划 - 管理员，用户用
	* @apiName projectPlan_getById
	* @apiGroup projectPlan
	*
	* @apiParam {Long} projectPlanId * 项目规划Id（必须，只有项目成员和管理员能获取）
	* @apiParam {String} token * 身份验证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "No_Error",
  	*		"data": {
    *			"id": 1,
    *			"projectId": 1,
    *			"title": "项目1——计划1",
    *			"file": {
    *  				"id": 1,
    *  				"title": "无文件",
    *  				"content": null,
    *  				"url": null,
    *  				"owner": null,
    *  				"date": null
    *			},
    *			"content": "无",
    *			"state": -1,
    *			"response": null,
    *			"plandate": 1488902400000,
    *			"comdate": null,
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
    public DataWrapper<ProjectPlan> getById(
    		@RequestParam(value = "projectPlanId", required = true) Long projectPlanId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectPlanService.getById(projectPlanId, token);
    }
	
	
	
	/**
	* @api {get} api/projectPlan/getProjectPlanList  获取项目规划列表 - 管理员，用户用
	* @apiName projectPlan_getProjectPlanList
	* @apiGroup projectPlan
	*
	* @apiParam {Long} projectId * 项目Id（必须，只有项目成员和管理员能获取）
	* @apiParam {int} state * 状态，未提交-1，待审核0，已通过1，逾期-2，未通过2（非必须）
	* @apiParam {String} token * 身份验证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*		"callStatus": "SUCCEED",
	*		"errorCode": "No_Error",
	*		"data": {
	*			"projectPlanForYear": [
	* 				{
	*    				"year": 2018,
	*   				"projectPlan": [
	*     					{
	*       					"id": 6,
	*       					"projectId": 1,
	*       					"title": "项目1——计划5",
	*      				 		"file": {
	*         						"id": 1,
	*          						"title": "无文件",
	*         						"content": null,
	*         						"url": null,
	*         						"owner": null,
	*         						"date": null
	*        					},
	*       		 			"content": "无",
	*       					"state": -1,
	*       					"response": null,
	*       					"plandate": 1531238400000,
	*       					"comdate": null,
	*       					"resdate": null
	*     					}
	*   				]
	*  				},
	* 				{
	*    				"year": 2017,
	*    				"projectPlan": [
	*      					{
	*       					"id": 1,
	*         				"projectId": 1,
	*        					"title": "项目1——计划1",
	*        					"file": {
	*         						"id": 1,
	*         						"title": "无文件",
	*         			 			"content": null,
	*         						"url": null,
    *          						"owner": null,
    *          						"date": null
    *        					},
    *        					"content": "无",
    *        					"state": -1,
    *        					"response": null,
    *        					"plandate": 1488902400000,
    *        					"comdate": null,
    *        					"resdate": null
    *      					}
    *    				]
    *  				}
    *			],
    *			"all": 2,
    *			"unCommit": 2,
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
	@RequestMapping(value="getProjectPlanList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ProjectPlanWrapper> getProjectPlanList(
    		@RequestParam(value = "projectId", required = true) Long projectId,
    		@RequestParam(value = "state", required = false) Integer state,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectPlanService.getProjectPlanList(projectId,state, token);
    }
	
	
	/**
	* @api {post} api/projectPlan/commit  提交项目规划文件-管理员，用户用
	* @apiName projectPlan_commit
	* @apiGroup projectPlan
	*
	* @apiParam {Long} projectPlanId * 项目规划Id（必须）
	* @apiParam {file} file * 文件（必须）
	* @apiParam {String} token * 身份验证（必须，只有项目成员和管理员能用）
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
    		@RequestParam(value = "projectPlanId", required = true) Long projectPlanId,
    		@RequestParam(value = "file", required = true) MultipartFile file,
    		@RequestParam(value = "token",required = true) String token,
    		HttpServletRequest request
    		) {
		
    	return projectPlanService.commit(projectPlanId,file, token,request);
    }
	
	/**
	* @api {post} api/projectPlan/verify  审核项目规划文件-管理员用
	* @apiName projectPlan_verify
	* @apiGroup projectPlan
	*
	* @apiParam {Long} projectPlanId * 项目规划Id（必须）
	* @apiParam {Integer} state * 审核状态 1-通过，2-未通过；只接受1和2（必须）
	* @apiParam {String} response * 未通过情况下的适用（非必须）
	* @apiParam {String} token * 身份验证
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
    		@RequestParam(value = "projectPlanId", required = true) Long projectPlanId,
    		@RequestParam(value = "state", required = true) Integer state,
    		@RequestParam(value = "response",required = false) String response,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectPlanService.verify(projectPlanId,state, response,token);
    }


}
