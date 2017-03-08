package com.car.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.car.enums.Parameters;
import com.car.utils.HttpRequestUtil;

@Component("taskJob") 
public class TaskJob {
	
	@Scheduled(cron = "0 5 21 * * ?")
	public void myJobs() {
		System.out.println("system starts");
		String string = HttpRequestUtil.sendGet("http://localhost:8080/Car/api/timer/updateCareerPlanState?xtoken=" + Parameters.xtoken);
		System.out.println(string);
		System.out.println("system ends");
    }  

}
