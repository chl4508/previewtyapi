package com.morpheus.previewtyapi.service.impl;

import com.morpheus.previewtyapi.mapper.AdminLoginMapper;
import com.morpheus.previewtyapi.service.AdminLoginService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Service("AdminLoginService")
public class AdminLoginServiceImpl implements AdminLoginService {

    @Autowired
    private AdminLoginMapper adminLoginMapper;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ResCode resCode;

    @Autowired
    ComUtil comUtil;

    private static final Logger logger = LoggerFactory.getLogger(AdminLoginServiceImpl.class);


    @Override
    public String selectAdminSignin(String userLoginId, String userPw, String accessToken, HttpServletRequest request) {
        if(userLoginId.isEmpty() || userPw.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        //String decodeUserPw = comUtil.decrypt(userPw, "morpheus3d");

        UserVO userVO = new UserVO();
        userVO.setUserLoginId(userLoginId);
        userVO.setUserPw(comUtil.sha256(userLoginId + userPw));
        UserVO resultVO = adminLoginMapper.selectAdminSignin(userVO);
        String result = "";
        if (resultVO.getColumCnt() != 0) {
            if (resultVO.getApproved().equals("Y")) {
                //로그인성공
                //resultVO.setUserPw("");
                Map<String, Object> resultMap = convertUtil.convertObjectToMap(resultVO);
                result = resCode.getResultMapJson(accessToken,0, resCode.resultMessage(0), resultMap,request.getRequestURI(),request);
            } else if (resultVO.getApproved().equals("N")) {
                //승인 대기
                result = resCode.getResultErrorJson(accessToken,111, resCode.resultMessage(001),request.getRequestURI(),request);
            } else if (resultVO.getApproved().equals("X")) {
                //승인 거절
                result = resCode.getResultErrorJson(accessToken,112, resCode.resultMessage(002),request.getRequestURI(),request);
            } else {
                //비밀번호 틀림
                result = resCode.getResultErrorJson(accessToken,113, resCode.resultMessage(003),request.getRequestURI(),request);
            }
        } else {
            //정보없음
            result = resCode.getResultErrorJson(accessToken,113, resCode.resultMessage(113),request.getRequestURI(),request);
        }
        return result;
    }

    @Override
    public String selectAdminUserInfo(String userLoginId, String accessToken, HttpServletRequest request) {
        if(userLoginId.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        UserVO res = adminLoginMapper.selectAdminUserInfo(userLoginId);
        if(res.getColumCnt() == 0){
            return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
        }
        Map<String, Object> resultMap = convertUtil.convertObjectToMap(res);

        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }

    @Override
    public String selectUserInfo(String userId, String accessToken, HttpServletRequest request) {
        if(userId.isEmpty()){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        UserVO res = adminLoginMapper.selectUserInfo(userId);
        if(res.getColumCnt() == 0){
            return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
        }
        Map<String, Object> resultMap = convertUtil.convertObjectToMap(res);
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }


}
