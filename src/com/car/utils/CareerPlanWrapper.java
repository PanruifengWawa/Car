package com.car.utils;

import java.util.List;

import com.car.models.CareerPlanForYear;

public class CareerPlanWrapper {
	private List<CareerPlanForYear> careerPlanForYears;
	private Integer all;
	private Integer unCommit;
	private Integer toVerify;
	private Integer passed;
	private Integer overdue;
	private Integer unPassed;
	public List<CareerPlanForYear> getCareerPlanForYears() {
		return careerPlanForYears;
	}
	public void setCareerPlanForYears(List<CareerPlanForYear> careerPlanForYears) {
		this.careerPlanForYears = careerPlanForYears;
	}
	public Integer getAll() {
		return all;
	}
	public void setAll(Integer all) {
		this.all = all;
	}
	public Integer getUnCommit() {
		return unCommit;
	}
	public void setUnCommit(Integer unCommit) {
		this.unCommit = unCommit;
	}
	public Integer getToVerify() {
		return toVerify;
	}
	public void setToVerify(Integer toVerify) {
		this.toVerify = toVerify;
	}
	public Integer getPassed() {
		return passed;
	}
	public void setPassed(Integer passed) {
		this.passed = passed;
	}
	public Integer getOverdue() {
		return overdue;
	}
	public void setOverdue(Integer overdue) {
		this.overdue = overdue;
	}
	public Integer getUnPassed() {
		return unPassed;
	}
	public void setUnPassed(Integer unPassed) {
		this.unPassed = unPassed;
	}
	
	

}
