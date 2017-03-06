package com.car.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.car.service.CareerPlanService;
import com.car.utils.CareerPlanWrapper;
import com.car.utils.DataWrapper;

@Controller
@RequestMapping(value="/api/careerPlan")
public class CarrerPlanController {
	
	@Autowired
	CareerPlanService careerPlanService;
	
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
	
	
	@RequestMapping(value="delete", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteCareerPlan(
    		@RequestParam(value = "careerPlanIds", required = true) Long[] careerPlanIds,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.deleteCareerPlan(careerPlanIds,  token);
    }
	
	
	@RequestMapping(value="getCareePlan", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<CareerPlanWrapper> getCareePlan(
    		@RequestParam(value = "userId", required = false) Long userId,
    		@RequestParam(value = "state", required = false) Integer state,
    		@RequestParam(value = "token",required = true) String token
    		) {
		
    	return  careerPlanService.getCareerPlanList(userId, state, token);
    }
	
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

}
