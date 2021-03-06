package com.car.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.UserDao;
import com.car.enums.Parameters;
import com.car.models.User;
import com.car.utils.DaoUtils;
import com.car.utils.DataWrapper;


@Repository
public class UserDaoImpl extends BaseDao<User> implements UserDao {

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		return save(user);
	}

	@Override
	public User getByUserName(String userName) {
		// TODO Auto-generated method stub
		List<User> ret = null;
        Session session = getSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName",userName));
        try {
            ret = criteria.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ret != null && ret.size() > 0) {
			return ret.get(0);
		}
		return null;
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return get(id);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		return update(user);
	}

	@Override
	public DataWrapper<List<User>> getUserList(String degreeType, String school, String keywords,Integer state, String schoolYear,Integer careerCount, Integer numberPerPage,
			Integer currentPage) {
		// TODO Auto-generated method stub
		if (numberPerPage == null) {
			numberPerPage = -1;
		}
		if (currentPage == null) {
			currentPage = -1;
		}
		
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
		List<User> ret = null;
		Session session = getSession();
		Criteria criteria = session.createCriteria(User.class);
		if (state != null) {
			criteria.add(Restrictions.eq("state",state));
		}
		if (schoolYear != null) {
			criteria.add(Restrictions.eq("schoolYear",schoolYear));
		}
		if (degreeType != null) {
			criteria.add(Restrictions.eq("degreeType",degreeType));
		}
		if (school != null) {
			criteria.add(Restrictions.eq("school",school));
		}
		if (keywords != null) {
			criteria.add(Restrictions.or(Restrictions.like("userName",keywords,MatchMode.ANYWHERE),
					Restrictions.like("name",keywords,MatchMode.ANYWHERE),Restrictions.like("email",keywords,MatchMode.ANYWHERE)
					));
		}
		if (careerCount != null) {
			criteria.add(Restrictions.eq("careerCount",careerCount));
		}
		
		criteria.add(Restrictions.eq("type",Parameters.user));

		
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


	@Override
	public DataWrapper<List<User>> getProjectMember(Long projectId) {
		// TODO Auto-generated method stub
		DataWrapper<List<User>> dataWrapper = new DataWrapper<List<User>>();
		String sql = "select u.* from t_user u,r_project_member pm where pm.user_id = u.id and pm.project_id=?";
		
		
		List<User> ret = null;
        Session session = getSession();
        
        try {
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.setParameter(0, projectId);
            ret = query.list();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        dataWrapper.setData(ret);
        return dataWrapper;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		return delete(user);
	}


}
