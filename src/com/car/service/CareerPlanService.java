package com.car.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.car.models.CareerPlan;
import com.car.utils.CareerPlanWrapper;
import com.car.utils.DataWrapper;

public interface CareerPlanService {
	DataWrapper<Void> addCareerPlan(Long[] userIds,String title,String content,String plandateStr,String token);
	DataWrapper<Void> deleteCareerPlan(Long[] careerPlanIds,String token);
	DataWrapper<CareerPlanWrapper> getCareerPlanList(Long userId,Integer state,String schoolYear,String token);
	DataWrapper<Void> commit(Long careerPlanId,MultipartFile file,String token,HttpServletRequest request);
	DataWrapper<Void> verify(Long careerPlanId,Integer state,String response,String token);
	DataWrapper<CareerPlan> getById(Long careerPlanId,String token);
}
