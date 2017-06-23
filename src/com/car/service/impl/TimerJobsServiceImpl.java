package com.car.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.CareerPlanDao;
import com.car.dao.ProjectPlanDao;
import com.car.dao.UserDao;
import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.models.CareerPlan;
import com.car.models.ProjectPlan;
import com.car.models.User;
import com.car.service.TimerJobsService;
import com.car.utils.DataWrapper;
import com.car.utils.DateUtil;
import com.car.utils.MailUtil;

@Service("timerJobsService")
public class TimerJobsServiceImpl implements TimerJobsService {
	@Autowired
	CareerPlanDao careerPlanDao;
	
	@Autowired
	ProjectPlanDao projectPlanDao;
	
	@Autowired
	UserDao userDao;

	@Override
	public DataWrapper<Void> updateCareerPlanState(String xtoken) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if (xtoken.equals(Parameters.xtoken)) {
			if (!careerPlanDao.updateCareerPlanState()) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> updateProjectPlanState(String xtoken) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if (xtoken.equals(Parameters.xtoken)) {
			if (!projectPlanDao.updateProjectPlanState()) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> emailCareer(String xtoken) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if (xtoken.equals(Parameters.xtoken)) {
			List<CareerPlan> careerPlans = careerPlanDao.getOverDueCareerPlan();
			if (careerPlans != null) {
				for(CareerPlan careerPlan: careerPlans) {
					User user = userDao.getById(careerPlan.getUserId());
					if (user != null) {
						String[] emailList = {user.getEmail()};
						System.out.println("个人计划邮件：" + user.getEmail());
						MailUtil.send(emailList, "个人生涯规划提醒", user.getUserName() + " " + user.getName() + "您好<br/>  您的生涯计划：<h1>" + careerPlan.getTitle() + "</h1>截止日期为" + DateUtil.date2String(careerPlan.getPlandate()) + ",即将到期或已超期，请尽快登录系统提交文件。");
					}
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
	public DataWrapper<Void> emailProject(String xtoken) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if (xtoken.equals(Parameters.xtoken)) {
			List<ProjectPlan> projectPlans = projectPlanDao.getOverDuePlan();
			if (projectPlans != null) {
				for(ProjectPlan projectPlan: projectPlans) {
					List<User> userList = userDao.getProjectMember(projectPlan.getProjectId()).getData();
					String[] emailList = new String[userList.size()];
					for (int i = 0; i < userList.size(); i++) {
						emailList[i] = userList.get(i).getEmail();
						System.out.println("项目计划邮件：" + userList.get(i).getEmail());
					}
					MailUtil.send(emailList, "项目规划提醒", "您的项目规划：<h1>" + projectPlan.getTitle() + "</h1>未提交或已逾期，请登录系统查看。");
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
