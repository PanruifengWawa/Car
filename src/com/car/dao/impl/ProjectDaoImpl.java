package com.car.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.ProjectDao;
import com.car.models.Project;
import com.car.utils.DataWrapper;

@Repository
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao {

	@Override
	public boolean addProject(Project project) {
		// TODO Auto-generated method stub
		return save(project);
	}

	@Override
	public boolean deleteProject(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

	@Override
	public DataWrapper<List<Project>> getProjectList(String name, Long userId) {
		// TODO Auto-generated method stub
		DataWrapper<List<Project>> dataWrapper = new DataWrapper<List<Project>>();
		String sql = "";
		if (userId != null) {
			sql += "select p.* from t_project p, r_project_member pm where p.id = pm.project_id and pm.user_id = " + userId;
		} else {
			sql += "select p.* fromt_project p where 1=1"; 
		}

		if (name != null) {
			sql += " and p.name like %" + name + "%";
		}
		
		List<Project> ret = null;
        Session session = getSession();
        
        try {
            Query query = session.createSQLQuery(sql).addEntity(Project.class);
            ret = query.list();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        return dataWrapper;
	}

}
