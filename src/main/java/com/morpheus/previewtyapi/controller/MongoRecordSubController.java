package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.dto.SetRecordDto;
import com.morpheus.previewtyapi.dto.SetSubRecordDto;
import com.morpheus.previewtyapi.service.MongoRecordService;
import com.morpheus.previewtyapi.service.MongoRecordSubService;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.AggregateRecordVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import com.morpheus.previewtyapi.vo.SubRecordVO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class MongoRecordSubController {

    private static final Logger logger = LoggerFactory.getLogger(MongoRecordSubController.class);

    @Qualifier("MongoRecordSubService")
    @Autowired
    MongoRecordSubService mongoRecordSubService;

    @Autowired
    ResCode resCode;


    @PostMapping(value="/api/search/aggregate-record-sub", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="aggregate한 record sub collection 데이터 조회")
    public String searchAggregateRecordSub(@RequestBody SubRecordVO subRecordVO, HttpServletRequest request){
        return mongoRecordSubService.searchAggregateRecordSub(subRecordVO,request);
    }

    @PutMapping(value="/api/set/record-sub", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record sub collection 데이터 수정")
    public String updateRecordSub(@RequestBody SetSubRecordDto setSubRecordDto, HttpServletRequest request){
        return mongoRecordSubService.updateRecordSub(setSubRecordDto,request);
    }

    @PostMapping(value="/api/delete/record-sub", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record sub collection 데이터 삭제")
    public String deleteRecordSub(@RequestBody SetSubRecordDto setSubRecordDto, HttpServletRequest request){
        return mongoRecordSubService.deleteRecordSub(setSubRecordDto,request);
    }



}
