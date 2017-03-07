package com.car.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.car.models.Project;
import com.car.models.ProjectFile;
import com.car.models.User;
import com.car.utils.DataWrapper;

public interface ProjectService {
	DataWrapper<Void> addProject(String name,String token);
	DataWrapper<Void> deleteProject(Long projectId,String token);
	DataWrapper<Void> addMember(Long projectId,Long userId,String token);
	DataWrapper<Void> removeMember(Long projectId,Long userId,String token);
	
	DataWrapper<List<Project>> getProjectList(String name,String token);
	DataWrapper<List<User>> getMemberListList(Long projectId,String token);
	
	DataWrapper<Void> uploadProjectFile(Long projectId,MultipartFile file,String token,HttpServletRequest request);
	DataWrapper<Void> deleteProjectFile(Long projectFileId,String token);
	
	DataWrapper<List<ProjectFile>> getProjectFileList(Long projectId,Integer numberPerPage,Integer currentPage,String token);
}
