package com.palmD.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class File_serv {

	public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) throws Exception {
		UUID uuid = UUID.randomUUID();
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));  
		String savedFileName = uuid.toString() + extension;
		String fileUploadFullUrl = uploadPath + "/" + savedFileName;
		
		mkdirs(uploadPath);
		
		FileOutputStream fos;
		fos = new FileOutputStream(fileUploadFullUrl);
		fos.write(fileData);
		fos.close();
		return savedFileName;
	}
	
	public void deleteFile(String filePath) throws Exception {
		File deleteFile = new File(filePath);
		if(deleteFile.exists()) deleteFile.delete();
	}
	
	private void mkdirs (String uploadPath) {
		File folder = new File(uploadPath);
		if(!folder.exists()) {
			folder.mkdirs();
		}
	}
}
