package com.morpheus.previewtyapi.service.v2.impl;

import com.mongodb.BasicDBObject;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.service.v2.MongoRecordService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service("v2.MongoRecordService")
public class MongoRecordServiceImpl implements MongoRecordService {

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;


    @Override
    public String findRecordList(String chart, int limit, int skip, String sort, HttpServletRequest request) {
        List<Map> res = new ArrayList<>();
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(sort) || comUtil.empty(chart)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {

            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart));
            Query query = new Query(criteria);
            // sort
            Enum sortDirection = Sort.Direction.DESC;
            if (sort.equals("asc") || sort.equals("ASC")) {
                sortDirection = Sort.Direction.ASC;
            }
            query.limit(limit);
            query.skip(skip);

            res = mongoTemplate.find(query.with(Sort.by((Sort.Direction) sortDirection, "record","type")), Map.class,"record2");

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
    public String findRecord(String chart, String record, String type, HttpServletRequest request) {
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
            res = mongoTemplate.find(query, Map.class,"record2");

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
    public String findRecordListCount(String chart, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        Long res;
        if(comUtil.empty(chart)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart));
            Query query = new Query(criteria);
            res = mongoTemplate.count(query, Map.class,"record2");
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("count",res);
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }

    @Override
    public String insertRecord(RecordVO recordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(recordVO.getChart()) || comUtil.empty(recordVO.getRecord()) || comUtil.empty(recordVO.getType())
            || comUtil.empty(recordVO.getDescription()) || comUtil.empty(recordVO.getIn_id()) || comUtil.empty(recordVO.getUp_id())){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            BasicDBObject query = new BasicDBObject();

            query.put("chart",recordVO.getChart());
            query.put("record",recordVO.getRecord());
            query.put("type",recordVO.getType());
            query.put("description",recordVO.getDescription());
            query.put("in_id",recordVO.getIn_id());
            query.put("up_id",recordVO.getUp_id());
            query.put("del_yn","n");
            query.put("in_dt",new Date());
            query.put("up_dt",new Date());

            mongoTemplate.insert(query,"record2");

        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String updateRecord(RecordVO recordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(recordVO.getChart()) || comUtil.empty(recordVO.getRecord()) || comUtil.empty(recordVO.getType())
                || comUtil.empty(recordVO.getUp_id()) || comUtil.empty(recordVO.getDescription())){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(recordVO.getChart())
                    ,Criteria.where("record").is(recordVO.getRecord()),Criteria.where("type").is(recordVO.getType()));
            Query query = new Query(criteria);
            Update update = new Update();

            update.set("up_id", recordVO.getUp_id());
            update.set("description", recordVO.getDescription());
            update.currentDate("up_dt");

            mongoTemplate.updateMulti(query,update,"record2");
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteRecord(String chart, String record, String type, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type) || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart)
                    ,Criteria.where("record").is(record),Criteria.where("type").is(type));
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
