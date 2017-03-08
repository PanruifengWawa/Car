package com.car.service;

import com.car.utils.DataWrapper;

public interface TimerJobsService {
	DataWrapper<Void> updateCareerPlanState(String xtoken);
	DataWrapper<Void> updateProjectPlanState(String xtoken);
	
	DataWrapper<Void> emailCareer(String xtoken);
	
	DataWrapper<Void> emailProject(String xtoken);

}
