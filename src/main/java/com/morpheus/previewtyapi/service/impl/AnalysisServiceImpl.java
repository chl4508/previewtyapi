package com.morpheus.previewtyapi.service.impl;

import com.morpheus.previewtyapi.dto.*;
import com.morpheus.previewtyapi.mapper.AdminLoginMapper;
import com.morpheus.previewtyapi.mapper.AnalysisMapper;
import com.morpheus.previewtyapi.service.AdminLoginService;
import com.morpheus.previewtyapi.service.AnalysisService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.AnalysisVO;
import com.morpheus.previewtyapi.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Service("AnalysisService")
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ResCode resCode;

    @Autowired
    ComUtil comUtil;

    private static final Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);


    @Override
    public String setFacialWidth(FacialWidthDto facialWidthDto, String accessToken, HttpServletRequest request){
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(facialWidthDto.getUserId());
        analysisVO.setAnalysisRecord(facialWidthDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_facial_width");

        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            facialWidthDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateFacialWidth(facialWidthDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertFacialWidth(facialWidthDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }

    }

    @Override
    public String setFacialHeightBalance(FacialHeightBalanceDto facialHeightBalanceDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(facialHeightBalanceDto.getUserId());
        analysisVO.setAnalysisRecord(facialHeightBalanceDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_facial_height_balance");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            facialHeightBalanceDto.setAnalysisId(resultMap.get("analysisId").toString());

            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateFacialHeightBalance(facialHeightBalanceDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertFacialHeightBalance(facialHeightBalanceDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String setEyesLength(EyesLengthDto eyesLengthDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(eyesLengthDto.getUserId());
        analysisVO.setAnalysisRecord(eyesLengthDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_eyes_length");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            eyesLengthDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateEyesLength(eyesLengthDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertEyesLength(eyesLengthDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String setEyeWidthBalance(EyeWidthBalanceDto eyeWidthBalanceDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(eyeWidthBalanceDto.getUserId());
        analysisVO.setAnalysisRecord(eyeWidthBalanceDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_eye_width_balance");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            eyeWidthBalanceDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateEyeWidthBalance(eyeWidthBalanceDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertEyeWidthBalance(eyeWidthBalanceDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String setNoseLipsLength(NoseLipsLengthDto noseLipsLengthDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(noseLipsLengthDto.getUserId());
        analysisVO.setAnalysisRecord(noseLipsLengthDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_nose_lips_length");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            noseLipsLengthDto.setAnalysisId(resultMap.get("analysisId").toString());

            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateNoseLipsLength(noseLipsLengthDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertNoseLipsLength(noseLipsLengthDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }


    @Override
    public String setNoseLipsAngle(NoseLipsAngleDto noseLipsAngleDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(noseLipsAngleDto.getUserId());
        analysisVO.setAnalysisRecord(noseLipsAngleDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_nose_lips_angle");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            noseLipsAngleDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateNoseLipsAngle(noseLipsAngleDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertNoseLipsAngle(noseLipsAngleDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String setAsymmetryByVolume(AsymmetryByVolumeDto asymmetryByVolumeDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(asymmetryByVolumeDto.getUserId());
        analysisVO.setAnalysisRecord(asymmetryByVolumeDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_asymmetry_by_volume");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            asymmetryByVolumeDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateAsymmetryByVolume(asymmetryByVolumeDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertAsymmetryByVolume(asymmetryByVolumeDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String setCurvedLength(CurvedLengthDto curvedLengthDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(curvedLengthDto.getUserId());
        analysisVO.setAnalysisRecord(curvedLengthDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_curved_length");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            curvedLengthDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateCurvedLength(curvedLengthDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertCurvedLength(curvedLengthDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String setApProjection(ApProjectionDto apProjectionDto, String accessToken, HttpServletRequest request) {
        AnalysisVO analysisVO = new AnalysisVO();
        analysisVO.setUserId(apProjectionDto.getUserId());
        analysisVO.setAnalysisRecord(apProjectionDto.getAnalysisRecord());
        analysisVO.setTableName("analysis_ap_projection");
        try {
            Map<String, Object> resultMap = analysisMapper.selectAnalysisId(analysisVO);
            if(Integer.parseInt(resultMap.get("columCnt").toString()) == 0){
                return resCode.getResultErrorJson(accessToken, 100, resCode.resultMessage(100), request.getRequestURI(), request);
            }
            apProjectionDto.setAnalysisId(resultMap.get("analysisId").toString());
            int cnt = analysisMapper.selectAnalysisCount(analysisVO);
            int res = -1;
            if(cnt>0){
                //update
                res = analysisMapper.updateApProjection(apProjectionDto);
                if(res==0){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }else{
                //insert
                res = analysisMapper.insertApProjection(apProjectionDto);
                if(res!=1){
                    return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
                }
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String insertUserRecord(UserRecordDto userRecordDto, String accessToken, HttpServletRequest request) {
        try {
            //insert
            int res = analysisMapper.insertUserRecord(userRecordDto);
            if(res!=1){
                return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }

    @Override
    public String updateUserRecord(UserRecordDto userRecordDto, String accessToken, HttpServletRequest request) {
        try {
            //update
            int res = analysisMapper.updateUserRecord(userRecordDto);
            if(res==0){
                return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
            }
            return resCode.getResultJson(accessToken,0, resCode.resultMessage(0),request.getRequestURI(),request);
        } catch (Exception e) {
            //e.printStackTrace();
            return resCode.getResultErrorJson(accessToken, 103, resCode.resultMessage(103), request.getRequestURI(), request);
        }
    }
}
