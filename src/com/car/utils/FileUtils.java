package com.car.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public class FileUtils {
	public static String saveFile(MultipartFile file,String dir,HttpServletRequest request){
		if (file == null) {
			return null;
		}
		String filePath = request.getSession().getServletContext().getRealPath("/") + "/" + dir + "/";
		String newFileName = MD5Util.getMD5String(file.getOriginalFilename() + new Date() + UUID.randomUUID().toString()).replace(".","")
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

		File fileDir = new File(filePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		try {
            FileOutputStream out = new FileOutputStream(filePath + newFileName);
                // 写入文件
            out.write(file.getBytes());
            out.flush();
            out.close();
            return "/" + dir +"/" + newFileName;
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
}
