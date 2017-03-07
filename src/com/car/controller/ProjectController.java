package com.car.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.car.models.Project;
import com.car.models.ProjectFile;
import com.car.models.User;
import com.car.service.ProjectService;
import com.car.utils.DataWrapper;

@Controller
@RequestMapping(value="/api/project")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;
	
	
	/**
	* @api {post} api/project/add 创建项目 -管理员用
	* @apiName project_add
	* @apiGroup project
	*
	* @apiParam {String} name * 项目名称（必须）
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
    public DataWrapper<Void> addProject (
    		@RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.addProject(name, token);
    }
	
	
	/**
	* @api {post} api/project/delete 删除项目 -管理员用
	* @apiName project_delete
	* @apiGroup project
	*
	* @apiParam {Long} projectId * 项目Id（必须）
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
    public DataWrapper<Void> deleteProject(
    		@RequestParam(value = "projectId", required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.deleteProject(projectId, token);
    }
	
	
	/**
	* @api {post} api/project/addMember 项目添加成员 -管理员用
	* @apiName project_addMember
	* @apiGroup project
	*
	* @apiParam {Long} projectId * 项目Id（必须）
	* @apiParam {Long} userId * 用户Id（必须）
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
	@RequestMapping(value="addMember", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMember (
    		@RequestParam(value = "projectId", required = true) Long projectId,
    		@RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.addMember(projectId, userId, token);
    }
	
	/**
	* @api {post} api/project/getProjectList 获取项目列表 -管理员，用户用
	* @apiName project_getProjectList
	* @apiGroup project
	*
	* @apiParam {String} name * 项目名称（非必须，模糊搜索）
	* @apiParam {int} numberPerPage * 分页大小 （可选）
	* @apiParam {int} currentPage * 当前页（可选，不分页的话，取出所有的数据）
	* @apiParam {String} token * 身份验证（必须，对于普通用户，获得个人所在的项目列表；对于管理员，获得所有的项目列表）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "No_Error",
  	*		"data": [
    *			{
    *  				"id": 1,
    *  				"name": "项目1",
    *  				"regdate": 1488816000000
    *			},
    *			{
    *  				"id": 2,
    *  				"name": "项目2",
    *  				"regdate": 1488816000000
    *			},
    *			{
    *  				"id": 3,
    *  				"name": "项目3",
    *  				"regdate": 1488816000000
    *			}
  	*		],
  	*		"token": null,
  	*		"numberPerPage": -1,
  	*		"currentPage": -1,
  	*		"totalNumber": 3,
  	*		"totalPage": -3
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
	@RequestMapping(value="getProjectList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Project>> getProjectList (
    		@RequestParam(value = "name",required = false) String name,
    		@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.getProjectList(name,numberPerPage,currentPage,token);
    }

	/**
	* @api {post} api/project/removeMember 项目删除成员 -管理员用
	* @apiName project_removeMember
	* @apiGroup project
	*
	* @apiParam {Long} projectId * 项目Id（必须）
	* @apiParam {Long} userId * 用户Id（必须）
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
	@RequestMapping(value="removeMember", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> removeMember (
    		@RequestParam(value = "projectId", required = true) Long projectId,
    		@RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.removeMember(projectId, userId, token);
    }
	
	
	/**
	* @api {get} api/project/getMemberList 获取项目成员 -管理员用,用户用
	* @apiName project_getMemberListList
	* @apiGroup project
	*
	* @apiParam {Long} projectId * 项目Id（必须）
	* @apiParam {String} token * 身份验证（必须）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*		"callStatus": "SUCCEED",
	*		"errorCode": "No_Error",
	*		"data": [
	*			{
	* 				"id": 1,
	*  				"userName": "1253024",
	* 				"password": null,
	* 				"name": "潘瑞峰1",
	*  				"state": 1,
	* 				"email": "163@163.com",
	* 				"registerDate": 1488279855000,
	* 				"schoolYear": "2012",
	* 				"type": 1
	*			},
	*			{
	* 				"id": 2,
	*				"userName": "12530241",
	* 				"password": null,
	* 				"name": "潘瑞峰",
	* 				"state": 1,
	*  				"email": "123@qq.com",
	* 				"registerDate": 1488279875000,
	* 				"schoolYear": "2014",
	* 				"type": 1
	*			}
	*		],
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
	@RequestMapping(value="getMemberList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<User>> getMemberListList (
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.getMemberList(projectId,token);
    }
	
	
	/**
	* @api {post} api/project/uploadProjectFile 项目文件上传 -管理员用，用户用
	* @apiName project_uploadProjectFile
	* @apiGroup project
	*
	* @apiParam {Long} projectId * 项目Id（必须）
	* @apiParam {file} file * 文件（必须）
	* @apiParam {String} token * 身份验证（必须；项目成员或者管理员能上传文件）
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
	@RequestMapping(value="uploadProjectFile", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadProjectFile (
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "file",required = true) MultipartFile file,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request
    		) {
		
    	return projectService.uploadProjectFile(projectId,file,token,request);
    }
	
	/**
	* @api {post} api/project/deleteProjectFile 项目文件删除 -管理员，用户用
	* @apiName project_deleteProjectFile
	* @apiGroup project
	*
	* @apiParam {Long} projectFileId * 项目文件Id（必须；注意不是文件Id，它由**获取项目文件列表接口**所得）
	* @apiParam {String} token * 身份验证（必须，项目成员或管理员能删除文件）
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
	@RequestMapping(value="deleteProjectFile", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteProjectFile (
    		@RequestParam(value = "projectFileId",required = true) Long projectFileId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.deleteProjectFile(projectFileId,token);
    }
	
	/**
	* @api {get} api/project/getProjectFileList 获取项目文件列表 -管理员，用户用
	* @apiName project_getProjectFileList
	* @apiGroup project
	*
	* @apiParam {Long} projectId * 项目Id（必须）
	* @apiParam {int} numberPerPage * 分页大小 （可选）
	* @apiParam {int} currentPage * 当前页（可选，不分页的话，取出所有的数据）
	* @apiParam {String} token * 身份验证（必须；项目人员或管理员能获取）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*		"callStatus": "SUCCEED",
	*		"errorCode": "No_Error",
	*		"data": [
	*			{
	* 				"id": 3,
	*  				"projectId": 1,
	* 				"file": {
	*   				"id": 9,
	*   				"title": "登陆选中_#7f6866.png",
	*    				"content": null,
	*   				"url": "/project/1/4eff26e852c9a21b463e8cc42fc4f94a.png",
	*   				"owner": "admin",
	*   				"date": 1488875009000
	*  				}
	*			}
	*		],
	*		"token": null,
	*		"numberPerPage": -1,
	*		"currentPage": -1,
	*		"totalNumber": 1,
	*		"totalPage": -1
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
	@RequestMapping(value="getProjectFileList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectFile>> getProjectFileList (
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.getProjectFileList(projectId,numberPerPage,currentPage,token);
    }
}
