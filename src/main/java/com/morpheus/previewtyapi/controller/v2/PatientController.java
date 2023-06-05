package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.PatientService;
import com.morpheus.previewtyapi.vo.v2.PatientVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.PatientController")
public class PatientController {

    @Qualifier("v2.PatientService")
    @Autowired
    PatientService patientService;

    /**
     *
     * findPatientList.class 의 설명을 하단에 작성한다.
     * patient 데이터 리스트 조회
     * parameter : [request, pageNo, pageNum, chart]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/20
     **/
    @GetMapping(value="/v2/api/search/patient-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 리스트 조회")
    @ResponseBody
    public String findPatientList(HttpServletRequest request,
                                  @RequestParam(value = "pageNo", required=true) @ApiParam(value = "보여줄 페이지 값", required = true) int pageNo,
                                  @RequestParam(value = "pageNum", required=true) @ApiParam(value = "페이지당 보여줄 갯수", required = true) int pageNum,
                                  @RequestParam(value = "matching_con_id", required=true) @ApiParam(value = "matching userId", required = true) String matching_con_id,
                                  @RequestParam(value = "content", required=false) @ApiParam(value = "특정 user_nm, phone 조회 명", required = false) String content,
                                  @RequestParam(value = "chart", required=false) @ApiParam(value = "chart 명", required = false) String chart){
        return patientService.findPatientList(pageNo,pageNum,matching_con_id,content,chart,request);
    }

    /**
     *
     * findPatientList.class 의 설명을 하단에 작성한다.
     * patient 데이터 리스트 카운트 조회
     * parameter : [request, chart]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/20
     **/
    @GetMapping(value="/v2/api/search/patient-count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 리스트 카운트 조회")
    @ResponseBody
    public String findPatientListCount(HttpServletRequest request,
                                @RequestParam(value = "matching_con_id", required=true) @ApiParam(value = "matching userId", required = true) String matching_con_id,
                                @RequestParam(value = "content", required=false) @ApiParam(value = "특정 user_nm, phone 조회 명", required = false) String content,
                                @RequestParam(value = "chart", required=false) @ApiParam(value = "chart 명", required = false) String chart){
        return patientService.findPatientListCount(matching_con_id,content,chart,request);
    }

    /**
     *
     * insertPatient.class 의 설명을 하단에 작성한다.
     * patient 데이터 등록
     * parameter : [request, patientVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/20
     **/
    @PostMapping(value="/v2/api/set/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 등록")
    @ResponseBody
    public String insertPatient(HttpServletRequest request,
                                @RequestBody @ApiParam(value = "Patient 객체", required = false) PatientVO patientVO){
        return patientService.insertPatient(patientVO, request);
    }

    /**
     *
     * updatePatient.class 의 설명을 하단에 작성한다.
     * patient 데이터 수정
     * parameter : [request, patientVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/20
     **/
    @PutMapping(value="/v2/api/set/patient", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 수정")
    @ResponseBody
    public String updatePatient(HttpServletRequest request,
                                @RequestBody @ApiParam(value = "Patient 객체", required = false) PatientVO patientVO){
        return patientService.updatePatient(patientVO, request);
    }

    /**
     *
     * deletePatient.class 의 설명을 하단에 작성한다.
     * patient 데이터 삭제
     * parameter : [request, chart, matching_user_id, up_id]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/20
     **/
    @DeleteMapping(value="/v2/api/set/patient", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="patient 데이터 삭제")
    @ResponseBody
    public String deletePatient(HttpServletRequest request,
                                @RequestParam(value = "chart", required=true) @ApiParam(value = "chart 명", required = true) String chart,
                                @RequestParam(value = "matching_user_id", required=true) @ApiParam(value = "matching_user_id 명", required = true) String matching_user_id,
                                @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id){
        return patientService.deletePatient(chart, matching_user_id, up_id, request);
    }

}
