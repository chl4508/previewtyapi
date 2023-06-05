package com.morpheus.previewtyapi.controller.v2;


import com.morpheus.previewtyapi.service.v2.MongoDataService;
import com.morpheus.previewtyapi.vo.v2.DataVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.MongoDataController")
public class MongoDataController {

    @Qualifier("v2.MongoDataService")
    @Autowired
    MongoDataService mongoDataService;


    /**
    *
    * findDataList.class 의 설명을 하단에 작성한다.
    * chart, record, type 명을 통한 data 데이터 리스트 조회
    * parameter : [request, chart, record, type, limit, skip]
    * returnType : java.lang.String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2022/06/09
    **/
    @GetMapping(value="/v2/api/mongo/search/data-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type 명을 통한 data 데이터 리스트 조회")
    @ResponseBody
    public String findDataList(HttpServletRequest request,
                               @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                               @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                               @RequestParam(value = "limit", required=true) @ApiParam(value = "조회 limit 값 ", required = true) int limit,
                               @RequestParam(value = "skip", required=true) @ApiParam(value = "조회 skip 값", required = true) int skip){
        return mongoDataService.findDataList(chart, record, type, limit, skip, request);
    }

    /**
     *
     * findData.class 의 설명을 하단에 작성한다.
     * chart, record, type, name 명을 통한 data 데이터 조회
     * parameter : [request, chart, record, type, name]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/09
     **/
    @GetMapping(value="/v2/api/mongo/search/data", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type, name 명을 통한 data 데이터 조회")
    @ResponseBody
    public String findData(HttpServletRequest request,
                               @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                               @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                               @RequestParam(value = "name", required=true) @ApiParam(value = "name 명", required = true) String name){
        return mongoDataService.findData(chart, record, type, name, request);
    }

    /**
     *
     * insertData.class 의 설명을 하단에 작성한다.
     * data 데이터 등록
     * parameter : [request, dataVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/09
     **/
    @PostMapping(value="/v2/api/mongo/set/data", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="data 데이터 등록")
    @ResponseBody
    public String insertData(HttpServletRequest request,
                             @RequestBody @ApiParam(value = "Data 객체", required = false) DataVO dataVO){
        return mongoDataService.insertData(dataVO, request);
    }

    /**
     *
     * deleteData.class 의 설명을 하단에 작성한다.
     * chart, record, type, name 명을 통한 data 데이터 삭제
     * parameter : [request, chart, record, type, name, up_id]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/09
     **/
    @DeleteMapping(value="/v2/api/mongo/set/data", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type, name 명을 통한 data 데이터 삭제")
    @ResponseBody
    public String deleteData(HttpServletRequest request,
                             @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                             @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                             @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                             @RequestParam(value = "name", required=true) @ApiParam(value = "name 명", required = true) String name,
                             @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id){
        return mongoDataService.deleteData(chart, record, type, name, up_id, request);
    }


}
