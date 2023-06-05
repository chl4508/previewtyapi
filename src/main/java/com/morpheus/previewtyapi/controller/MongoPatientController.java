package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.dto.PatientDto;
import com.morpheus.previewtyapi.service.MongoPatientService;
import com.morpheus.previewtyapi.service.MongoService;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.PatientVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class MongoPatientController {

    private static final Logger logger = LoggerFactory.getLogger(MongoPatientController.class);

    @Qualifier("MongoPatientService")
    @Autowired
    MongoPatientService patientsMongoService;

    @Autowired
    ResCode resCode;





    /**
     *
     * searchManyPatient.class 의 설명을 하단에 작성한다.
     *  mgGetManyPatient 대응 서비스
     * parameter : [patientVO, request]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/12/20
     **/
    @PostMapping(value="/api/search/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="patient collection 데이터 조회")
    public String searchFindPatient(@RequestBody PatientVO patientVO, HttpServletRequest request){
        return patientsMongoService.searchFindPatient(patientVO,request);
    }


    /**
     *
     * searchManyPatient.class 의 설명을 하단에 작성한다.
     *  getDocCount 대응 서비스
     * parameter : [patientVO, request]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2021/12/20
     **/
    @PostMapping(value="/api/search/patient-count", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="patient collection 카운트 조회")
    public String countPatient(@RequestBody PatientVO patientVO, HttpServletRequest request){
        return patientsMongoService.countPatient(patientVO,request);
    }



    @PostMapping(value="/api/set/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient collection 등록")
    @ResponseBody
    public String insertPatient(@RequestBody PatientDto patientDto, HttpServletRequest request){
        return patientsMongoService.insertPatient(patientDto,request);
    }

    @PutMapping(value="/api/set/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient collection 수정")
    @ResponseBody
    public String updatePatient(@RequestBody PatientDto patientDto, HttpServletRequest request){
        return patientsMongoService.updatePatient(patientDto,request);
    }

    @DeleteMapping(value="/api/set/patient", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient collection 삭제")
    @ResponseBody
    public String deletePatient(@RequestParam(value = "chart", required = true)
                                    @ApiParam(value = "chart 명", required = true) String chart,
                                HttpServletRequest request){
        return patientsMongoService.deletePatient(chart,request);
    }

}
