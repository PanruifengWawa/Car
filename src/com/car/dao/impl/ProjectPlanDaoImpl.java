package com.car.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.ProjectPlanDao;
import com.car.models.ProjectPlan;


@Repository
public class ProjectPlanDaoImpl extends BaseDao<ProjectPlan> implements ProjectPlanDao {

	@Override
	public boolean addProjectPlan(ProjectPlan projectPlan) {
		// TODO Auto-generated method stub
		return save(projectPlan);
	}

	@Override
	public boolean deleteProjectPlan(Long id) {
		// TODO Auto-generated method stub
		Session session = getSession();
        String sql = "delete from t_project_plan where id=?";
        try {
        	Query query = session.createSQLQuery(sql);
        	query.setParameter(0, id);
            query.executeUpdate();
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return false;
		}
        
        return true;
	}

	@Override
	public ProjectPlan getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public List<ProjectPlan> getProjectPlanList(Long projectId, Integer state) {
		// TODO Auto-generated method stub
		List<ProjectPlan> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectPlan.class);
        if (projectId != null) {
        	criteria.add(Restrictions.eq("projectId",projectId));
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
	public boolean updateProjectPlan(ProjectPlan projectPlan) {
		// TODO Auto-generated method stub
		return update(projectPlan);
	}

	@Override
	public boolean updateProjectPlanState() {
		// TODO Auto-generated method stub
		Session session = getSession();
		String sql = "update t_project_plan set state = -2 where state = -1 and curdate() > plandate" ;
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
	public List<ProjectPlan> getOverDuePlan() {
		// TODO Auto-generated method stub
		List<ProjectPlan> ret = null;
        Session session = getSession();
        String sql = "SELECT * FROM t_project_plan where (state=-1 and date_sub(curdate(),interval -7 day) > plandate) or state=-2";

        
        Query query = session.createSQLQuery(sql).addEntity(ProjectPlan.class);
        try {
            ret = query.list();
        }catch (Exception e){
            e.printStackTrace();
        }

		return ret;
	}

}
