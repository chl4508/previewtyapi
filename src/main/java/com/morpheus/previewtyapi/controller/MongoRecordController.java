package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.dto.SetRecordDto;
import com.morpheus.previewtyapi.service.MongoRecordService;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.AggregateRecordVO;
import com.morpheus.previewtyapi.vo.PatientVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class MongoRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MongoRecordController.class);

    @Qualifier("MongoRecordService")
    @Autowired
    MongoRecordService mongoRecordService;

    @Autowired
    ResCode resCode;


    @PostMapping(value="/api/search/find-record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record collection 데이터 조회")
    public String searchFindRecord(@RequestBody RecordVO recordVO, HttpServletRequest request){
        return mongoRecordService.searchFindRecord(recordVO,request);
    }

    @PostMapping(value="/api/search/record-count", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record collection 카운트 조회")
    public String searchRecordCount(@RequestBody RecordVO recordVO, HttpServletRequest request){
        return mongoRecordService.searchFindRecordCount(recordVO,request);
    }


    @PostMapping(value="/api/search/aggregate-record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="aggregate한 record collection 데이터 조회")
    public String searchAggregateRecord(@RequestBody AggregateRecordVO aggregateRecordVO, HttpServletRequest request){
        return mongoRecordService.searchAggregateRecord(aggregateRecordVO,request);
    }


    @PostMapping(value="/api/set/record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record collection 데이터 등록")
    public String insertRecord(@RequestBody SetRecordDto setRecordDto, HttpServletRequest request){
        return mongoRecordService.insertRecord(setRecordDto,request);
    }


    @PutMapping(value="/api/set/record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record collection 데이터 수정")
    public String updateRecord(@RequestBody SetRecordDto setRecordDto, HttpServletRequest request){
        return mongoRecordService.updateRecord(setRecordDto,request);
    }

    @DeleteMapping(value="/api/set/record", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record collection 데이터 삭제")
    public String deleteRecord(@RequestParam(value = "chart", required = true)
                               @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required = false)
                               @ApiParam(value = "record 명", required = false) String record,
                               @RequestParam(value = "type", required = false)
                               @ApiParam(value = "type 명", required = false) String type,
                               HttpServletRequest request){
        return mongoRecordService.deleteRecord(chart,record,type,request);
    }



}
