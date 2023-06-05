package com.morpheus.previewtyapi.service.impl;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.doc.RecordDoc;
import com.morpheus.previewtyapi.dto.PatientDto;
import com.morpheus.previewtyapi.service.MongoPatientService;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.PatientVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("MongoPatientService")
public class MongoPatientServiceImpl implements MongoPatientService {

    private static final Logger logger = LoggerFactory.getLogger(MongoPatientServiceImpl.class);

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;


    @Override
    public String searchFindPatient(PatientVO patientVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        List<Map> res = new ArrayList<>();


        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            if(patientVO.getChart().isEmpty() && patientVO.getSingleChart().isEmpty() && patientVO.getName().isEmpty()){
                // patient 전체 조회
                res = mongoTemplate.findAll(Map.class,"patient");

                if(patientVO.isSort()){
                    Query query = new Query();
                    // sort
                    Enum sort = Sort.Direction.DESC;
                    if (patientVO.getSortValue().equals("asc") || patientVO.getSortValue().equals("ASC")) {
                        sort = Sort.Direction.ASC;
                    }
                    res = mongoTemplate.find(query.with(Sort.by((Sort.Direction) sort, patientVO.getSortKey())), Map.class,"patient");

                }else {
                    res = mongoTemplate.findAll(Map.class,"patient");
                }


            }else {


                Criteria criteria = new Criteria();
                // or 값 존재시
                if (patientVO.isOr()) {

                    criteria.orOperator(
                            //chart
                            Criteria.where("chart")
                                    .regex(patientVO.getChart().get("regex").toString(),patientVO.getChart().get("options").toString())

                            //name
                            ,Criteria.where("name")
                                    .regex(patientVO.getName().get("regex").toString(),patientVO.getName().get("options").toString())

                    );
                }

                //단일 chart 조회시
                if (!patientVO.getSingleChart().isEmpty()) {
                    criteria.andOperator(Criteria.where("chart").is(patientVO.getSingleChart()));
                }

                Query query = new Query(criteria);
                if(!patientVO.isSort()){
                    res = mongoTemplate.find(query, Map.class,"patient");
                }else {// sort
                    Enum sort = Sort.Direction.DESC;
                    if (patientVO.getSortValue().equals("asc") || patientVO.getSortValue().equals("ASC")) {
                        sort = Sort.Direction.ASC;
                    }
                    res = mongoTemplate.find(query.with(Sort.by((Sort.Direction) sort, patientVO.getSortKey())), Map.class,"patient");
                }

            }
            config.closeMongoClient();
            //데이터 없을시 에러
            if(res.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);

    }

    @Override
    public String countPatient(PatientVO patientVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        Long res;

        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

        try {
            if(patientVO.getChart().isEmpty() && patientVO.getSingleChart().isEmpty() && patientVO.getName().isEmpty()){
                // patient 전체 조회
                Query countQuery = new Query();
                res = mongoTemplate.count(countQuery,Map.class,"patient");
            }else {

                Criteria criteria = new Criteria();
                // or 값 존재시
                if (patientVO.isOr()) {
                    criteria.orOperator(
                            //chart
                            Criteria.where("chart")
                                    .regex(patientVO.getChart().get("regex").toString(),patientVO.getChart().get("options").toString()),
                            //name
                            Criteria.where("name")
                                    .regex(patientVO.getName().get("regex").toString(),patientVO.getChart().get("options").toString())
                    );
                }

                //단일 chart 조회시
                if (!patientVO.getSingleChart().isEmpty()) {
                    criteria.andOperator(Criteria.where("chart").is(patientVO.getSingleChart()));
                }

                Query query = new Query(criteria);

                res = mongoTemplate.count(query, Map.class,"patient");
            }

            config.closeMongoClient();

        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("count",res);
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);

    }


    @Override
    public String insertPatient(PatientDto patientDto, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        try {
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

            BasicDBObject query = new BasicDBObject();

            query.put("chart",patientDto.getChart());
            query.put("name",patientDto.getName());
            query.put("birth",patientDto.getBirth());
            query.put("age",patientDto.getAge());
            query.put("gender",patientDto.getGender());
            query.put("home",patientDto.getHome());
            query.put("email",patientDto.getEmail());
            query.put("phone",patientDto.getPhone());
            query.put("addDate",patientDto.getAddDate());
            query.put("bookmark",patientDto.getBookmark());
            query.put("comment",patientDto.getComment());
            query.put("dental",patientDto.getDental());
            query.put("ac",patientDto.getAc());

            mongoTemplate.insert(query,"patient");
            config.closeMongoClient();
        } catch (Exception e) {
            //e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String updatePatient(PatientDto patientDto, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            Query query = new Query();
            Update update = new Update();

            //select
            if(patientDto.getSearchChart().isEmpty()){
                return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
            }
            query.addCriteria(Criteria.where("chart").is(patientDto.getSearchChart()));

            //update
            if(!patientDto.getChart().isEmpty()) {
                update.set("chart", patientDto.getChart());
            }
            if(!patientDto.getName().isEmpty()) {
                update.set("name", patientDto.getName());
            }
            if(!patientDto.getBirth().isEmpty()) {
                update.set("birth", patientDto.getBirth());
            }
            if(!patientDto.getAge().isEmpty()) {
                update.set("age", patientDto.getAge());
            }
            if(!patientDto.getGender().isEmpty()) {
                update.set("gender", patientDto.getGender());
            }
            if(!patientDto.getHome().isEmpty()) {
                update.set("home", patientDto.getHome());
            }
            if(!patientDto.getEmail().isEmpty()) {
                update.set("email", patientDto.getEmail());
            }
            if(!patientDto.getPhone().isEmpty()) {
                update.set("phone", patientDto.getPhone());
            }
            if(!patientDto.getAddDate().isEmpty()) {
                update.set("addDate", patientDto.getAddDate());
            }
            if(!patientDto.getBookmark().isEmpty()) {
                update.set("bookmark", patientDto.getBookmark());
            }
            if(!patientDto.getComment().isEmpty()) {
                update.set("comment", patientDto.getComment());
            }
            if(!patientDto.getDental().isEmpty()) {
                update.set("dental", patientDto.getDental());
            }
            if(!patientDto.getAc().isEmpty()) {
                update.set("ac", patientDto.getAc());
            }

            mongoTemplate.updateMulti(query,update,"patient");
            config.closeMongoClient();
        } catch (Exception e) {
            //e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deletePatient(String chart, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            Criteria criteria = new Criteria();
            criteria.andOperator(Criteria.where("chart").is(chart));
            Query selectQuery = new Query(criteria);
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            //삭제할 데이터조회
            List<Map> res = mongoTemplate.find(selectQuery, Map.class,"patient");

            //데이터 없을시 에러
            if(res.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }

            //삭제할 데이터 deleted 레코드에 삽입
//            BasicDBObject insertQuery = new BasicDBObject();
//            for(Map result : res){
//                Iterator<String> keys = result.keySet().iterator();
//                while(keys.hasNext()){
//                    String key = keys.next();
//                    String value = result.get(key).toString();
//                    insertQuery.put(key,value);
//                }
//            }
//            mongoTemplate.insert(insertQuery,"deleted");

            //deleted 레코드에 삽입된 데이터 날짜 수정
//            Update updateQuery = new Update();
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
//            String toDate = dateFormat.format(new Date());
//            updateQuery.set("deleteddate",toDate);
//            mongoTemplate.updateFirst(selectQuery, updateQuery, Map.class, "deleted");

            //record 데이터 삭제
//            mongoTemplate.remove(selectQuery,"record");

            //patient 데이터 삭제
            mongoTemplate.remove(selectQuery,"patient");
            config.closeMongoClient();
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

}
