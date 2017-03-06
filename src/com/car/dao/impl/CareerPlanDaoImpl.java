package com.car.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.CareerPlanDao;
import com.car.models.CareerPlan;


@Repository
public class CareerPlanDaoImpl extends BaseDao<CareerPlan> implements CareerPlanDao {

	@Override
	public boolean saveCareerPlan(CareerPlan careerPlan) {
		// TODO Auto-generated method stub
		return save(careerPlan);
	}

	@Override
	public boolean deleteCareerPlan(Long careerPlanId) {
		// TODO Auto-generated method stub
		return delete(get(careerPlanId));
	}

	@Override
	public List<CareerPlan> getCareerPlan(Long userId,Integer state) {
		// TODO Auto-generated method stub
		List<CareerPlan> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(CareerPlan.class);
        if (userId != null) {
        	criteria.add(Restrictions.eq("userId",userId));
		}
        if (state != null) {
        	criteria.add(Restrictions.eq("state",state));
		}
        
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }

		return ret;
	}

	@Override
	public CareerPlan getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean updateCareerPlan(CareerPlan careerPlan) {
		// TODO Auto-generated method stub
		return update(careerPlan);
	}

}
