package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.DataService;
import com.morpheus.previewtyapi.vo.v2.DataVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.DataController")
public class DataController {

    @Qualifier("v2.DataService")
    @Autowired
    DataService dataService;


    /**
     *
     * findDataList.class 의 설명을 하단에 작성한다.
     * chart, record, type, name 명을 통한 data 데이터 리스트 조회
     * parameter : [request, chart, record, type, limit, skip]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/09
     **/
    @GetMapping(value="/v2/api/search/data-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type, name 명을 통한 data 데이터 리스트 조회")
    @ResponseBody
    public String findDataList(HttpServletRequest request,
                               @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                               @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                               @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                               @RequestParam(value = "name", required=false) @ApiParam(value = "name 명", required = false) String name,
                               @RequestParam(value = "pageNo", required=true) @ApiParam(value = "보여줄 페이지 값", required = true) int pageNo,
                               @RequestParam(value = "pageNum", required=true) @ApiParam(value = "페이지당 보여줄 갯수", required = true) int pageNum){
        return dataService.findDataList(pageNo, pageNum, chart, record, type, name, request);
    }

    /**
     *
     * findPatientListCount.class 의 설명을 하단에 작성한다.
     * chart, record, type, name 명을 통한 data 데이터 카운트 조회
     * parameter : [request, chart, record, type, name]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/30
     **/
    @GetMapping(value="/v2/api/search/data-count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type, name 명을 통한 data 데이터 카운트 조회")
    @ResponseBody
    public String findPatientListCount(HttpServletRequest request,
                                       @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                                       @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                                       @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                                       @RequestParam(value = "name", required=false) @ApiParam(value = "name 명", required = false) String name){
        return dataService.findDataListCount(chart, record, type, name, request);
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
    @PostMapping(value="/v2/api/set/data", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="data 데이터 등록")
    @ResponseBody
    public String insertData(HttpServletRequest request,
                             @RequestBody @ApiParam(value = "Data 객체", required = false) DataVO dataVO){
        return dataService.insertData(dataVO, request);
    }


    /**
     *
     * updateData.class 의 설명을 하단에 작성한다.
     * data 데이터 수정
     * parameter : [request, dataVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/22
     **/
    @PutMapping(value="/v2/api/set/data", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="data 데이터 수정")
    @ResponseBody
    public String updateData(HttpServletRequest request,
                             @RequestBody @ApiParam(value = "Data 객체", required = false) DataVO dataVO){
        return dataService.updateData(dataVO, request);
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
    @DeleteMapping(value="/v2/api/set/data", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart, record, type, name 명을 통한 data 데이터 삭제")
    @ResponseBody
    public String deleteData(HttpServletRequest request,
                             @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                             @RequestParam(value = "record", required=true) @ApiParam(value = "record 명", required = true) String record,
                             @RequestParam(value = "type", required=true) @ApiParam(value = "type 명", required = true) String type,
                             @RequestParam(value = "name", required=true) @ApiParam(value = "name 명", required = true) String name,
                             @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id){
        return dataService.deleteData(chart, record, type, name, up_id, request);
    }
}
