package com.car.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.car.utils.HttpRequestUtil;

@Component("taskJob") 
public class TaskJob {
	
	@Scheduled(cron = "0 18 16 * * ?")
	public void job1() {
//		System.out.println("system starts");
//		String string = HttpRequestUtil.sendPost("http://localhost:8080/Car/api/user/login", "userName=1253024&password=12345678");
//		System.out.println(string);
//		System.out.println("system ends");
    }  

}
