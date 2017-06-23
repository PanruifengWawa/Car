package com.car.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.car.dao.FileDao;
import com.car.dao.ProjectMemberDao;
import com.car.dao.ProjectPlanDao;
import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.models.File;
import com.car.models.ProjectMember;
import com.car.models.ProjectPlan;
import com.car.models.ProjectPlanForYear;
import com.car.models.User;
import com.car.service.ProjectPlanService;
import com.car.utils.DataWrapper;
import com.car.utils.DateUtil;
import com.car.utils.FileUtils;
import com.car.utils.ProjectPlanWrapper;
import com.car.utils.SessionManager;

@Service("projectPlanService")
public class ProjectPlanServiceImpl implements ProjectPlanService {
	@Autowired
	ProjectPlanDao projectPlanDao;
	
	@Autowired
	ProjectMemberDao projectMemberDao;
	@Autowired
	FileDao fileDao;

	@Override
	public DataWrapper<Void> addProjectPlan(Long projectId, String title, String content, String plandateStr,
			String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			ProjectPlan projectPlan = new ProjectPlan();
			projectPlan.setId(null);
			projectPlan.setProjectId(projectId);
			projectPlan.setTitle(title);
			projectPlan.setContent(content);
			projectPlan.setState(Parameters.stateUncommit);
			projectPlan.setPlandate(DateUtil.parse(plandateStr));
			File file = new File();
			file.setId(new Long(1));
			projectPlan.setFile(file);
			if (!projectPlanDao.addProjectPlan(projectPlan)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteProjectPlan(Long projectPlanId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			if (!projectPlanDao.deleteProjectPlan(projectPlanId)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<ProjectPlan> getById(Long projectPlanId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<ProjectPlan> dataWrapper = new DataWrapper<ProjectPlan>(); 
		User user = SessionManager.getSession(token);
		if (user != null) {
			ProjectPlan projectPlan = projectPlanDao.getById(projectPlanId);
			if (user.getType() == Parameters.admin) {
				dataWrapper.setData(projectPlan);
			} else if(projectPlan !=null && user.getType() == Parameters.user) {
				ProjectMember projectMember = projectMemberDao.getByUserIdProjectId(user.getId(), projectPlan.getProjectId());
				if (projectMember != null) {
					dataWrapper.setData(projectPlan);
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
	public DataWrapper<ProjectPlanWrapper> getProjectPlanList(Long projectId, Integer state, Integer order,String token) {
		// TODO Auto-generated method stub
		DataWrapper<ProjectPlanWrapper> dataWrapper = new DataWrapper<ProjectPlanWrapper>(); 
		User user = SessionManager.getSession(token);
		List<ProjectPlan> projectPlanList = null;
		if (user != null) {
			if (user.getType() == Parameters.admin) {
				projectPlanList = projectPlanDao.getProjectPlanList(projectId, state,order);
			} else if (user.getType() == Parameters.user && projectId != null) {
				ProjectMember projectMember = projectMemberDao.getByUserIdProjectId(user.getId(), projectId);
				if (projectMember != null) {
					projectPlanList = projectPlanDao.getProjectPlanList(projectId, state,order);
				} else {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
			}
			
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		ProjectPlanWrapper projectPlanWrapper = new ProjectPlanWrapper();
		Integer unCommit = 0;
		Integer toVerify = 0;
		Integer passed = 0;
		Integer overdue = 0;
		Integer unPassed = 0;
		
		if (projectPlanList != null) {
			Map<Integer, ProjectPlanForYear> map = new TreeMap<Integer, ProjectPlanForYear>(
	                new Comparator<Integer>() {
	                    public int compare(Integer obj1, Integer obj2) {
	                        // 升序
	                    	if (order == 0) {
	                    		return obj1.compareTo(obj2);
							} else { //降序排序
								return obj2.compareTo(obj1);
							}
	                        
	                    }
	                });
			for(ProjectPlan projectPlan: projectPlanList) {
				switch (projectPlan.getState()) {
				case -2:
					overdue++;
					break;
				case -1:
					unCommit++;
					break;
				case 0:
					toVerify++;
					break;
				case 1:
					passed++;
					break;
				case 2:
					unPassed++;
					break;

				default:
					break;
				}
				Integer year = DateUtil.getYear(projectPlan.getPlandate());
				ProjectPlanForYear projectPlanForYear = null;
				if (map.get(year) == null) {
					List<ProjectPlan> projectPlan2 = new ArrayList<ProjectPlan>();
					projectPlan2.add(projectPlan);
					
					projectPlanForYear = new ProjectPlanForYear();
					projectPlanForYear.setYear(year);
					projectPlanForYear.setProjectPlan(projectPlan2);
				} else {
					projectPlanForYear =  map.get(year);
					projectPlanForYear.getProjectPlan().add(projectPlan);
				}
				map.put(year, projectPlanForYear);
			}
			
			List<ProjectPlanForYear> projectPlanForYear = new ArrayList<ProjectPlanForYear>();
			for(ProjectPlanForYear e: map.values()) {
				projectPlanForYear.add(e);
			}
			projectPlanWrapper.setProjectPlanForYear(projectPlanForYear);
		}
		projectPlanWrapper.setAll(projectPlanList == null ? 0: projectPlanList.size());
		projectPlanWrapper.setPassed(passed);
		projectPlanWrapper.setOverdue(overdue);
		projectPlanWrapper.setUnPassed(unPassed);
		projectPlanWrapper.setUnCommit(unCommit);
		projectPlanWrapper.setToVerify(toVerify);
		dataWrapper.setData(projectPlanWrapper);
		
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> commit(Long projectPlanId, MultipartFile file, String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User user = SessionManager.getSession(token);
		if (user != null && projectPlanId != null && file != null) {
			ProjectPlan projectPlan = projectPlanDao.getById(projectPlanId);
			if (projectPlan != null) {
				ProjectMember projectMember = projectMemberDao.getByUserIdProjectId(user.getId(), projectPlan.getProjectId());
				if (projectMember != null || user.getType() == Parameters.admin) {
					String filePath = FileUtils.saveFile(file, "projectPlan", request);
					if (filePath != null) {
						File fileEntity = new File();
						fileEntity.setTitle(file.getOriginalFilename());
						fileEntity.setUrl(Parameters.fileSrc + filePath);
						fileEntity.setDate(new Timestamp(System.currentTimeMillis()));
						fileEntity.setId(null);
						fileEntity.setOwner(user.getName());
						fileDao.saveFile(fileEntity);
						if (fileEntity.getId() != null) {
							projectPlan.setFile(fileEntity);
							projectPlan.setState(Parameters.stateToVerify);
							projectPlan.setComdate(new Date());
							if (!projectPlanDao.updateProjectPlan(projectPlan)) {
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
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> verify(Long projectPlanId, Integer state, String response, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (!(state == Parameters.statePassed || state == Parameters.stateUnpassed)) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			return dataWrapper;
		}
		if (admin != null && admin.getType() == Parameters.admin  && projectPlanId != null && state != null) {
			ProjectPlan projectPlan = projectPlanDao.getById(projectPlanId);
			if (projectPlan != null) {
				if (state == Parameters.stateUnpassed) {
					projectPlan.setResponse(response);
					projectPlan.setResdate(new Date());
				}
				projectPlan.setState(state);
				if (!projectPlanDao.updateProjectPlan(projectPlan)) {
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

}
