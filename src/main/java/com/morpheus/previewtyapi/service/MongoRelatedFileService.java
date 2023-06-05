package com.morpheus.previewtyapi.service;

import com.morpheus.previewtyapi.dto.PatientDto;
import com.morpheus.previewtyapi.vo.FileRecordVO;
import com.morpheus.previewtyapi.vo.PatientVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import com.morpheus.previewtyapi.vo.RelatedVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoRelatedFileService {


    String insertRelatedFile(MultipartFile multipartFile, FileRecordVO fileRecordVO, HttpServletRequest request);

    String selectRelatedFile(String fileId, HttpServletRequest request);

    String aggregateRelatedFile(RelatedVO relatedVO, HttpServletRequest request);

    String updateRelatedFile(MultipartFile multipartFile,FileRecordVO fileRecordVO, HttpServletRequest request);

    String deleteRelatedFile(String fileid, HttpServletRequest request);

}
