package com.car.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.car.dao.FileDao;
import com.car.dao.MaterialDao;
import com.car.enums.ErrorCodeEnum;
import com.car.enums.Parameters;
import com.car.models.File;
import com.car.models.Material;
import com.car.models.User;
import com.car.service.MaterialService;
import com.car.utils.DataWrapper;
import com.car.utils.FileUtils;
import com.car.utils.SessionManager;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {

	@Autowired
	FileDao fileDao;
	
	@Autowired
	MaterialDao materialDao;
	
	@Override
	public DataWrapper<Void> uploadMaterial(MultipartFile file, String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			if (file != null) {
				String filePath = FileUtils.saveFile(file, "material", request);
				if (filePath != null) {
					File fileEntity = new File();
					fileEntity.setTitle(file.getOriginalFilename());
					fileEntity.setUrl(Parameters.fileSrc + filePath);
					fileEntity.setDate(new Timestamp(System.currentTimeMillis()));
					fileEntity.setId(null);
					fileEntity.setOwner(admin.getName());
					fileDao.saveFile(fileEntity);
					if (fileEntity.getId() != null) {
						Material material = new Material();
						material.setFile(fileEntity);
						if (!materialDao.saveMaterial(material)) {
							dataWrapper.setErrorCode(ErrorCodeEnum.Error);
						}
						
					} else {
						dataWrapper.setErrorCode(ErrorCodeEnum.Error);
					}
				} else {
					dataWrapper.setErrorCode(ErrorCodeEnum.Error);
				}
				
			} else {
				dataWrapper.setErrorCode(ErrorCodeEnum.Null_Input_Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<Void> deleteMaterial(Long id, String token, HttpServletRequest request) {
		// TODO Auto-generated method stub
		DataWrapper<Void> dataWrapper = new DataWrapper<Void>(); 
		User admin = SessionManager.getSession(token);
		if (admin != null && admin.getType() == Parameters.admin) {
			if (!materialDao.deleteMaterial(id)) {
				dataWrapper.setErrorCode(ErrorCodeEnum.Error);
			}
		} else {
			dataWrapper.setErrorCode(ErrorCodeEnum.Error);
		}
		return dataWrapper;
	}

	@Override
	public DataWrapper<List<Material>> getMaterialList(Integer numberPerPage, Integer currentPage) {
		// TODO Auto-generated method stub
		return materialDao.getMaterialList(numberPerPage, currentPage);
	}

}
