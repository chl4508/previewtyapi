package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.MongoPatientService;
import com.morpheus.previewtyapi.vo.v2.PatientVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.MongoPatientController")
public class MongoPatientController {


    @Qualifier("v2.MongoPatientService")
    @Autowired
    MongoPatientService mongoPatientService;


    /**
     *
     * findPatientList.class 의 설명을 하단에 작성한다.
     * 전체 patient 데이터 리스트 조회
     * parameter : [patientVO, request]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/24
     **/
    @GetMapping(value="/v2/api/mongo/search/patient-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 리스트 조회")
    @ResponseBody
    public String findPatientList(HttpServletRequest request,
                                  @RequestParam(value = "limit", required=true) @ApiParam(value = "조회 limit 값", required = true) int limit,
                                  @RequestParam(value = "skip", required=true) @ApiParam(value = "조회 skip 값", required = true) int skip,
                                  @RequestParam(value = "sort", required=true) @ApiParam(value = "정렬 순서", required = true) String sort
                                  ){
        return mongoPatientService.findPatientList(limit,skip,sort,request);
    }


    /**
    *
    * findPatient.class 의 설명을 하단에 작성한다.
    * chart 명에 맞는 patient 를 조회한다
    * parameter : [request, chart]
    * returnType : java.lang.String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2022/05/25
    **/
    @GetMapping(value="/v2/api/mongo/search/patient", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="chart 명에 맞는 patient 데이터 조회")
    @ResponseBody
    public String findPatient(HttpServletRequest request,
                              @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart){
        return mongoPatientService.findPatient(chart, request);
    }


    /**
     *
     * findPatientCount.class 의 설명을 하단에 작성한다.
     * patient 데이터 리스트 카운트 조회
     * parameter : [request]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/25
     **/
    @GetMapping(value="/v2/api/mongo/search/patients-count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 리스트 카운트 조회")
    @ResponseBody
    public String findPatientListCount(HttpServletRequest request){
        return mongoPatientService.findPatientListCount(request);
    }

    /**
     *
     * updatePatient.class 의 설명을 하단에 작성한다.
     * patient 데이터 수정
     * parameter : [patientVO, request]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/25
     **/
    @PutMapping(value="/v2/api/mongo/set/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 수정")
    @ResponseBody
    public String updatePatient(HttpServletRequest request,
                                @RequestBody @ApiParam(value = "Patient 객체", required = false) PatientVO patientVO){
        return mongoPatientService.updatePatient(patientVO, request);
    }

    /**
     *
     * insertPatient.class 의 설명을 하단에 작성한다.
     * patient 데이터 등록
     * parameter : [request, patientVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/05/25
     **/
    @PostMapping(value="/v2/api/mongo/set/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 등록")
    @ResponseBody
    public String insertPatient(HttpServletRequest request,
                                @RequestBody @ApiParam(value = "Patient 객체", required = false) PatientVO patientVO){
        return mongoPatientService.insertPatient(patientVO, request);
    }


    /**
    *
    * deletePatient.class 의 설명을 하단에 작성한다.
    * patient 데이터 삭제
    * parameter : [request, chart]
    * returnType : java.lang.String
    * @author 최연식
    * @version 1.0.0
    * 작성일 2022/05/25
    **/
    @DeleteMapping(value="/v2/api/mongo/set/patient", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 삭제")
    @ResponseBody
    public String deletePatient(HttpServletRequest request,
                                @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                                @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id){
        return mongoPatientService.deletePatient(chart, up_id, request);
    }


}
