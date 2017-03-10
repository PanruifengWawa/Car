package com.car.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
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
		Session session = getSession();
        String sql = "delete from t_career_plan where id=?";
        try {
        	Query query = session.createSQLQuery(sql);
        	query.setParameter(0, careerPlanId);
            query.executeUpdate();
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return false;
		}
        
        return true;
	}

	@Override
	public List<CareerPlan> getCareerPlan(Long userId,Integer state,String schoolYear) {
		// TODO Auto-generated method stub
		List<CareerPlan> ret = null;
        Session session = getSession();
        String sql = "select cp.* from t_career_plan cp, t_user u where cp.user_id=u.id";
        if (userId != null) {
			sql += " and cp.user_id = " + userId;
		}
        if (state != null) {
			sql += " and cp.state = " + state;
		}
        if (schoolYear != null) {
        	sql += " and u.school_year = " + schoolYear;
		}
        
        Query query = session.createSQLQuery(sql).addEntity(CareerPlan.class);
        try {
            ret = query.list();
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

	@Override
	public boolean updateCareerPlanState() {
		// TODO Auto-generated method stub
		Session session = getSession();
		String sql = "update t_career_plan set state = -2 where state = -1 and curdate() > plandate" ;
        try {
        	Query query = session.createSQLQuery(sql);
            query.executeUpdate();
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return false;
		}
        
        return true;
	}

	@Override
	public List<CareerPlan> getOverDueCareerPlan() {
		// TODO Auto-generated method stub
		List<CareerPlan> ret = null;
        Session session = getSession();
        String sql = "SELECT * FROM t_career_plan where (state=-1 and date_sub(curdate(),interval -7 day) > plandate) or state=-2";

        
        Query query = session.createSQLQuery(sql).addEntity(CareerPlan.class);
        try {
            ret = query.list();
        }catch (Exception e){
            e.printStackTrace();
        }

		return ret;
	}


}
