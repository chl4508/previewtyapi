package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.dto.*;
import com.morpheus.previewtyapi.service.AnalysisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class AnalysisController {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisController.class);

    @Qualifier("AnalysisService")
    @Autowired
    AnalysisService analysisService;


    @PostMapping(value = "/api/set/facial/width", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="FacialWidth 등록 or 수정")
    @ResponseBody
    public String setFacialWidth(HttpServletRequest request, HttpServletResponse response,
                                 @Valid @RequestBody @ApiParam(value = "FacialWidth 등록 및 수정 객체", required = true) FacialWidthDto facialWidthDto){

        String accessToken = request.getHeader("access_token");
        return analysisService.setFacialWidth(facialWidthDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/facial/height-balance", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="FacialHeight-balance 등록 or 수정")
    @ResponseBody
    public String setFacialHeightBalance(HttpServletRequest request, HttpServletResponse response,
                                         @Valid @RequestBody @ApiParam(value = "FacialHeight-balance 등록 및 수정 객체", required = true) FacialHeightBalanceDto facialHeightBalanceDto){


        String accessToken = request.getHeader("access_token");

        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setFacialHeightBalance(facialHeightBalanceDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/eyes/length", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="FacialHeight-balance 등록 or 수정")
    @ResponseBody
    public String setEyesLength(HttpServletRequest request, HttpServletResponse response,
                                @Valid @RequestBody @ApiParam(value = "EyesLength 등록 및 수정 객체", required = true) EyesLengthDto eyesLengthDto){

        String accessToken = request.getHeader("access_token");

        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setEyesLength(eyesLengthDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/eye/width-balance", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="EyeWidth-balance 등록 or 수정")
    @ResponseBody
    public String setEyeWidthBalance(HttpServletRequest request, HttpServletResponse response,
                                     @Valid @RequestBody @ApiParam(value = "EyeWidth-balance 등록 or 수정 객체", required = true) EyeWidthBalanceDto eyeWidthBalanceDto){

        String accessToken = request.getHeader("access_token");

        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setEyeWidthBalance(eyeWidthBalanceDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/nose/lips-length", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="NoseLips-length 등록 or 수정")
    @ResponseBody
    public String setNoseLipsLength(HttpServletRequest request, HttpServletResponse response,
                                    @Valid @RequestBody @ApiParam(value = "NoseLips-length 등록 or 수정객체", required = true) NoseLipsLengthDto noseLipsLengthDto){

        String accessToken = request.getHeader("access_token");

        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setNoseLipsLength(noseLipsLengthDto, accessToken, request);
    }


    @PostMapping(value = "/api/set/nose/lips-angle", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="NoseLips-angle 등록 or 수정")
    @ResponseBody
    public String setNoseLipsAngle(HttpServletRequest request, HttpServletResponse response,
                                   @Valid @RequestBody @ApiParam(value = "NoseLips-angle 등록 or 수정 객체", required = true) NoseLipsAngleDto noseLipsAngleDto){

        String accessToken = request.getHeader("access_token");
        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setNoseLipsAngle(noseLipsAngleDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/asymmetry-by-volume", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="asymmetry-by-volume 등록 or 수정")
    @ResponseBody
    public String setAsymmetryByVolume(HttpServletRequest request, HttpServletResponse response,
                                       @Valid @RequestBody @ApiParam(value = "asymmetry-by-volume 등록 or 수정 객체", required = true) AsymmetryByVolumeDto asymmetryByVolumeDto){

        String accessToken = request.getHeader("access_token");
        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setAsymmetryByVolume(asymmetryByVolumeDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/curved-length", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="curved-length 등록 or 수정")
    @ResponseBody
    public String setCurvedLength(HttpServletRequest request, HttpServletResponse response,
                                  @Valid @RequestBody @ApiParam(value = "curved-length 등록 or 수정 객체", required = true) CurvedLengthDto curvedLengthDto){
        String accessToken = request.getHeader("access_token");
        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setCurvedLength(curvedLengthDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/ap-projection", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="ap-projection 등록 or 수정")
    @ResponseBody
    public String setApProjection(HttpServletRequest request, HttpServletResponse response,
                                  @Valid @RequestBody @ApiParam(value = "ap-projection 등록 or 수정 객체", required = true) ApProjectionDto apProjectionDto){
        String accessToken = request.getHeader("access_token");
        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.setApProjection(apProjectionDto, accessToken, request);
    }

    @PostMapping(value = "/api/set/user-record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="user-record 등록")
    @ResponseBody
    public String insertUserRecord(HttpServletRequest request, HttpServletResponse response,
                                  @Valid @RequestBody @ApiParam(value = "user-record 등록", required = true) UserRecordDto userRecordDto){
        String accessToken = request.getHeader("access_token");
        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.insertUserRecord(userRecordDto, accessToken, request);
    }

    @PutMapping(value = "/api/set/user-record", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="user-record 수정")
    @ResponseBody
    public String updateUserRecord(HttpServletRequest request, HttpServletResponse response,
                                  @Valid @RequestBody @ApiParam(value = "user-record 수정 객체", required = true) UserRecordDto userRecordDto){
        String accessToken = request.getHeader("access_token");
        //analysisService.setFacialWidth(analysisVO, accessToken, request);
        return analysisService.updateUserRecord(userRecordDto, accessToken, request);
    }


}
