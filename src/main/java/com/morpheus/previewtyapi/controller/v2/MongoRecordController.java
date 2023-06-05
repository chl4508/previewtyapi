package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.MongoRecordService;
import com.morpheus.previewtyapi.vo.v2.RecordVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.MongoRecordController")
public class MongoRecordController {

    @Qualifier("v2.MongoRecordService")
    @Autowired
    MongoRecordService mongoRecordService;

    /**
     *
     * findRecordList.class 의 설명을 하단에 작성한다.
     * chart명을 통한 record 데이터 리스트 조회
     * parameter : [request, chart, limit, skip, sort]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/25
     **/
    @GetMapping(value="/v2/api/mongo/search/record-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart명을 통한 record 데이터 리스트 조회")
    @ResponseBody
    public String findRecordList(HttpServletRequest request,
                                 @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                                 @RequestParam(value = "limit", required=true) @ApiParam(value = "조회 limit 값", required = true) int limit,
                                 @RequestParam(value = "skip", required=true) @ApiParam(value = "조회 skip 값", required = true) int skip,
                                 @RequestParam(value = "sort", required=true) @ApiParam(value = "정렬 순서", required = true) String sort){
        return mongoRecordService.findRecordList(chart,limit,skip,sort,request);
    }

    /**
     *
     * findRecord.class 의 설명을 하단에 작성한다.
     * chart,record,type 명을 통한 record 데이터 조회
     * parameter : [request, chart, record, type]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/25
     **/
    @GetMapping(value="/v2/api/mongo/search/record", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart,record,type 명을 통한 record 데이터 조회")
    @ResponseBody
    public String findRecord(HttpServletRequest request,
                             @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                             @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                             @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type){
        return mongoRecordService.findRecord(chart,record,type,request);
    }

    /**
     *
     * findRecordListCount.class 의 설명을 하단에 작성한다.
     * chart 명을 통한 record 데이터 카운트 조회
     * parameter : [request, chart]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/25
     **/
    @GetMapping(value="/v2/api/mongo/search/records-count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart 명을 통한 record 데이터 카운트 조회")
    @ResponseBody
    public String findRecordListCount(HttpServletRequest request,
                                      @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart){
        return mongoRecordService.findRecordListCount(chart,request);
    }

    /**
     *
     * insertRecord.class 의 설명을 하단에 작성한다.
     * record 데이터 등록
     * parameter : [request, recordVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/31
     **/
    @PostMapping(value="/v2/api/mongo/set/record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="record 데이터 등록")
    @ResponseBody
    public String insertRecord(HttpServletRequest request,
                                @RequestBody@ApiParam(value = "Record 객체", required = false)RecordVO recordVO){
        return mongoRecordService.insertRecord(recordVO, request);
    }

    /**
     *
     * updateRecord.class 의 설명을 하단에 작성한다.
     * record 데이터 수정
     * parameter : [request, recordVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/31
     **/
    @PutMapping(value="/v2/api/mongo/set/record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="record 데이터 수정")
    @ResponseBody
    public String updateRecord(HttpServletRequest request,
                               @RequestBody@ApiParam(value = "Record 객체", required = false)RecordVO recordVO){
        return mongoRecordService.updateRecord(recordVO, request);
    }

    /**
     *
     * deleteRecord.class 의 설명을 하단에 작성한다.
     * record 데이터 삭제
     * parameter : [request, chart]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/31
     **/
    @DeleteMapping(value="/v2/api/mongo/set/record", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value="record 데이터 삭제")
    @ResponseBody
    public String deleteRecord(HttpServletRequest request,
                               @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                               @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                               @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id){
        return mongoRecordService.deleteRecord(chart, record, type, up_id, request);
    }


}
