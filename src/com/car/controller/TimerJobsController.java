package com.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.service.TimerJobsService;
import com.car.utils.DataWrapper;

@Controller
@RequestMapping(value="/api/timer")
public class TimerJobsController {
	
	@Autowired
	TimerJobsService timerJobsService;
	
	@RequestMapping(value="updateCareerPlanState", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> updateCareerPlanState(
    		 @RequestParam(value = "xtoken",required = true) String xtoken
    		 ) {
		
    	return timerJobsService.updateCareerPlanState(xtoken);
    }
	
	@RequestMapping(value="updateProjectPlanState", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> updateProjectPlanState(
    		 @RequestParam(value = "xtoken",required = true) String xtoken
    		) {
		
    	return timerJobsService.updateProjectPlanState(xtoken);
    }
	
	@RequestMapping(value="emailCareer", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> emailCareer(
    		 @RequestParam(value = "xtoken",required = true) String xtoken
    		) {
		
    	return timerJobsService.emailCareer(xtoken);
    }
	
	@RequestMapping(value="emailProject", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> emailProject(
    		 @RequestParam(value = "xtoken",required = true) String xtoken
    		) {
		
    	return timerJobsService.emailProject(xtoken);
    }

}
