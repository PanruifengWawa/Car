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
	* @api {post} api/project/delete 删除 -管理员用
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
	
	@RequestMapping(value="getProjectList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Project>> getProjectList (
    		@RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.getProjectList(name,token);
    }

	@RequestMapping(value="removeMember", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> removeMember (
    		@RequestParam(value = "projectId", required = true) Long projectId,
    		@RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.removeMember(projectId, userId, token);
    }
	
	@RequestMapping(value="getMemberListList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<User>> getMemberListList (
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.getMemberListList(projectId,token);
    }
	
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
	
	@RequestMapping(value="deleteProjectFile", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteProjectFile (
    		@RequestParam(value = "projectFileId",required = true) Long projectFileId,
            @RequestParam(value = "token",required = true) String token
    		) {
		
    	return projectService.deleteProjectFile(projectFileId,token);
    }
	
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
