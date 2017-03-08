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

import com.car.dao.CareerPlanDao;
import com.car.dao.FileDao;
import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.models.CareerPlan;
import com.car.models.CareerPlanForYear;
import com.car.models.File;
import com.car.models.User;
import com.car.service.CareerPlanService;
import com.car.utils.CareerPlanWrapper;
import com.car.utils.DataWrapper;
import com.car.utils.DateUtil;
import com.car.utils.FileUtils;
import com.car.utils.SessionManager;

@Service("careerPlanService")
public class CareerPlanServiceImpl implements CareerPlanService {
	@Autowired
	CareerPlanDao careerPlanDao;
	
	@Autowired
	FileDao fileDao;

	@Override
	public DataWrapper<Void> addCareerPlan(Long[] userIds, String title, String content, String plandateStr,
			String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			for(Long userId : userIds) {
				CareerPlan careerPlan = new CareerPlan();
				careerPlan.setId(null);
				careerPlan.setUserId(userId);
				careerPlan.setTitle(title);
				careerPlan.setContent(content);
				careerPlan.setState(Parameters.stateUncommit);
				careerPlan.setPlandate(DateUtil.parse(plandateStr));
				File file = new File();
				file.setId(new Long(1));
				careerPlan.setFile(file);
				careerPlanDao.saveCareerPlan(careerPlan);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteCareerPlan(Long[] careerPlanIds, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			for(Long careerPlanId : careerPlanIds) {
				careerPlanDao.deleteCareerPlan(careerPlanId);
			}
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<CareerPlanWrapper> getCareerPlanList(Long userId,Integer state,String schoolYear,String token) {
		// TODO Auto-generated method stub
		DataWrapper<CareerPlanWrapper> dataWrapper = new DataWrapper<CareerPlanWrapper>(); 
		User user = SessionManager.getSession(token);
		List<CareerPlan> careerPlans = null;
		if (user == null) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			return dataWrapper;
		} else if (user.getType() == Parameters.admin) {
			
			careerPlans = careerPlanDao.getCareerPlan(userId,state,schoolYear);

		} else {
			careerPlans = careerPlanDao.getCareerPlan(user.getId(),state,schoolYear);
		}
		
		CareerPlanWrapper careerPlanWrapper = new CareerPlanWrapper();
		Integer unCommit = 0;
		Integer toVerify = 0;
		Integer passed = 0;
		Integer overdue = 0;
		Integer unPassed = 0;
		if (careerPlans != null) {
			Map<Integer, CareerPlanForYear> map = new TreeMap<Integer, CareerPlanForYear>(
	                new Comparator<Integer>() {
	                    public int compare(Integer obj1, Integer obj2) {
	                        // 降序排序
	                        return obj2.compareTo(obj1);
	                    }
	                });

			for(CareerPlan careerPlan: careerPlans) {
				switch (careerPlan.getState()) {
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
				Integer year = DateUtil.getYear(careerPlan.getPlandate());
				CareerPlanForYear careerPlanForYear = null;
				if (map.get(year) == null) {
					List<CareerPlan> careerPlans2 = new ArrayList<CareerPlan>();
					careerPlans2.add(careerPlan);
					
					careerPlanForYear = new CareerPlanForYear();
					careerPlanForYear.setYear(year);
					careerPlanForYear.setCareerPlans(careerPlans2);
				} else {
					careerPlanForYear =  map.get(year);
					careerPlanForYear.getCareerPlans().add(careerPlan);
				}
				map.put(year, careerPlanForYear);
			}
			
			List<CareerPlanForYear> careerPlanForYears = new ArrayList<CareerPlanForYear>();
			for(CareerPlanForYear e: map.values()) {
				careerPlanForYears.add(e);
			}
			careerPlanWrapper.setCareerPlanForYears(careerPlanForYears);
		}
		
		careerPlanWrapper.setAll(careerPlans.size());
		careerPlanWrapper.setPassed(passed);
		careerPlanWrapper.setOverdue(overdue);
		careerPlanWrapper.setUnPassed(unPassed);
		careerPlanWrapper.setUnCommit(unCommit);
		careerPlanWrapper.setToVerify(toVerify);
		dataWrapper.setData(careerPlanWrapper);
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> commit(Long careerPlanId,MultipartFile file, String token,HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User user = SessionManager.getSession(token);
		if (careerPlanId != null && file !=null && user != null) {
			CareerPlan careerPlan = careerPlanDao.getById(careerPlanId);
			if (careerPlan != null && careerPlan.getUserId() == user.getId()) {
				String filePath = FileUtils.saveFile(file, "career", request);
				
				if (filePath != null) {
					File fileEntity = new File();
					fileEntity.setTitle(user.getUserName() + "_" + user.getName() + "_" + careerPlanId);
					fileEntity.setUrl(Parameters.fileSrc + filePath);
					fileEntity.setDate(new Timestamp(System.currentTimeMillis()));
					fileEntity.setId(null);
					fileEntity.setOwner(user.getName());
					fileDao.saveFile(fileEntity);
					if (fileEntity.getId() != null) {
						careerPlan.setFile(fileEntity);
						careerPlan.setState(Parameters.stateToVerify);
						careerPlan.setComdate(new Date());
						if (!careerPlanDao.updateCareerPlan(careerPlan)) {
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
	public DataWrapper<Void> verify(Long careerPlanId, Integer state, String response, String token) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (!(state == Parameters.statePassed || state == Parameters.stateUnpassed)) {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			return dataWrapper;
		}
		if (admin != null && admin.getType() == Parameters.admin  && careerPlanId != null && state != null) {
			CareerPlan careerPlan = careerPlanDao.getById(careerPlanId);
			if (careerPlan != null) {
				if (state == Parameters.stateUnpassed) {
					careerPlan.setResponse(response);
					careerPlan.setResdate(new Date());
				}
				careerPlan.setState(state);
				if (!careerPlanDao.updateCareerPlan(careerPlan)) {
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
	public DataWrapper<CareerPlan> getById(Long careerPlanId, String token) {
		// TODO Auto-generated method stub
		DataWrapper<CareerPlan> dataWrapper = new DataWrapper<CareerPlan>(); 
		User user = SessionManager.getSession(token);
		if (user != null) {
			CareerPlan careerPlan = careerPlanDao.getById(careerPlanId);
			if (user.getType() == Parameters.admin) {
				dataWrapper.setData(careerPlan);
			} else if(careerPlan !=null && user.getType() == Parameters.user && careerPlan.getUserId() == user.getId()) {
				dataWrapper.setData(careerPlan);
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

}
