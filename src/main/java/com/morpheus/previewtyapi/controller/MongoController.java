package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.service.MongoService;
import com.morpheus.previewtyapi.util.ResCode;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@ApiIgnore
public class MongoController {

    private static final Logger logger = LoggerFactory.getLogger(MongoController.class);

    @Qualifier("MongoService")
    @Autowired
    MongoService patientsMongoService;

    @Autowired
    ResCode resCode;







    /**
    *
    * mongoFind.class 의 설명을 하단에 작성한다.
    * mongoDB에서 단일조회를 위한 Api 서비스
    *
    * parameter : [databaseName, collectionName, fieldName, fieldDataName]
    * returnType : java.lang.String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2021/08/05
    **/
//    @GetMapping(value="/api/mongoTest", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String mongoFind(String databaseName, String collectionName, String fieldName, String fieldDataName){
//
//
//        databaseName = "previewty";
//        collectionName = "record";
//        fieldName = "chart";
//        fieldDataName = "test";
//
//        logger.info("============================");
//        logger.info("PatientsMongoController");
//        logger.info("databaseName : "+databaseName);
//        logger.info("collectionName : "+collectionName);
//        logger.info("fieldName : "+fieldName);
//        logger.info("fieldDataName : "+fieldDataName);
//        logger.info("============================");
//        Map<String, Object> resultObject = patientsMongoService.mongoFind(databaseName, collectionName, fieldName,fieldDataName);
//        String resMsg = resCode.resultMessage(0);
//        //String result = resCode.getResultJson(0, resMsg, resultObject);
//        String result = "";
//        return result;
//    }


    /**
    * classRemark
    * mongoFindAll.class 의 설명을 하단에 작성한다.
    *
    * parameter :
    * returnType :
    * @author 최연식
    * @version 1.0.0
    * 작성일 2021/08/09
    **/
//    @GetMapping(value="/tests", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String mongoFindAll(String databaseName){
//        databaseName = "previewty";
//        //collectionName = "record";
//
//
//        logger.info("============================");
//        logger.info("PatientsMongoController");
//        logger.info("databaseName : "+databaseName);
//        //logger.info("collectionName : "+collectionName);
//        logger.info("============================");
//        patientsMongoService.mongoFindCount(databaseName);
//        return "";
//    }


    /**
    *
    * mongoFindAll2.class 의 설명을 하단에 작성한다.
    *
    * parameter : [databaseName]
    * returnType : java.lang.String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2021/08/13
    **/
//    @GetMapping(value="/testss", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String mongoFindAll2(String databaseName){
//        databaseName = "previewty";
//        //collectionName = "record";
//
//
//        logger.info("============================");
//        logger.info("PatientsMongoController");
//        logger.info("databaseName : "+databaseName);
//        //logger.info("collectionName : "+collectionName);
//        logger.info("============================");
//        patientsMongoService.mongoFindList(databaseName);
//        return "";
//    }


//    @GetMapping(value="/api/test", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String test(String test, HttpServletRequest request){
//        String accessToken = request.getHeader("access_token");
//        if(accessToken == null){
//            return resCode.getResultErrorJson("", 112, resCode.resultMessage(112), "url");
//        }

//        return "테스트";
//    }



}
