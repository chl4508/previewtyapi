package com.morpheus.previewtyapi.service.impl;

import com.mongodb.BasicDBObject;
import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.dto.SetRecordDto;
import com.morpheus.previewtyapi.service.MongoRecordService;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.AggregateRecordVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("MongoRecordService")
public class MongoRecordServiceImpl implements MongoRecordService {

    private static final Logger logger = LoggerFactory.getLogger(MongoRecordServiceImpl.class);

    @Autowired
    MongoDBConfig config;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;


    @Override
    public String searchFindRecord(RecordVO recordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        List<Map> res = new ArrayList<>();

        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");


        try {
            if (recordVO.getChart().isEmpty() && recordVO.getRecord().isEmpty() && recordVO.getType().isEmpty() && recordVO.getDataFileId().isEmpty()) {
                // record 전체 조회
                if (recordVO.isSort()) {
                    Query query = new Query();
                    // sort
                    Enum sort = Sort.Direction.DESC;
                    if (recordVO.getSortValue().equals("asc") || recordVO.getSortValue().equals("ASC")) {
                        sort = Sort.Direction.ASC;
                    }

                    res = mongoTemplate.find(query.with(Sort.by((Sort.Direction) sort, recordVO.getSortKey())), Map.class, "record");

                } else {
                    res = mongoTemplate.findAll(Map.class, "record");
                }


            } else {

                Criteria criteria = new Criteria();
                // and 값 존재시
                if (recordVO.isAnd()) {
                    if (!recordVO.getChart().isEmpty() && !recordVO.getRecord().isEmpty() && recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //record
                                , Criteria.where("record").is(recordVO.getRecord())
                        );
                    }
                    if (!recordVO.getChart().isEmpty() && !recordVO.getRecord().isEmpty() && !recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //record
                                , Criteria.where("record").is(recordVO.getRecord())
                                //type
                                , Criteria.where("type").is(recordVO.getType())
                        );
                    }
                    if (!recordVO.getChart().isEmpty() && recordVO.getRecord().isEmpty() && !recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //type
                                , Criteria.where("type").is(recordVO.getType())
                        );
                    }
                    if (!recordVO.getChart().isEmpty() && !recordVO.getDataFileId().isEmpty() && recordVO.getRecord().isEmpty()  && recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //data.fileid
                                , Criteria.where("data.fileid").is(recordVO.getDataFileId())
                        );
                    }


                } else {
                    if (!recordVO.getChart().isEmpty()) {
                        criteria.and("chart").is(recordVO.getChart());
                    }
                    if (!recordVO.getDataFileId().isEmpty()) {
                        criteria.and("data.fileid").is(recordVO.getDataFileId());
                    }
                }

                Query query = new Query(criteria);

                if (recordVO.isSort()) {
                    // sort
                    Enum sort = Sort.Direction.DESC;
                    if (recordVO.getSortValue().equals("asc") || recordVO.getSortValue().equals("ASC")) {
                        sort = Sort.Direction.ASC;
                    }

                    res = mongoTemplate.find(query.with(Sort.by((Sort.Direction) sort, recordVO.getSortKey())), Map.class, "record");

                } else {
                    res = mongoTemplate.find(query, Map.class, "record");
                }
            }
            config.closeMongoClient();
            //데이터 없을시 에러
            if (res.size() == 0) {
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken, 101, resCode.resultMessage(101), request.getRequestURI(), request);
        }

        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);

    }

    @Override
    public String searchFindRecordCount(RecordVO recordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        Long res;

        MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");


        try {
            if (recordVO.getChart().isEmpty() && recordVO.getRecord().isEmpty() && recordVO.getType().isEmpty() && recordVO.getDataFileId().isEmpty()) {
                // record 전체 카운트 조회
                Query countQuery = new Query();
                res = mongoTemplate.count(countQuery, Map.class, "record");
            } else {

                Criteria criteria = new Criteria();
                // and 값 존재시
                if (recordVO.isAnd()) {

                    if (!recordVO.getChart().isEmpty() && !recordVO.getRecord().isEmpty() && recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //record
                                , Criteria.where("record").is(recordVO.getRecord())
                        );
                    }
                    if (!recordVO.getChart().isEmpty() && !recordVO.getRecord().isEmpty() && !recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //record
                                , Criteria.where("record").is(recordVO.getRecord())
                                //type
                                , Criteria.where("type").is(recordVO.getType())
                        );
                    }
                    if (!recordVO.getChart().isEmpty() && recordVO.getRecord().isEmpty() && !recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //type
                                , Criteria.where("type").is(recordVO.getType())
                        );
                    }
                    if (!recordVO.getChart().isEmpty() && !recordVO.getDataFileId().isEmpty() && recordVO.getRecord().isEmpty()  && recordVO.getType().isEmpty()) {
                        criteria.andOperator(
                                //chart
                                Criteria.where("chart").is(recordVO.getChart())
                                //data.fileid
                                , Criteria.where("data.fileid").is(recordVO.getDataFileId())
                        );
                    }


                } else {
                    if (!recordVO.getChart().isEmpty()) {
                        criteria.and("chart").is(recordVO.getChart());
                    }
                    if (!recordVO.getDataFileId().isEmpty()) {
                        criteria.and("data.fileid").is(recordVO.getDataFileId());
                    }
                }

                Query query = new Query(criteria);

                res = mongoTemplate.count(query, Map.class, "record");
            }
            config.closeMongoClient();
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken, 101, resCode.resultMessage(101), request.getRequestURI(), request);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("count",res);
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);

    }

    @Override
    public String searchAggregateRecord(AggregateRecordVO aggregateRecordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");


        List<Map> res = new ArrayList<>();
        try {

            //Match
            Criteria matchCriteria = new Criteria();
            Criteria match_arr[]  = new Criteria[aggregateRecordVO.getMatch().size()];
            for(int i = 0;i < match_arr.length;i++){
                Iterator<String> keys = aggregateRecordVO.getMatch().keySet().iterator();
                while(keys.hasNext()){
                    String key = keys.next();
                    String value = aggregateRecordVO.getMatch().get(key).toString();
                    match_arr[i]=Criteria.where(key).is(value);
                }

            }

            matchCriteria.andOperator(match_arr);
            MatchOperation match = Aggregation.match(matchCriteria);


            //sort
            Enum sortDirction = Sort.Direction.DESC;
            Iterator<String> keys = aggregateRecordVO.getSort().keySet().iterator();
            String  sortFields[] = new String[aggregateRecordVO.getSort().size()];
            int i = 0;
            while(keys.hasNext()){
                String key = keys.next();
                String value = aggregateRecordVO.getSort().get(key).toString();
                if (value.equals("asc") || value.equals("ASC")) {
                    sortDirction = Sort.Direction.ASC;
                }
                sortFields[i] = key;
                i++;
            }

            SortOperation sort = Aggregation.sort((Sort.Direction) sortDirction,sortFields);

            Aggregation aggregation = Aggregation.newAggregation(match,sort);
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            res = mongoTemplate.aggregate(aggregation,"record", Map.class).getMappedResults();
            config.closeMongoClient();
            //데이터가 없을시
            if (res.size() == 0) {
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken, 101, resCode.resultMessage(101), request.getRequestURI(), request);
        }

        return resCode.getResultListMapJson(accessToken, 0, resCode.resultMessage(0), res, request.getRequestURI(), request);
    }

    @Override
    public String insertRecord(SetRecordDto setRecordDto, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");

            BasicDBObject query = new BasicDBObject();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            String toDate = dateFormat.format(new Date());
            query.put("chart",setRecordDto.getChart());
            query.put("record",setRecordDto.getRecord());
            query.put("type",setRecordDto.getType());
            query.put("date",toDate);
            query.put("description",setRecordDto.getDescription());
            query.put("study",setRecordDto.getStudy());
            query.put("data",setRecordDto.getData());
            query.put("volume",setRecordDto.getVolume());
            query.put("bone",setRecordDto.getBone());
            query.put("previewty",setRecordDto.getPreviewty());
            mongoTemplate.insert(query,"record");
            config.closeMongoClient();
        } catch (Exception e) {
            //e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String updateRecord(SetRecordDto setRecordDto, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            Query query = new Query();
            Update update = new Update();

            //select
            if(setRecordDto.getSearchChart().isEmpty()){
                return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
            }
            query.addCriteria(Criteria.where("chart").is(setRecordDto.getSearchChart()));
            if(!setRecordDto.getSearchRecord().isEmpty()){
                query.addCriteria(Criteria.where("record").is(setRecordDto.getRecord()));
            }
            if(!setRecordDto.getSearchType().isEmpty()){
                query.addCriteria(Criteria.where("type").is(setRecordDto.getSearchType()));
            }

            //update
            if(!setRecordDto.getChart().isEmpty()) {
                update.set("chart", setRecordDto.getChart());
            }
            if(!setRecordDto.getRecord().isEmpty()){
                update.set("record",setRecordDto.getRecord());
            }
            if(!setRecordDto.getType().isEmpty()){
                update.set("type",setRecordDto.getType());
            }
            if(!setRecordDto.getDescription().isEmpty()){
                update.set("description",setRecordDto.getDescription());
            }
            if(!setRecordDto.getStudy().isEmpty()){
                update.set("study",setRecordDto.getStudy());
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
    public String deleteRecord(String chart,String record,String type, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");

        try {
            Criteria criteria = new Criteria();
            Query selectQuery = new Query(criteria);
            selectQuery.addCriteria(Criteria.where("chart").is(chart));
            if(!record.isEmpty()){
                selectQuery.addCriteria(Criteria.where("record").is(record));
            }
            if(!type.isEmpty()){
                selectQuery.addCriteria(Criteria.where("type").is(type));
            }
            MongoTemplate mongoTemplate = config.multiMongoTemplate("previewty");
            //삭제할 데이터조회
            List<Map> res = mongoTemplate.find(selectQuery, Map.class,"record");

            //데이터 없을시 에러
            if(res.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }

            //삭제할 데이터 deleted 레코드에 삽입
            BasicDBObject insertQuery = new BasicDBObject();
            for(Map result : res){
                Iterator<String> keys = result.keySet().iterator();
                while(keys.hasNext()){
                    String key = keys.next();
                    String value = result.get(key).toString();
                    insertQuery.put(key,value);
                }
            }
            mongoTemplate.insert(insertQuery,"deleted");

            //deleted 레코드에 삽입된 데이터 날짜 수정
            Update updateQuery = new Update();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
            String toDate = dateFormat.format(new Date());
            updateQuery.set("deleteddate",toDate);
            mongoTemplate.updateFirst(selectQuery, updateQuery, Map.class, "deleted");

            //record 데이터 삭제
            mongoTemplate.remove(selectQuery,"record");
            config.closeMongoClient();
        } catch (Exception e) {
//            e.printStackTrace();
            config.closeMongoClient();
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
