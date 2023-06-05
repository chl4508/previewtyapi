package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.RecordService;
import com.morpheus.previewtyapi.vo.v2.RecordVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.RecordController")
public class RecordController {

    @Qualifier("v2.RecordService")
    @Autowired
    RecordService recordService;

    /**
     *
     * findRecordList.class 의 설명을 하단에 작성한다.
     * chart, record, type 명을 통한 record 데이터 리스트 조회
     * parameter : [request, pageNo, pageNum, chart, record, type]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/21
     **/
    @GetMapping(value="/v2/api/search/record-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type 명을 통한 record 데이터 리스트 조회")
    @ResponseBody
    public String findRecordList(HttpServletRequest request,
                                 @RequestParam(value = "pageNo", required=true) @ApiParam(value = "보여줄 페이지 값", required = true) int pageNo,
                                 @RequestParam(value = "pageNum", required=true) @ApiParam(value = "페이지당 보여줄 갯수", required = true) int pageNum,
                                 @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                                 @RequestParam(value = "record", required=false) @ApiParam(value = "record 명", required = false) String record,
                                 @RequestParam(value = "type", required=false) @ApiParam(value = "type 명", required = false) String type){
        return recordService.findRecordList(pageNo, pageNum, chart, record, type, request);
    }

    /**
     *
     * findRecordListCount.class 의 설명을 하단에 작성한다.
     * chart record, type명을 통한 record 데이터 리스트 카운트 조회
     * parameter : [request, chart, record, type]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/21
     **/
    @GetMapping(value="/v2/api/search/record-count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart record, type명을 통한 record 데이터 리스트 카운트 조회")
    @ResponseBody
    public String findRecordListCount(HttpServletRequest request,
                                 @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                                 @RequestParam(value = "record", required=false) @ApiParam(value = "record 명", required = false) String record,
                                 @RequestParam(value = "type", required=false) @ApiParam(value = "type 명", required = false) String type){
        return recordService.findRecordListCount(chart, record, type, request);
    }

    /**
     *
     * insertRecord.class 의 설명을 하단에 작성한다.
     * record 데이터 등록
     * parameter : [request, recordVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/21
     **/
    @PostMapping(value="/v2/api/set/record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="record 데이터 등록")
    @ResponseBody
    public String insertRecord(HttpServletRequest request,
                               @RequestBody @ApiParam(value = "Record 객체", required = false) RecordVO recordVO){
        return recordService.insertRecord(recordVO, request);
    }

    /**
     *
     * updateRecord.class 의 설명을 하단에 작성한다.
     * record 데이터 수정
     * parameter : [request, recordVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/21
     **/
    @PutMapping(value="/v2/api/set/record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="record 데이터 수정")
    @ResponseBody
    public String updateRecord(HttpServletRequest request,
                               @RequestBody@ApiParam(value = "Record 객체", required = false)RecordVO recordVO){
        return recordService.updateRecord(recordVO, request);
    }

    /**
     *
     * deleteRecord.class 의 설명을 하단에 작성한다.
     * record 데이터 삭제
     * parameter : [request, chart, record, type, up_id]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/21
     **/
    @DeleteMapping(value="/v2/api/set/record", consumes = APPLICATION_JSON_VALUE)
    @ApiOperation(value="record 데이터 삭제")
    @ResponseBody
    public String deleteRecord(HttpServletRequest request,
                               @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                               @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                               @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id){
        return recordService.deleteRecord(chart, record, type,up_id, request);
    }


    /**
     *
     * findRecordPk.class 의 설명을 하단에 작성한다.
     * chart, record 명을 통한 record 또는 type의 데이터 pk 조회
     * parameter : [request, chart, record]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/07/07
     **/
    @GetMapping(value="/v2/api/search/record-pk", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record 명을 통한 record 또는 type의 데이터 pk 조회")
    @ResponseBody
    public String findRecordPk(HttpServletRequest request,
                               @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required=false) @ApiParam(value = "record 명", required = false) String record){
        return recordService.findRecordPk(chart, record, request);
    }
}
