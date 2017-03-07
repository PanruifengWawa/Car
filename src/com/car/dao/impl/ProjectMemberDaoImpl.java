package com.car.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.ProjectMemberDao;
import com.car.models.CareerPlan;
import com.car.models.ProjectMember;

@Repository
public class ProjectMemberDaoImpl extends BaseDao<ProjectMember> implements ProjectMemberDao {

	@Override
	public boolean addProjectMember(ProjectMember projectMember) {
		// TODO Auto-generated method stub
		return save(projectMember);
	}

	@Override
	public boolean deleteProjectMember(Long userId, Long projectId) {
		// TODO Auto-generated method stub
		Session session = getSession();
        String sql = "delete from r_project_member where project_id=? and user_id=?";
        try {
        	Query query = session.createSQLQuery(sql);
        	query.setParameter(0, projectId);
        	query.setParameter(1, userId);
            query.executeUpdate();
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
        	return false;
		}
        
        return true;
	}

	@Override
	public ProjectMember getByUserIdProjectId(Long userId, Long projectId) {
		// TODO Auto-generated method stub
		List<ProjectMember> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(ProjectMember.class);
        if (userId != null) {
        	criteria.add(Restrictions.eq("userId",userId));
		}
        if (projectId != null) {
        	criteria.add(Restrictions.eq("projectId",projectId));
		}
        try {
            ret = criteria.list();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return ret.get(0);
		}

		return null;
	}

}
