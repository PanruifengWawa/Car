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

import com.car.models.Material;
import com.car.service.MaterialService;
import com.car.utils.DataWrapper;

@Controller
@RequestMapping(value="/api/material")
public class MaterialController {
	@Autowired
	MaterialService materialService;
	
	
	/**
	* @api {post} api/material/upload 上传资料 -管理员用
	* @apiName material_upload
	* @apiGroup material
	*
	* @apiParam {file} file * 文件本身（必须）
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
	*  		"errorCode": "Error",//还有Null_Input_Error
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="upload", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadMaterial(
    		@RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request
    		) {
		
    	return materialService.uploadMaterial(file, token, request);
    }
	/**
	* @api {get} api/material/delete 删除资料 -管理员用
	* @apiName material_delete
	* @apiGroup material
	*
	* @apiParam {Long} id * material id（必须）
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
	*  		"errorCode": "Error",r
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="delete", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMaterial(
    		@RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request
    		) {
		
    	return materialService.deleteMaterial(id, token, request);
    }
	
	
	
	/**
	* @api {get} api/material/getMaterialList 获取资料列表 -管理员，用户用
	* @apiName material_getMaterialList
	* @apiGroup material
	*
	* @apiParam {int} numberPerPage * 分页大小 （可选）
	* @apiParam {int} currentPage * 当前页（可选，不分页的话，取出所有的数据）
	*
	* @apiSuccessExample {json} Success-Response:
	* 	HTTP/1.1 200 ok
	* 	{
  	*		"callStatus": "SUCCEED",
  	*		"errorCode": "No_Error",
  	*		"data": [
    *			{
    *  				"id": 2,
    *  				"file": {
    *    					"id": 2,
    *    					"title": "导航栏 #010022.jpg",
    *    					"content": null,
    *    					"url": "/material/c05c652cd06ab3d7d1cbb7d023695d69.jpg",
    *    					"owner": "prf",
    *    					"date": 1488366059000
    *  				}
    *			},
    *			{
    *  				"id": 3,
    *  				"file": {
    *    					"id": 3,
    *    					"title": "登陆-空白_03.png",
    *    					"content": null,
    *    					"url": "/material/cb363e7158f54109c8e3ba689abc782a.png",
    *    					"owner": "prf",
    *    					"date": 1488366154000
    *  				}
    *			}
  	*		],
  	*		"token": null,
  	*		"numberPerPage": -1,
  	*		"currentPage": -1,
  	*		"totalNumber": 2,
  	*		"totalPage": -2
	*	}
	*
	* @apiSuccessExample {json} Error-Response:
	* 	HTTP/1.1 200 ok
	* 	{
	*  		"callStatus": "FAILED",
	*  		"errorCode": "Error",r
	*  		"data": null,
	*  		"token": null,
	*  		"numberPerPage": 0,
	*  		"currentPage": 0,
	*  		"totalNumber": 0,
	*  		"totalPage": 0
	*	}
	**/
	@RequestMapping(value="getMaterialList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Material>> getMaterialList(
    		@RequestParam(value = "numberPerPage", required = false) Integer numberPerPage,
    		@RequestParam(value = "currentPage", required = false) Integer currentPage
    		) {
		
    	return materialService.getMaterialList(numberPerPage, currentPage);
    }

}
