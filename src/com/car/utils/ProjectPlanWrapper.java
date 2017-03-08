package com.car.utils;

import java.util.List;

import com.car.models.ProjectPlanForYear;

public class ProjectPlanWrapper {
	private List<ProjectPlanForYear> projectPlanForYear;
	private Integer all;
	private Integer unCommit;
	private Integer toVerify;
	private Integer passed;
	private Integer overdue;
	private Integer unPassed;
	public List<ProjectPlanForYear> getProjectPlanForYear() {
		return projectPlanForYear;
	}
	public void setProjectPlanForYear(List<ProjectPlanForYear> projectPlanForYear) {
		this.projectPlanForYear = projectPlanForYear;
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
