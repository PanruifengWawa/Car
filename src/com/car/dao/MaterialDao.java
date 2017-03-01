package com.car.dao;

import java.util.List;

import com.car.models.Material;
import com.car.utils.DataWrapper;

public interface MaterialDao {
	boolean saveMaterial(Material material);
	boolean deleteMaterial(Long id);
	DataWrapper<List<Material>> getMaterialList(Integer numberPerPage, Integer currentPage);
}
