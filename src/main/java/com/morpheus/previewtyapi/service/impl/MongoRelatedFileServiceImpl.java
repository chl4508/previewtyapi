package com.morpheus.previewtyapi.service.impl;


import com.mongodb.client.gridfs.GridFSBucket;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.doc.ChunksDoc;
import com.morpheus.previewtyapi.service.MongoRelatedFileService;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.FileRecordVO;
import com.morpheus.previewtyapi.vo.RelatedVO;
import org.apache.commons.io.IOUtils;
import org.bson.BsonValue;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.morpheus.previewtyapi.service.GenericAggregationUtils.aggregate;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service("MongoRelatedFileService")
public class MongoRelatedFileServiceImpl implements MongoRelatedFileService {

    private static final Logger logger = LoggerFactory.getLogger(MongoRelatedFileServiceImpl.class);

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;


    @Override
    public String insertRelatedFile(MultipartFile multipartFile, FileRecordVO fileRecordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        String fileId = "";
        try {
            GridFSBucket gridFSBucket = config.gridFSBucketTemplate("previewty");
            ObjectId fileObjId = gridFSBucket.uploadFromStream(multipartFile.getOriginalFilename(),multipartFile.getInputStream());
            fileId = fileObjId.toHexString();

            Query updateQuery = new Query();
//            Query update = new Query();

            updateQuery.addCriteria(Criteria.where("chart").is(fileRecordVO.getChart()));
            updateQuery.addCriteria(Criteria.where("record").is(fileRecordVO.getRecord()));
            updateQuery.addCriteria(Criteria.where("type").is(fileRecordVO.getType()));

            BasicQuery update = new BasicQuery(fileRecordVO.getPushData());

//            Iterator<String> keys = fileRecordVO.getPushData().keySet().iterator();
//            while(keys.hasNext()){
//                String key = keys.next();
//                String value = fileRecordVO.getPushData().get(key).toString();
//                update.addCriteria(Criteria.where(key).is(value));
//                update.addCriteria(Criteria.where(key).is(value));
//            }

            update.addCriteria(Criteria.where("fileid").is(fileId));
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            mongoTemplate.updateMulti(updateQuery,new Update().push("data",update),"record");
            config.closeMongoClient();
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("fileid",fileId);
        return resCode.getResultMapJson(accessToken,0,resCode.resultMessage(0),resultData,request.getRequestURI(), request);
    }

    @Override
    public String selectRelatedFile(String fileId, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(fileId.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }

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

            Map<String, Object> resultData = new HashMap<String, Object>();
            resultData.put("fileid",fileId);
            resultData.put("data",baseEncodeFile);
            config.closeMongoClient();
            return resCode.getResultMapJson(accessToken,0,resCode.resultMessage(0),resultData,request.getRequestURI(), request);
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
    }

    @Override
    public String aggregateRelatedFile(RelatedVO relatedVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        List<Map> res = new ArrayList<>();
        try {

            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

            Aggregation aggregation = null;
            UnwindOperation unwind = null;

            aggregation = newAggregation(
                    aggregate("$match", relatedVO.getMatch1())
                    , aggregate("$project", relatedVO.getProject())
            );

            res = mongoTemplate.aggregate(aggregation,"record", Map.class).getMappedResults();
            config.closeMongoClient();
            //데이터가 없을시
            if (res.size() == 0) {
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
        } catch (Exception e) {
            config.closeMongoClient();
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 101, resCode.resultMessage(101), request.getRequestURI(), request);
        }

        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);
    }

    @Override
    public String updateRelatedFile(MultipartFile multipartFile, FileRecordVO fileRecordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        String fileId = "";
        try {

            GridFSBucket gridFSBucket = config.gridFSBucketTemplate("previewty");
            ObjectId fileObjId = gridFSBucket.uploadFromStream(multipartFile.getOriginalFilename(),multipartFile.getInputStream());
            fileId = fileObjId.toHexString();

            Query updateQuery = new Query();
//            Query update = new Query();
            updateQuery.addCriteria(Criteria.where("chart").is(fileRecordVO.getChart()));
            updateQuery.addCriteria(Criteria.where("record").is(fileRecordVO.getRecord()));
            updateQuery.addCriteria(Criteria.where("type").is(fileRecordVO.getType()));

            String keys[] = fileRecordVO.getElemMatchKeys().split(",");
            String values[] = fileRecordVO.getElemMatchValues().split(",");

             switch (keys.length){
                 case 1 :
                    updateQuery.addCriteria(Criteria.where("data").elemMatch(Criteria.where(keys[0]).is(values[0])));
                    break;
                 case 2 :
                     updateQuery.addCriteria(Criteria.where("data").elemMatch(Criteria.where(keys[0]).is(values[0])
                             .and(keys[1]).is(values[1])));
                     break;
             }

            Update update = new Update();
            update.set("data.$.format", fileRecordVO.getDataFormat());
            update.set("data.$.fileid", fileId);

            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            mongoTemplate.updateMulti(updateQuery, update,"record");
            config.closeMongoClient();
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteRelatedFile(String fileId, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(fileId.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            ObjectId objectId = new ObjectId(fileId);
            GridFSBucket gridFSBucket = config.gridFSBucketTemplate("previewty");
            gridFSBucket.delete(objectId);
            config.closeMongoClient();
        } catch (Exception e) {
           // e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
        }
        Map<String, Object> resultData = new HashMap<String, Object>();
        resultData.put("fileid",fileId);
        return resCode.getResultMapJson(accessToken,0,resCode.resultMessage(0),resultData,request.getRequestURI(), request);

    }


}
