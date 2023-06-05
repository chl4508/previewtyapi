package com.morpheus.previewtyapi.service.impl;

import com.mongodb.BasicDBObject;

import com.mongodb.DBObject;

import com.morpheus.previewtyapi.config.MongoDBConfig;
import com.morpheus.previewtyapi.doc.RecordDoc;
import com.morpheus.previewtyapi.service.MongoService;
import com.morpheus.previewtyapi.util.ConvertUtil;
import org.bson.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;


import java.util.*;


@Service("MongoService")
public class MongoServiceImpl implements MongoService {

    private static final Logger logger = LoggerFactory.getLogger(MongoServiceImpl.class);

    @Autowired
    MongoDBConfig config;

    @Autowired
    ConvertUtil convertUtil;

    @Override
    public Map<String, Object> mongoFind(String databaseName, String collectionName, String fieldName, String fieldDataName) {


        logger.info("============================");
        logger.info("PatientsMongoServiceImpl");

//        get example
//        db.getCollection('record').find({"chart":"test"}
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("fieldName").is("fieldDataName"));

        String test = "{ \"$match\" : { \"chart\" : \"CHART_NUMBER\", \"record\" : \"001\", \"type\" : \"0\" } }, { \"$unwind\" : \"$data\" }, { \"$match\" : { \"$and\" : [ { \"data.name\" : { \"$regex\" : { \"$regex\" : \"Thumb\", \"$options\" : \"i\" } } }, { \"data.name\" : { \"$not\" : { \"$regex\" : { \"$regex\" : \"Extra\", \"$options\" : \"i\" } } } } ] } }, { \"$count\" : \"count\" }";

        Query query = new Query(criteria);
        System.out.println("test : "+query.toString());

        fieldName = "chart";
        fieldDataName = "test";

        MongoTemplate mongoTemplate = config.multiMongoTemplate(databaseName);
        RecordDoc record = mongoTemplate.findOne(query, RecordDoc.class, collectionName);
        System.out.println("record : "+record);
        //record.setRecord(record.getDate().replaceAll("\"",""));
        Map<String, Object> result = convertUtil.convertObjectToMap(record);


        //String result = new Gson().toJson(record);
        //result = result.replaceAll("\"","");
        //result = result.replaceAll("\\\"","");


        logger.info("record : " + record);
//        logger.info("getDate : " + record.getDate());
//        logger.info("getType : " + record.getType());
//        logger.info("getDescription : " + record.getDescription());
        logger.info("getChart : " + record.getChart());
        logger.info("getId : " + record.getId());
        logger.info("getClass : " + record.getClass());

//        Criteria criteria2 = new Criteria();
//        criteria2.andOperator(Criteria.where("chart").is("test001"));
//        Query query2 = new Query(criteria2);
//        logger.info("query2 : "+query);
//        MongoDatabaseFactory factory2 = new SimpleMongoClientDatabaseFactory(mongoClient, "ctviewer");
//        MongoTemplate t2 = new MongoTemplate(factory2);
//        RecordDoc record2 = t2.findOne(query2, RecordDoc.class, "record");
//        logger.info("record2 : "+record2);
//        logger.info("getDate2 : "+record2.getDate());
//        logger.info("getType2 : "+record2.getType());
//        logger.info("getDescription2 : "+record2.getDescription());
//        logger.info("getChart2 : "+record2.getChart());
//        logger.info("getId2 : "+record2.getId());
//        logger.info("getClass2 : "+record2.getClass());

        logger.info("============================");

        //mongoClient.close();


        return result;

    }

    @Override
    public Map<String, Object> mongoFindCount(String databaseName) {
//        count example
//        db.getCollection('record').aggregate([
//                {$match:{"chart":"test","record":"001","type":"0"}},
//        {$project:
//            {"count":
//                {$size:
//                    {$filter:
//                        {input:"$data",as:"data",cond:
//                            {$and:[
//                                    {$eq:["$$data.name","1"]},
//                                    {$eq:["$$data.format","1"]}
//                                ]}
//                        }
//                    }
//                }
//            }
//        }
//        ])

        //Match
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("chart").is("test"), Criteria.where("record").is("001"), Criteria.where("type").is("0"));
        MatchOperation match = Aggregation.match(criteria);


        ProjectionOperation agg = Aggregation.project() //
                .and(new AggregationExpression() {
                    @Override
                    public Document toDocument(AggregationOperationContext context) {
                        DBObject cond = new BasicDBObject();
                        cond.put("input", "$data");
                        cond.put("as","data");
                        List<DBObject> andExpressions = new ArrayList<DBObject>();
                        andExpressions.add(0, new BasicDBObject("$eq", Arrays.<Object>asList("$$data.name", "1")));
                        andExpressions.add(1, new BasicDBObject("$eq", Arrays.<Object>asList("$$data.format", "1")));
                        cond.put("cond", new BasicDBObject("$and", andExpressions));
                        DBObject filterExpression = new BasicDBObject();
                        filterExpression.put("$filter",cond);
                        return new Document("$size", filterExpression);
                    }
                }).as("count");

        DBObject countExpression = new BasicDBObject();
        countExpression.put("count", agg);
        Aggregation aggregation = Aggregation.newAggregation(match,agg);
        logger.info("===============================");
        logger.info("aggregation");
        logger.info(String.valueOf(aggregation));
        logger.info("===============================");
        MongoTemplate mongoTemplate = config.multiMongoTemplate(databaseName);
        List<Map> ress =mongoTemplate.aggregate(aggregation, "record", Map.class).getMappedResults();


        logger.info("_id : "+ress.get(0).get("_id"));
        logger.info("count : "+ress.get(0).get("count"));

        ress.forEach(System.out::println);	//결과 출력

        return null;
    }

    @Override
    public Map<String, Object> mongoFindList(String databaseName) {
        //        get example
//        db.getCollection('record').aggregate([
//                {$match:{"chart":"test","record":"001","type":"0"}},
//        {$unwind:"$data"},
//        {$match:{$and:[{"data.name":{$regex:"Thumb"}}]}},
//        {"$group":{"_id":"$_id","data":{$push:"$data"}}}])




        //Match
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("chart").is("test"), Criteria.where("record").is("001"), Criteria.where("type").is("0"));
        MatchOperation match = Aggregation.match(criteria);

        //Unwind
        UnwindOperation unwind = Aggregation.unwind("data");

        //Match2
        Criteria criteria2 = new Criteria();
        criteria2.andOperator(Criteria.where("data.name").regex("Thumb"));
        MatchOperation match2 = Aggregation.match(criteria2);

        //Group
        GroupOperation group = Aggregation.group("_id").push("data").as("data");

        Aggregation aggregation = Aggregation.newAggregation(match,unwind,match2,group);
        MongoTemplate mongoTemplate = config.multiMongoTemplate(databaseName);
        List<Map> ress = mongoTemplate.aggregate(aggregation,"record", Map.class).getMappedResults();
        logger.info("=====================================");
        logger.info("aggregation");
        logger.info(String.valueOf(aggregation));
        ress.forEach(System.out::println);	//결과 출력
        logger.info("=====================================");

        return null;
    }

//    @Override
//    public Map<String, Object> mongoInsertList(String databaseName) {
//
//        MongoTemplate mongoTemplate = config.multiMongoTemplate("databaseName");
//        BasicDBObject doc = new BasicDBObject();
//        doc.put("name","test");
//        try {
//            mongoTemplate.insert(doc,"test?");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> mongoUpdateList(String databaseName) {
//
//
//
//
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> mongoDelete(String databaseName) {
//        return null;
//    }


//    new DBObjectAggregationOperation((DBObject) JSON.parse(
//            String.format("{\"$match\":" +
//            "{ \"id\" : %d, \"dseq\" : %d, \"pid\" : %d," +
//            " date : { \"$gte\" : \"%s\" , \"$lte\" : \"%s\"}}" +
//            "}",
//            param.getId(),
//            param.getDSeq(),
//                    param.getPid(),
//                    param.getStartDateString(), param.getEndDateString())));
}
