package com.morpheus.previewtyapi.service.impl;


import com.mongodb.BasicDBObject;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.dto.SetSubRecordDto;
import com.morpheus.previewtyapi.service.MongoRecordSubService;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.SubRecordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static com.morpheus.previewtyapi.service.GenericAggregationUtils.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

@Service("MongoRecordSubService")
public class MongoRecordSubServiceImpl implements MongoRecordSubService {

    private static final Logger logger = LoggerFactory.getLogger(MongoRecordSubServiceImpl.class);

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;


    @Override
    public String searchAggregateRecordSub(SubRecordVO subRecordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        List<Map> res = new ArrayList<>();
        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

//            String match1 = "{ \"chart\" : \"00347499\", \"record\" : \"001\", \"type\" : \"0\" } }";
//            //String unwind = "$data";
//            String match2 = "{ \"$and\" : [ { \"$or\" : [ { \"data.format\" : \"bmp\" }," +
//                    " { \"data.format\" : \"jpg\" }, { \"data.format\" : \"png\" }, { \"data.format\" : \"tif\" }, { \"data.format\" : \"gif\" } ] }, { \"data.name\" : { \"$not\" : { \"$regex\" : \"Thumb\", \"$options\" : \"i\" } } }, { \"data.name\" : { \"$not\" : { \"$regex\" : \"Extra\", \"$options\" : \"i\" } } } ] }";
//            String group = "{ \"_id\" : \"$_id\", \"data\" : { \"$push\" : \"$data\" } }";

        Aggregation aggregation = null;


        try {
            switch (subRecordVO.getSelectName()){
                case "match-unwind-count":
                    aggregation = newAggregation(
                            aggregate("$match", subRecordVO.getMatch1())
                            , Aggregation.unwind(subRecordVO.getUnwind())
                            , Aggregation.count().as("count")
                    );
                    break;
                case "match-unwind-group":
                    aggregation = newAggregation(
                            aggregate("$match", subRecordVO.getMatch1())
                            , Aggregation.unwind(subRecordVO.getUnwind())
                            , aggregate("$group", subRecordVO.getGroup())
                    );
                    break;
                case "match-unwind-match-count":
                    aggregation = newAggregation(
                            aggregate("$match", subRecordVO.getMatch1())
                            , Aggregation.unwind(subRecordVO.getUnwind())
                            , aggregate("$match", subRecordVO.getMatch2())
                            , Aggregation.count().as("count")
                    );
                    break;
                case "match-unwind-match-group":
                    aggregation = newAggregation(
                            aggregate("$match", subRecordVO.getMatch1())
                            , Aggregation.unwind(subRecordVO.getUnwind())
                            , aggregate("$match", subRecordVO.getMatch2())
                            , aggregate("$group", subRecordVO.getGroup())
                    );
                    break;
                default:
                    config.closeMongoClient();
                    return resCode.getResultErrorJson(accessToken, 101, resCode.resultMessage(101), request.getRequestURI(), request);
            }
            res = mongoTemplate.aggregate(aggregation,"record", Map.class).getMappedResults();
            config.closeMongoClient();
            //데이터가 없을시
            if (res.size() == 0) {
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }

        } catch (Exception e) {
            //e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken, 102, resCode.resultMessage(102), request.getRequestURI(), request);
        }
        //몽고디비 쿼리상 정렬자체가 제대로 안되고있어서 로직상에서도 제거
//        if(subRecordVO.getSelectName().equals("match-unwind-match-group-sort")) {
//            Enum sort = Sort.Direction.DESC;
//            // sort
//            if (subRecordVO.getSortValue().equals("asc") || subRecordVO.getSortValue().equals("ASC")) {
//                sort = Sort.Direction.ASC;
//            }
//            aggregation = newAggregation(
//                aggregate("$match", subRecordVO.getMatch1())
//                , Aggregation.unwind(subRecordVO.getUnwind())
//                , aggregate("$match", subRecordVO.getMatch2())
//                , aggregate("$group", subRecordVO.getGroup())
//                , sort(Sort.by((Sort.Direction) sort, subRecordVO.getSortKey()))
//            );
//        }
        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);
    }

    @Override
    public String updateRecordSub(SetSubRecordDto setSubRecordDto, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            Query query = new Query();
            Update update = new Update();

            query.addCriteria(Criteria.where("chart").is(setSubRecordDto.getChart()));
            query.addCriteria(Criteria.where("record").is(setSubRecordDto.getRecord()));
            query.addCriteria(Criteria.where("data.name").is(setSubRecordDto.getDataName()));
            query.addCriteria(Criteria.where("type").is(setSubRecordDto.getType()));

            Iterator<String> keys = setSubRecordDto.getSetKeyVal().keySet().iterator();
            while(keys.hasNext()){
                String key = keys.next();
                String value = setSubRecordDto.getSetKeyVal().get(key).toString();
                update.set(key,value);
            }

            mongoTemplate.updateMulti(query,update,"record");
            config.closeMongoClient();
        } catch (Exception e) {
            //e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteRecordSub(SetSubRecordDto setSubRecordDto, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            String toDate = dateFormat.format(new Date());
            //updateQuery.set("deleteddate",toDate);

            //insert
            BasicDBObject insertQuery = new BasicDBObject();
            insertQuery.put("chart",setSubRecordDto.getChart());
            insertQuery.put("record", setSubRecordDto.getRecord());
            insertQuery.put("type",setSubRecordDto.getType());
            insertQuery.put("adddate",setSubRecordDto.getAdddate());
            insertQuery.put("deleteddate",toDate);
            insertQuery.put("name",setSubRecordDto.getName());
            insertQuery.put("format",setSubRecordDto.getFormat());
            insertQuery.put("fileid",setSubRecordDto.getFileid());

            mongoTemplate.insert(insertQuery,"deleted");





            //update
            Query updateQuery = new Query();
            Query update = new Query();

            updateQuery.addCriteria(Criteria.where("chart").is(setSubRecordDto.getChart()));
            updateQuery.addCriteria(Criteria.where("record").is(setSubRecordDto.getRecord()));
            updateQuery.addCriteria(Criteria.where("data.name").is(setSubRecordDto.getDataName()));
            updateQuery.addCriteria(Criteria.where("type").is(setSubRecordDto.getType()));

            Iterator<String> keys = setSubRecordDto.getSetKeyVal().keySet().iterator();
            while(keys.hasNext()){
                String key = keys.next();
                String value = setSubRecordDto.getSetKeyVal().get(key).toString();
                update.addCriteria(Criteria.where(key).is(value));
            }

            mongoTemplate.updateMulti(updateQuery,new Update().pull("data",update),"record");



            config.closeMongoClient();
        } catch (Exception e) {
            //e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
