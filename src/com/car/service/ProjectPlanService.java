package com.car.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.car.models.ProjectPlan;
import com.car.utils.DataWrapper;
import com.car.utils.ProjectPlanWrapper;

public interface ProjectPlanService {
	DataWrapper<Void> addProjectPlan(Long projectId,String title,String content,String plandateStr,String token);
	DataWrapper<Void> deleteProjectPlan(Long projectPlanId,String token);
	DataWrapper<ProjectPlan> getById(Long projectPlanId,String token);
	DataWrapper<ProjectPlanWrapper> getProjectPlanList(Long projectId,Integer state,Integer order,String token);
	DataWrapper<Void> commit(Long projectPlanId,MultipartFile file, String token,HttpServletRequest request);
	DataWrapper<Void> verify(Long projectPlanId,Integer state,String response,String token);

}
