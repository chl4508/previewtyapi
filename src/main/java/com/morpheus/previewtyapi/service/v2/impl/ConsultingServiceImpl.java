package com.morpheus.previewtyapi.service.v2.impl;

import com.morpheus.previewtyapi.mapper.ConsultingMapper;
import com.morpheus.previewtyapi.service.v2.ConsultingService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.ConsultingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("v2.ConsultingService")
public class ConsultingServiceImpl implements ConsultingService {

    @Autowired
    private ConsultingMapper consultingMapper;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;

    @Override
    public String findConsultingList(int pageNo, int pageNum, String consulting_record, String in_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(consulting_record)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        if(pageNo <= 0 || pageNum <= 0 || pageNum > 200){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        ConsultingVO consultingVO = new ConsultingVO();
        consultingVO.paging(pageNo,pageNum);
        consultingVO.setConsulting_record(consulting_record);
        consultingVO.setIn_id(in_id);
        try {
            resultList = consultingMapper.findConsultingList(consultingVO);
            if(resultList.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> pagingInfo = new HashMap<String, Object>();
        pagingInfo.put("pageNo",pageNo);
        pagingInfo.put("pageNum",pageNum);
        pagingInfo.put("pageStart",consultingVO.getPageStart());
        pagingInfo.put("pageEnd",consultingVO.getPageEnd());

        return resCode.getResultListObjectJson(accessToken, 0, resCode.resultMessage(0), pagingInfo, resultList, request.getRequestURI(), request);
    }

    @Override
    public String findConsultingCount(String consulting_record, String in_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(consulting_record)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        int cnt = 0;

        try {
            ConsultingVO consultingVO = new ConsultingVO();
            consultingVO.setConsulting_record(consulting_record);
            consultingVO.setIn_id(in_id);

            cnt = consultingMapper.findConsultingCount(consultingVO);

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
    public String insertConsulting(ConsultingVO consultingVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(consultingVO.getConsulting_record()) || comUtil.empty(consultingVO.getIn_id()) || comUtil.empty(consultingVO.getUp_id()) ){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        int consultingCnt = 0;
        try {

            consultingCnt = consultingMapper.insertConsulting(consultingVO);
            if(consultingCnt == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

        } catch (DuplicateKeyException e) {
            return resCode.getResultErrorJson(accessToken,105, resCode.resultMessage(105),request.getRequestURI(),request);
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("consulting_id",consultingVO.getConsulting_id());
        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }

    @Override
    public String updateConsulting(ConsultingVO consultingVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(consultingVO.getConsulting_id() == 0){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }


        try {
            int consultingIdCount = consultingMapper.findConsultingIdCount(consultingVO);
            if(consultingIdCount == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }
            int consultingRes = 0;
            consultingRes = consultingMapper.updateConsulting(consultingVO);
            if(consultingRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }

        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteConsulting(int consulting_id, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(consulting_id == 0 || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }

        try {
            ConsultingVO consultingVO = new ConsultingVO();
            consultingVO.setConsulting_id(consulting_id);
            consultingVO.setUp_id(up_id);
            int consultingIdCount = consultingMapper.findConsultingIdCount(consultingVO);
            if(consultingIdCount == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }
            int consultingRes = 0;
            consultingRes = consultingMapper.deleteConsulting(consultingVO);
            if(consultingRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }

        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }

        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
