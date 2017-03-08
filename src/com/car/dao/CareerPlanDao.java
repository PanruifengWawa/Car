package com.car.dao;

import java.util.List;

import com.car.models.CareerPlan;

public interface CareerPlanDao {
	boolean saveCareerPlan(CareerPlan careerPlan);
	boolean deleteCareerPlan(Long careerPlanId);
	boolean updateCareerPlan(CareerPlan careerPlan);
	List<CareerPlan> getCareerPlan(Long userId,Integer state,String schoolYear);
	CareerPlan getById(Long id);
}
