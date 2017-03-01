package com.car.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.car.models.Material;
import com.car.utils.DataWrapper;

public interface MaterialService {
	DataWrapper<Void> uploadMaterial(MultipartFile file,String token,HttpServletRequest request);
	DataWrapper<Void> deleteMaterial(Long id,String token,HttpServletRequest request);
	DataWrapper<List<Material>> getMaterialList(Integer numberPerPage,Integer currentPage);

}
