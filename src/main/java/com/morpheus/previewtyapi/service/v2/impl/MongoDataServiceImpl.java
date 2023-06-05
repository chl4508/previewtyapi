package com.morpheus.previewtyapi.service.v2.impl;

import com.mongodb.BasicDBObject;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.service.v2.MongoDataService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("v2.MongoDataService")
public class MongoDataServiceImpl implements MongoDataService {

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;

    @Override
    public String findDataList(String chart, String record, String type, int limit, int skip, HttpServletRequest request) {
        List<Map> res = new ArrayList<>();
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {

            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart)
                    ,Criteria.where("record").is(record),Criteria.where("type").is(type));
            Query query = new Query(criteria);

            query.limit(limit);
            query.skip(skip);

            res = mongoTemplate.find(query, Map.class,"data");

            //데이터 없을시 에러
            if(res.size() == 0){
                config.closeMongoClient();
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);
    }

    @Override
    public String findData(String chart, String record, String type, String name, HttpServletRequest request) {
        List<Map> res = new ArrayList<>();
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type) || comUtil.empty(name)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart),Criteria.where("record").is(record),
                    Criteria.where("type").is(type),Criteria.where("name").is(name));
            Query query = new Query(criteria);
            res = mongoTemplate.find(query, Map.class,"data");
            //데이터 없을시 에러
            if(res.size() == 0){
                config.closeMongoClient();
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);
    }

    @Override
    public String insertData(DataVO dataVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(dataVO.getChart()) || comUtil.empty(dataVO.getRecord()) || comUtil.empty(dataVO.getType())
                || comUtil.empty(dataVO.getName()) || comUtil.empty(dataVO.getFormat()) || comUtil.empty(dataVO.getFile_id())
                || comUtil.empty(dataVO.getIn_id()) || comUtil.empty(dataVO.getUp_id())){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            BasicDBObject query = new BasicDBObject();

            query.put("chart",dataVO.getChart());
            query.put("record",dataVO.getRecord());
            query.put("type",dataVO.getType());
            query.put("name",dataVO.getName());
            query.put("format",dataVO.getFormat());
            query.put("fileId",dataVO.getFile_id());
            query.put("in_id",dataVO.getIn_id());
            query.put("up_id",dataVO.getUp_id());
            query.put("del_yn","n");
            query.put("in_dt",new Date());
            query.put("up_dt",new Date());

            mongoTemplate.insert(query,"data");

        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteData(String chart, String record, String type, String name, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type) || comUtil.empty(name) || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart),Criteria.where("record").is(record),
                    Criteria.where("type").is(type),Criteria.where("name").is(name));
            Query query = new Query(criteria);
            Update update = new Update();

            update.set("del_yn", "y");
            update.set("up_id", up_id);
            update.currentDate("up_dt");

            mongoTemplate.updateMulti(query,update,"record2");
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }


        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
