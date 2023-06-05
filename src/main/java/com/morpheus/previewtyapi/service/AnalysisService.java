package com.morpheus.previewtyapi.service;

import com.morpheus.previewtyapi.dto.*;
import com.morpheus.previewtyapi.vo.AnalysisVO;
import com.morpheus.previewtyapi.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AnalysisService {

   String setFacialWidth(FacialWidthDto facialWidthDto, String accessToken, HttpServletRequest request);
   String setFacialHeightBalance(FacialHeightBalanceDto facialHeightBalanceDto, String accessToken, HttpServletRequest request);

   String setEyesLength(EyesLengthDto eyesLengthDto, String accessToken, HttpServletRequest request);
   String setEyeWidthBalance(EyeWidthBalanceDto eyeWidthBalanceDto, String accessToken, HttpServletRequest request);

   String setNoseLipsLength(NoseLipsLengthDto noseLipsLengthDto,String accessToken, HttpServletRequest request);
   String setNoseLipsAngle(NoseLipsAngleDto noseLipsAngleDto, String accessToken, HttpServletRequest request);

   String setAsymmetryByVolume(AsymmetryByVolumeDto asymmetryByVolumeDto,String accessToken, HttpServletRequest request);

   String setCurvedLength(CurvedLengthDto curvedLengthDto,String accessToken, HttpServletRequest request);

   String setApProjection(ApProjectionDto apProjectionDto,String accessToken, HttpServletRequest request);

   String insertUserRecord(UserRecordDto userRecordDto,String accessToken, HttpServletRequest request);
   String updateUserRecord(UserRecordDto userRecordDto,String accessToken, HttpServletRequest request);



}
