package com.morpheus.previewtyapi.service.v2.impl;

import com.morpheus.previewtyapi.mapper.PatientMapper;
import com.morpheus.previewtyapi.service.v2.PatientService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.PatientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("v2.PatientService")
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;



    @Override
    public String findPatientList(int pageNo, int pageNum, String matching_con_id, String content, String chart, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(matching_con_id)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        if(pageNo <= 0 || pageNum <= 0 || pageNum > 200){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        PatientVO patientVO = new PatientVO();
        patientVO.paging(pageNo,pageNum);
        patientVO.setMatching_con_id(matching_con_id);
        patientVO.setContent(content);
        patientVO.setChart(chart);
        try {
            resultList = patientMapper.findPatientList(patientVO);
            if(resultList.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> pagingInfo = new HashMap<String, Object>();
        pagingInfo.put("pageNo",pageNo);
        pagingInfo.put("pageNum",pageNum);
        pagingInfo.put("pageStart",patientVO.getPageStart());
        pagingInfo.put("pageEnd",patientVO.getPageEnd());

        return resCode.getResultListObjectJson(accessToken, 0, resCode.resultMessage(0), pagingInfo, resultList, request.getRequestURI(), request);
    }

    @Override
    public String findPatientListCount(String matching_con_id, String content, String chart, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(matching_con_id)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        int cnt = 0;
        try {
            PatientVO patientVO = new PatientVO();
            patientVO.setChart(chart);
            patientVO.setMatching_con_id(matching_con_id);
            patientVO.setContent(content);
            cnt = patientMapper.findPatientListCount(patientVO);

            if(cnt == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("count",cnt);
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }

    @Override
    public String insertPatient(PatientVO patientVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(patientVO.getChart()) || comUtil.empty(patientVO.getUser_nm()) || comUtil.empty(patientVO.getBirth())
                || comUtil.empty(patientVO.getGender()) || comUtil.empty(patientVO.getIn_id()) || comUtil.empty(patientVO.getUp_id())
                || comUtil.empty(patientVO.getMatching_user_id()) || comUtil.empty(patientVO.getMatching_con_id())){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            int patientCnt = patientMapper.findPatientCount(patientVO);
            if(patientCnt != 0){
                return resCode.getResultErrorJson(accessToken,105, resCode.resultMessage(105),request.getRequestURI(),request);
            }
            int patientRes = patientMapper.insertPatient(patientVO);
            int MatchingRes = patientMapper.insertMatching(patientVO);
            if(patientRes == 0 || MatchingRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (DuplicateKeyException e) {
            return resCode.getResultErrorJson(accessToken,105, resCode.resultMessage(105),request.getRequestURI(),request);
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String updatePatient(PatientVO patientVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(patientVO.getChart()) || comUtil.empty(patientVO.getUp_id())){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            int patientCnt = patientMapper.findPatientCount(patientVO);
            if(patientCnt == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

            int patientRes= 0;
            patientRes = patientMapper.updatePatient(patientVO);
            if(patientRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deletePatient(String chart, String matching_user_id, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(matching_user_id) || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        try {
            PatientVO patientVO = new PatientVO();
            patientVO.setChart(chart);
            patientVO.setMatching_user_id(matching_user_id);
            patientVO.setUp_id(up_id);

            int patientCnt = patientMapper.findPatientCount(patientVO);
            if(patientCnt == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

            int patientRes,matchingRes= 0;
            patientRes = patientMapper.deletePatient(patientVO);
            matchingRes = patientMapper.deleteMatching(patientVO);

            if(patientRes == 0 || matchingRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
