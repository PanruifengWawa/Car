package com.car.dao.impl;

import org.springframework.stereotype.Repository;

import com.car.dao.BaseDao;
import com.car.dao.FileDao;
import com.car.models.File;

@Repository
public class FileDaoImpl extends BaseDao<File> implements FileDao {

	@Override
	public boolean saveFile(File file) {
		// TODO Auto-generated method stub
		return save(file);
	}

}
