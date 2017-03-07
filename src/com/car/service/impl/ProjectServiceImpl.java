package com.car.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.car.dao.FileDao;
import com.car.dao.ProjectDao;
import com.car.dao.ProjectFileDao;
import com.car.dao.ProjectMemberDao;
import com.car.dao.UserDao;
import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.models.File;
import com.car.models.Project;
import com.car.models.ProjectFile;
import com.car.models.ProjectMember;
import com.car.models.User;
import com.car.service.ProjectService;
import com.car.utils.DataWrapper;
import com.car.utils.FileUtils;
import com.car.utils.SessionManager;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	ProjectFileDao projectFileDao;
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	ProjectMemberDao projectMemberDao;

	@Override
	public DataWrapper<Void> addProject(String name, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			Project project = new Project();
			project.setId(null);
			project.setName(name);
			project.setRegdate(new Date());
			if (!projectDao.addProject(project)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteProject(Long projectId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			
			if (!projectDao.deleteProject(projectId)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> addMember(Long projectId, Long userId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin && projectId != null && userId != null) {
			
			ProjectMember projectMember = new ProjectMember();
			projectMember.setId(null);
			projectMember.setProjectId(projectId);
			projectMember.setUserId(userId);
			if (!projectMemberDao.addProjectMember(projectMember)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> removeMember(Long projectId, Long userId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin && projectId != null && userId != null) {
			
			if (!projectMemberDao.deleteProjectMember(userId, projectId)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Project>> getProjectList(String name, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<Project>> dataWrapper = new DataWrapper<List<Project>>(); 
		User user = SessionManager.getSession(token);
		if (user != null) {
			if (user.getType() == Parameters.admin) {
				dataWrapper = projectDao.getProjectList(name, null);
			} else {
				dataWrapper = projectDao.getProjectList(name, user.getId());
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<User>> getMemberListList(Long projectId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>(); 
		User user = SessionManager.getSession(token);
		if (user != null && projectId != null) {
			dataWrapper = userDao.getProjectMember(projectId);
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> uploadProjectFile(Long projectId, MultipartFile file, String token,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User user = SessionManager.getSession(token);
		if (user != null && projectId != null && file != null) {
			ProjectMember projectMember = projectMemberDao.getByUserIdProjectId(user.getId(), projectId);
			if (projectMember != null) {
				String filePath = FileUtils.saveFile(file, "project/" + projectId, request);
				File fileEntity = new File();
				fileEntity.setTitle(file.getOriginalFilename());
				fileEntity.setUrl(filePath);
				fileEntity.setDate(new Timestamp(System.currentTimeMillis()));
				fileEntity.setId(null);
				fileEntity.setOwner(user.getName());
				fileDao.saveFile(fileEntity);
				if (fileEntity.getId() != null) {
					ProjectFile projectFile = new ProjectFile();
					projectFile.setId(null);
					projectFile.setProjectId(projectId);
					projectFile.setFile(fileEntity);
					if (!projectFileDao.addProjectFile(projectFile)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				} else {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteProjectFile(Long projectFileId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User user = SessionManager.getSession(token);
		if (user != null && projectFileId != null) {
			ProjectFile projectFile = projectFileDao.getById(projectFileId);
			if (projectFile != null) {
				ProjectMember projectMember = projectMemberDao.getByUserIdProjectId(user.getId(), projectFile.getProjectId());
				if (projectMember != null) {
					if (!projectFileDao.deleteProjectFile(projectFile)) {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				} else {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
				
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<ProjectFile>> getProjectFileList(Long projectId,Integer numberPerPage,Integer currentPage,String token) {
		// TODO Auto-generated method stub
		DataWrapper<List<ProjectFile>> dataWrapper = new DataWrapper<List<ProjectFile>>(); 
		User user = SessionManager.getSession(token);
		if (user != null && projectId != null) {
			ProjectMember projectMember = projectMemberDao.getByUserIdProjectId(user.getId(), projectId);
			if (projectMember != null) {
				dataWrapper = projectFileDao.getProjectFileList(projectId, numberPerPage, currentPage);
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

}
