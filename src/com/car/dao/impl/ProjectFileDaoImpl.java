package com.car.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.ProjectFileDao;
import com.car.models.Material;
import com.car.models.ProjectFile;
import com.car.utils.DaoUtils;
import com.car.utils.DataWrapper;

@Repository
public class ProjectFileDaoImpl extends BaseDao<ProjectFile> implements ProjectFileDao {

	@Override
	public boolean addProjectFile(ProjectFile projectFile) {
		// TODO Auto-generated method stub
		return save(projectFile);
	}

	@Override
	public ProjectFile getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean deleteProjectFile(ProjectFile projectFile) {
		// TODO Auto-generated method stub
		return delete(projectFile);
	}

	@Override
	public DataWrapper<List<ProjectFile>> getProjectFileList(Long projectId, Integer numberPerPage,
			Integer currentPage) {
		// TODO Auto-generated method stub
		if (numberPerPage == null) {
			numberPerPage = -1;
		}
		if (currentPage == null) {
			currentPage = -1;
		}
		
		DataWrapper<List<ProjectFile>> dataWrapper = new DataWrapper<List<ProjectFile>>();
		List<ProjectFile> ret = null;
		Session session = getSession();
		Criteria criteria = session.createCriteria(ProjectFile.class);


		criteria.add(Restrictions.eq("projectId",projectId));
		
		criteria.setProjection(Projections.rowCount());
		int totalItemNum = ((Long) criteria.uniqueResult()).intValue();
		int totalPageNum = DaoUtils.getTotalPageNum(totalItemNum, numberPerPage);
		
		criteria.setProjection(null);
		if (currentPage > 0 && numberPerPage > 0) {
			criteria.setMaxResults(numberPerPage);
			criteria.setFirstResult((currentPage-1)*numberPerPage);
		}
		try {
			ret = criteria.list();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(numberPerPage);
		return dataWrapper;
	}

}
