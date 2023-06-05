package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.ConsultingService;
import com.morpheus.previewtyapi.vo.v2.ConsultingVO;
import com.morpheus.previewtyapi.vo.v2.PatientVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.ConsultingController")
public class ConsultingController {

    @Qualifier("v2.ConsultingService")
    @Autowired
    ConsultingService consultingService;

    /**
     *
     * findConsultingList.class 의 설명을 하단에 작성한다.
     * consulting 데이터 리스트 조회
     * parameter : [request, pageNo, pageNum, consulting_record, in_id]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/07/13
     **/
    @GetMapping(value="/v2/api/search/consulting-list", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="Consulting 데이터 리스트 조회")
    @ResponseBody
    public String findConsultingList(HttpServletRequest request,
                                     @RequestParam(value = "pageNo", required=true) @ApiParam(value = "보여줄 페이지 값", required = true) int pageNo,
                                     @RequestParam(value = "pageNum", required=true) @ApiParam(value = "페이지당 보여줄 갯수", required = true) int pageNum,
                                     @RequestParam(value = "consulting_record", required=true) @ApiParam(value = "consulting_record ", required = true) String consulting_record,
                                     @RequestParam(value = "in_id", required=false) @ApiParam(value = "in_id 명", required = false) String in_id) {
        return consultingService.findConsultingList(pageNo, pageNum, consulting_record, in_id, request);
    }

    /**
     *
     * findConsultingCount.class 의 설명을 하단에 작성한다.
     * consulting 데이터 카운트 조회
     * parameter : [request, consulting_record, in_id]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/07/13
     **/
    @GetMapping(value="/v2/api/search/consulting-count", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="Consulting 데이터 카운트 조회")
    @ResponseBody
    public String findConsultingCount(HttpServletRequest request,
                                      @RequestParam(value = "consulting_record", required=true) @ApiParam(value = "consulting_record ", required = true) String consulting_record,
                                      @RequestParam(value = "in_id", required=false) @ApiParam(value = "in_id 명", required = false) String in_id) {
        return consultingService.findConsultingCount(consulting_record, in_id, request);
    }

    /**
     *
     * insertConsulting.class 의 설명을 하단에 작성한다.
     * consulting 데이터 등록
     * parameter : [request, consultingVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/07/13
     **/
    @PostMapping(value="/v2/api/set/consulting", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="Consulting 데이터 등록")
    @ResponseBody
    public String insertConsulting(HttpServletRequest request,
                                @RequestBody @ApiParam(value = "ConsultingVO 객체", required = false) ConsultingVO consultingVO){
        return consultingService.insertConsulting(consultingVO, request);
    }

    /**
     *
     * updateConsulting.class 의 설명을 하단에 작성한다.
     * consulting 데이터 수정
     * parameter : [request, consultingVO]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/07/14
     **/
    @PutMapping(value="/v2/api/set/consulting", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="Consulting 데이터 수정")
    @ResponseBody
    public String updateConsulting(HttpServletRequest request,
                                   @RequestBody @ApiParam(value = "ConsultingVO 객체", required = false) ConsultingVO consultingVO){
        return consultingService.updateConsulting(consultingVO, request);
    }


    /**
     *
     * deleteConsulting.class 의 설명을 하단에 작성한다.
     * Consulting 데이터 삭제
     * parameter : [request, consulting_id, up_id]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/07/14
     **/
    @DeleteMapping(value="/v2/api/set/consulting", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="Consulting 데이터 삭제")
    @ResponseBody
    public String deleteConsulting(HttpServletRequest request,
                                   @RequestParam(value = "consulting_id", required=true) @ApiParam(value = "consulting_id ", required = true) int consulting_id,
                                   @RequestParam(value = "up_id", required=true) @ApiParam(value = "up_id 명", required = true) String up_id) {
        return consultingService.deleteConsulting(consulting_id, up_id, request);
    }





}
