package com.morpheus.previewtyapi.service.v2.impl;

import com.mongodb.client.gridfs.GridFSBucket;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.doc.ChunksDoc;
import com.morpheus.previewtyapi.service.v2.MongoFileService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("v2.MongoFileService")
public class MongoFileServiceImpl implements MongoFileService {

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;


    @Override
    public String uploadFile(MultipartFile multipartFile, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        String fileId = "";
        if(multipartFile.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        try {
            GridFSBucket gridFSBucket = config.gridFSBucketTemplate("previewty");
            ObjectId fileObjId = gridFSBucket.uploadFromStream(multipartFile.getOriginalFilename(),multipartFile.getInputStream());
            fileId = fileObjId.toHexString();
            config.closeMongoClient();
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("fileid",fileId);
        return resCode.getResultMapJson(accessToken,0,resCode.resultMessage(0),resultData,request.getRequestURI(), request);
    }

    @Override
    public String downloadFile(String fileId, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(fileId.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        Map<String, Object> resultData = new HashMap<String, Object>();
        try {
            BasicQuery query = new BasicQuery("{ files_id : ObjectId('"+fileId+"') }");
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            List<ChunksDoc> files = mongoTemplate.find(query, ChunksDoc.class, "fs.chunks");

            if(files.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }

            String baseEncodeFile = "";
            for(int i = 0; i< files.size(); i++) {
                baseEncodeFile += Base64.getEncoder().encodeToString(files.get(i).getData());
            }
            resultData.put("fileid",fileId);
            resultData.put("data",baseEncodeFile);
            config.closeMongoClient();
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultMapJson(accessToken,0,resCode.resultMessage(0),resultData,request.getRequestURI(), request);
    }

    @Override
    public String deleteFile(String fileId, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(fileId.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }

        try {
            GridFSBucket gridFSBucket = config.gridFSBucketTemplate("previewty");
            gridFSBucket.delete(new ObjectId(fileId));
            config.closeMongoClient();
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
