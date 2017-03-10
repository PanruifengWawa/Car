package com.car.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.car.enums.Parameters;
import com.car.utils.HttpRequestUtil;

@Component("taskJob") 
public class TaskJob {
	
	@Scheduled(cron = "0 0 1 * * ?")
	public void myJobs() {
		System.out.println("system starts");
		String updateCareerPlanState = HttpRequestUtil.sendGet("http://localhost:8080/Car/api/timer/updateCareerPlanState?xtoken=" + Parameters.xtoken);
		System.out.println(updateCareerPlanState);
		
		String updateProjectPlanState = HttpRequestUtil.sendGet("http://localhost:8080/Car/api/timer/updateProjectPlanState?xtoken=" + Parameters.xtoken);
		System.out.println(updateProjectPlanState);
		
		String emailCareer = HttpRequestUtil.sendGet("http://localhost:8080/Car/api/timer/emailCareer?xtoken=" + Parameters.xtoken);
		System.out.println(emailCareer);
		
		String emailProject = HttpRequestUtil.sendGet("http://localhost:8080/Car/api/timer/emailProject?xtoken=" + Parameters.xtoken);
		System.out.println(emailProject);
		System.out.println("system ends");
    }  

}
