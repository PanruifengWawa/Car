package com.car.service.impl;

import org.springframework.stereotype.Service;

import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.service.TimerJobsService;
import com.car.utils.DataWrapper;

@Service("timerJobsService")
public class TimerJobsServiceImpl implements TimerJobsService {

	@Override
	public DataWrapper<Void> updateCareerPlanState(String xtoken) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		if (xtoken.equals(Parameters.xtoken)) {
			System.out.println(1);
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
			
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}



}
