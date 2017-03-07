package com.car.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.ProjectDao;
import com.car.models.Project;
import com.car.utils.DaoUtils;
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
	public DataWrapper<List<Project>> getProjectList(String name, Long userId,Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		DataWrapper<List<Project>> dataWrapper = new DataWrapper<List<Project>>();
		if (numberPerPage == null) {
			numberPerPage = -1;
		}
		if (currentPage == null) {
			currentPage = -1;
		}
	        
		String sql = "";
		if (userId != null) {
			sql += "select p.* from t_project p, r_project_member pm where p.id = pm.project_id and pm.user_id = " + userId;
		} else {
			sql += "select p.* from t_project p where 1=1"; 
		}

		if (name != null) {
			sql += " and p.name like '%" + name + "%'";
		}
		sql += " order by regdate desc";
		List<Project> ret = null;
        Session session = getSession();
        Query query = session.createSQLQuery(sql).addEntity(Project.class);
        
        int totalItemNum = (query.list().size());
        int totalPageNum = DaoUtils.getTotalPageNum(totalItemNum, numberPerPage);
       
        
        if (numberPerPage > 0 && currentPage > 0) {
            query.setMaxResults(numberPerPage);
            query.setFirstResult((currentPage - 1) * numberPerPage);
        }
        
        try {
            ret = query.list();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(numberPerPage);
        dataWrapper.setData(ret);
        return dataWrapper;
	}

}
