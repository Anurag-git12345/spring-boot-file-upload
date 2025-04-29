package com.becoder.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import com.becoder.model.Product;
import com.becoder.repository.ProductRepository;
import com.becoder.service.FileService;

@Service
public class FileSeviceImpl implements FileService{
	
	@Value("${file.upload.path}")
	private String uploadPath;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Boolean uploadFile(MultipartFile file) throws IOException {
		
		String fileName = file.getOriginalFilename();
		File savefile = new File(uploadPath);
		
		if (!savefile.exists()) {
			savefile.mkdir();
		}
		
		String storePath = uploadPath.concat(fileName);
		
		long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
		if (upload != 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public String uploadFileWithData(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		File savefile = new File(uploadPath);
		
		String rndString = UUID.randomUUID().toString();
		// my_photo.jpeg -> my_photo_jhsfhjkbsf.jpeg -> my_photo.jpeg 
		String removeExtention = FilenameUtils.removeExtension(fileName); // -> my_photo
		String extention = FilenameUtils.getExtension(fileName);
		fileName = removeExtention+"_"+rndString+"."+extention;
		
		if (!savefile.exists()) {
			savefile.mkdir();
		}
		
		String storePath = uploadPath.concat(fileName);
		
		long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
		if (upload != 0) {
			return fileName;
		}
		return null;
	}

	@Override
	public byte[] downloadFile(String file) throws Exception {
		String fullPath = uploadPath.concat(file); // file/a.png
		try {
			InputStream ios = new FileInputStream(fullPath);
			
			return StreamUtils.copyToByteArray(ios);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
			
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	@Override
	public Boolean saveProduct(Product product) {
		Product save = productRepository.save(product);
		if (!ObjectUtils.isEmpty(save)) {
			return true;
		}
		return false;
	}

}
		