package com.becoder.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.becoder.model.Product;

public interface FileService {

	public Boolean uploadFile(MultipartFile file) throws IOException;
	
	public byte[] downloadFile(String file) throws Exception;
	
	public Boolean saveProduct(Product product);
	
	public String uploadFileWithData(MultipartFile file) throws IOException;
}
