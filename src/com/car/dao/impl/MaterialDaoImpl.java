package com.car.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.MaterialDao;
import com.car.models.Material;
import com.car.utils.DaoUtils;
import com.car.utils.DataWrapper;

@Repository
public class MaterialDaoImpl extends BaseDao<Material> implements MaterialDao {

	@Override
	public boolean saveMaterial(Material material) {
		// TODO Auto-generated method stub
		return save(material);
	}

	@Override
	public DataWrapper<List<Material>> getMaterialList(Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		if (numberPerPage == null) {
			numberPerPage = -1;
		}
		if (currentPage == null) {
			currentPage = -1;
		}
		
		DataWrapper<List<Material>> dataWrapper = new DataWrapper<List<Material>>();
		List<Material> ret = null;
		Session session = getSession();
		Criteria criteria = session.createCriteria(Material.class);



		
		criteria.setProjection(Projections.rowCount());
		int totalItemNum = ((Long) criteria.uniqueResult()).intValue();
		int totalPageNum = DaoUtils.getTotalPageNum(totalItemNum, numberPerPage);
		
		criteria.addOrder(Order.desc("file"));
		
		criteria.setProjection(null);
		if (currentPage > 0 && numberPerPage > 0) {
			criteria.setMaxResults(numberPerPage);
			criteria.setFirstResult((currentPage-1)*numberPerPage);
		}
		try {
			ret = criteria.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		dataWrapper.setData(ret);
        dataWrapper.setTotalNumber(totalItemNum);
        dataWrapper.setCurrentPage(currentPage);
        dataWrapper.setTotalPage(totalPageNum);
        dataWrapper.setNumberPerPage(numberPerPage);
		return dataWrapper;
	}

	@Override
	public boolean deleteMaterial(Long id) {
		// TODO Auto-generated method stub
		return delete(get(id));
	}

}
