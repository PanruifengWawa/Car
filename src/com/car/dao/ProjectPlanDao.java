package com.car.dao;

import java.util.List;

import com.car.models.ProjectPlan;

public interface ProjectPlanDao {
	boolean addProjectPlan(ProjectPlan projectPlan);
	boolean deleteProjectPlan(Long id);
	boolean updateProjectPlan(ProjectPlan projectPlan);
	ProjectPlan getById(Long id);
	
	List<ProjectPlan> getProjectPlanList(Long projectId,Integer state);
	
	boolean updateProjectPlanState();
	
	List<ProjectPlan> getOverDuePlan();
	
}
