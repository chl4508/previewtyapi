package com.morpheus.previewtyapi.service.v2.impl;

import com.mongodb.BasicDBObject;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.service.v2.MongoPatientService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.PatientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Service("v2.MongoPatientService")
public class MongoPatientServiceImpl implements MongoPatientService {


    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;


    @Override
    public String findPatientList(int limit, int skip, String sort, HttpServletRequest request) {
        List<Map> res = new ArrayList<>();
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(sort)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"));
            Query query = new Query(criteria);
            // sort
            Enum sortDirection = Sort.Direction.DESC;
            if (sort.equals("asc") || sort.equals("ASC")) {
                sortDirection = Sort.Direction.ASC;
            }
            query.limit(limit);
            query.skip(skip);
            res = mongoTemplate.find(query.with(Sort.by((Sort.Direction) sortDirection, "up_dt")), Map.class,"patient2");

            //데이터 없을시 에러
            if(res.size() == 0){
                config.closeMongoClient();
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }

        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);
    }

    @Override
    public String findPatient(String chart, HttpServletRequest request) {
        List<Map> res = new ArrayList<>();
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart));
            Query query = new Query(criteria);
            res = mongoTemplate.find(query, Map.class,"patient2");
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
    public String findPatientListCount(HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
        Long res;

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"));
            Query query = new Query(criteria);
            res = mongoTemplate.count(query, Map.class,"patient2");

        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("count",res);
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }

    @Override
    public String updatePatient(PatientVO patientVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(patientVO.getUp_id()) || comUtil.empty(patientVO.getName()) || comUtil.empty(patientVO.getBirth())
                || comUtil.empty(patientVO.getGender()) || comUtil.empty(patientVO.getAddress()) || comUtil.empty(patientVO.getEmail())
                || comUtil.empty(patientVO.getPhone())){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {

            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(patientVO.getChart()));
            Query query = new Query(criteria);
            Update update = new Update();

            update.set("name", patientVO.getName());
            update.set("birth", patientVO.getBirth());
            update.set("gender", patientVO.getGender());
            update.set("address", patientVO.getAddress());
            update.set("email", patientVO.getEmail());
            update.set("phone", patientVO.getPhone());
            update.set("up_id", patientVO.getUp_id());
            update.currentDate("up_dt");

            mongoTemplate.updateMulti(query,update,"patient2");

        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String insertPatient(PatientVO patientVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        if(comUtil.empty(patientVO.getChart()) || comUtil.empty(patientVO.getIn_id()) || comUtil.empty(patientVO.getUp_id())
                || comUtil.empty(patientVO.getName()) || comUtil.empty(patientVO.getGender()) || comUtil.empty(patientVO.getAddress())
                || comUtil.empty(patientVO.getEmail()) || comUtil.empty(patientVO.getPhone())){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
        try {

            BasicDBObject query = new BasicDBObject();

            query.put("chart",patientVO.getChart());
            query.put("name",patientVO.getName());
            query.put("birth",patientVO.getBirth());
            query.put("gender",patientVO.getGender());
            query.put("address",patientVO.getAddress());
            query.put("email",patientVO.getEmail());
            query.put("phone",patientVO.getPhone());
            query.put("in_id",patientVO.getIn_id());
            query.put("up_id",patientVO.getUp_id());
            query.put("del_yn","n");
            query.put("in_dt",new Date());
            query.put("up_dt",new Date());

            mongoTemplate.insert(query,"patient2");
        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deletePatient(String chart, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("del_yn").is("n"),Criteria.where("chart").is(chart));
            Query query = new Query(criteria);
            Update update = new Update();
            update.set("del_yn", "y");
            update.set("up_id", up_id);
            update.currentDate("up_dt");

            mongoTemplate.updateMulti(query,update,"patient2");

        } catch (Exception e) {
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }

        config.closeMongoClient();
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
