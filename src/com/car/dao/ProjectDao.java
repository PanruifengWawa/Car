package com.car.dao;

import java.util.List;

import com.car.models.Project;
import com.car.utils.DataWrapper;

public interface ProjectDao {
	boolean addProject(Project project);
	boolean deleteProject(Long id);
	DataWrapper<List<Project>> getProjectList(String name,Long userId,Integer numberPerPage,Integer currentPage);
}
