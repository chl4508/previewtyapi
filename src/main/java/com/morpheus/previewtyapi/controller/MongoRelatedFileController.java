package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.dto.SetSubRecordDto;
import com.morpheus.previewtyapi.service.MongoRecordSubService;
import com.morpheus.previewtyapi.service.MongoRelatedFileService;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.FileRecordVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import com.morpheus.previewtyapi.vo.RelatedVO;
import com.morpheus.previewtyapi.vo.SubRecordVO;
import io.swagger.annotations.ApiOperation;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class MongoRelatedFileController {

    private static final Logger logger = LoggerFactory.getLogger(MongoRelatedFileController.class);

    @Qualifier("MongoRelatedFileService")
    @Autowired
    MongoRelatedFileService mongoRelatedFileService;

    @Autowired
    ResCode resCode;



    @PostMapping(value ="/api/upload/related-file", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record file 업로드")
    public String uploadRelatedFile(@RequestParam(value = "file") MultipartFile multipartFile,
                                    @RequestParam(value ="pushData") String pushData,
                                    @RequestParam(value ="chart") String chart,
                                    @RequestParam(value ="record") String record,
                                    @RequestParam(value ="chartType") String chartType,
                                    HttpServletRequest request) {
        FileRecordVO fileRecordVO = new FileRecordVO();
        fileRecordVO.setChart(chart);
        fileRecordVO.setRecord(record);
        fileRecordVO.setType(chartType);
        fileRecordVO.setPushData(pushData);

        return mongoRelatedFileService.insertRelatedFile(multipartFile,fileRecordVO,request);
    }

    @GetMapping(value ="/api/download/related-file", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record file 다운로드")
    public String downloadRelatedFile(@RequestParam("fileId")String fileId, HttpServletRequest request) {
        return mongoRelatedFileService.selectRelatedFile(fileId,request);
    }


    @PostMapping(value ="/api/search/related-file", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record file 조회")
    public String aggregateRelatedFile(@RequestBody RelatedVO relatedVO, HttpServletRequest request) {
        return mongoRelatedFileService.aggregateRelatedFile(relatedVO,request);
    }

    @PutMapping(value ="/api/set/related-file", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record file 정보 수정")
    public String uploadRelatedFile(@RequestParam(value = "file") MultipartFile multipartFile,
                                    @RequestParam(value ="dataFormat") String dataFormat,
                                    @RequestParam(value ="elemMatchKeys") String elemMatchKeys,
                                    @RequestParam(value ="elemMatchValues") String elemMatchValues,
                                    @RequestParam(value ="chart") String chart,
                                    @RequestParam(value ="record") String record,
                                    @RequestParam(value ="chartType") String chartType,
                                    HttpServletRequest request) {
        FileRecordVO fileRecordVO = new FileRecordVO();
        fileRecordVO.setChart(chart);
        fileRecordVO.setRecord(record);
        fileRecordVO.setType(chartType);
        fileRecordVO.setDataFormat(dataFormat);
        fileRecordVO.setElemMatchKeys(elemMatchKeys);
        fileRecordVO.setElemMatchValues(elemMatchValues);

        return mongoRelatedFileService.updateRelatedFile(multipartFile, fileRecordVO,request);
    }

    @DeleteMapping(value ="/api/set/delete-file", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="record file 정보 삭제")
    public String uploadRelatedFile(@RequestParam(value = "fileId") String fileId,
                                    HttpServletRequest request) {
        return mongoRelatedFileService.deleteRelatedFile(fileId, request);
    }





}
