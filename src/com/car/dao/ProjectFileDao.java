package com.car.dao;

import java.util.List;

import com.car.models.ProjectFile;
import com.car.utils.DataWrapper;

public interface ProjectFileDao {
	boolean addProjectFile(ProjectFile projectFile);
	boolean deleteProjectFile(ProjectFile projectFile);
	ProjectFile getById(Long id);
	
	DataWrapper<List<ProjectFile>> getProjectFileList(Long projectId,Integer numberPerPage, Integer currentPage);
}
