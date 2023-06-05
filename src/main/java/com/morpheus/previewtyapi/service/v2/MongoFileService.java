package com.morpheus.previewtyapi.service.v2;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoFileService {

    String uploadFile(MultipartFile multipartFile, HttpServletRequest request);

    String downloadFile(String fileId, HttpServletRequest request);

    String deleteFile(String fileId , HttpServletRequest request);


}
