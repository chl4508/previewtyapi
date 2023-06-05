package com.morpheus.previewtyapi.service.v2.impl;

import com.morpheus.previewtyapi.mapper.RecordMapper;
import com.morpheus.previewtyapi.service.v2.RecordService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("v2.RecordService")
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;


    @Override
    public String findRecordList(int pageNo, int pageNum, String chart,String record, String type, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(pageNo <= 0 || pageNum <= 0 || pageNum > 200 || comUtil.empty(chart)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        RecordVO recordVO = new RecordVO();
        recordVO.paging(pageNo,pageNum);
        recordVO.setChart(chart);
        recordVO.setRecord(record);
        recordVO.setType(type);
        try {
            resultList = recordMapper.findRecordList(recordVO);
            if(resultList.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> pagingInfo = new HashMap<String, Object>();
        pagingInfo.put("pageNo",pageNo);
        pagingInfo.put("pageNum",pageNum);
        pagingInfo.put("pageStart",recordVO.getPageStart());
        pagingInfo.put("pageEnd",recordVO.getPageEnd());

        return resCode.getResultListObjectJson(accessToken, 0, resCode.resultMessage(0), pagingInfo, resultList, request.getRequestURI(), request);
    }

    @Override
    public String findRecordListCount(String chart, String record, String type, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        int cnt = 0;
        try {
            RecordVO recordVO = new RecordVO();
            recordVO.setChart(chart);
            recordVO.setRecord(record);
            recordVO.setType(type);
            cnt = recordMapper.findRecordListCount(recordVO);

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
    public String insertRecord(RecordVO recordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(recordVO.getChart()) || comUtil.empty(recordVO.getRecord()) || comUtil.empty(recordVO.getType())
                 || comUtil.empty(recordVO.getUp_id()) || comUtil.empty(recordVO.getIn_id())){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            int recordCount = recordMapper.findRecordListCount(recordVO);
            if(recordCount != 0){
                return resCode.getResultErrorJson(accessToken,105, resCode.resultMessage(105),request.getRequestURI(),request);
            }
            int recordRes = 0;
            recordRes = recordMapper.insertRecord(recordVO);
            if(recordRes == 0){
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
    public String updateRecord(RecordVO recordVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(recordVO.getChart()) || comUtil.empty(recordVO.getRecord()) || comUtil.empty(recordVO.getType()) || comUtil.empty(recordVO.getUp_id())){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {

            int recordCount = recordMapper.findRecordListCount(recordVO);
            if(recordCount == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

            int recordRes= 0;
            recordRes = recordMapper.updateRecord(recordVO);
            if(recordRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteRecord(String chart, String record, String type, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type)
                || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            RecordVO recordVO = new RecordVO();
            recordVO.setChart(chart);
            recordVO.setRecord(record);
            recordVO.setType(type);
            recordVO.setUp_id(up_id);

            int recordCount = recordMapper.findRecordListCount(recordVO);
            if(recordCount == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

            int patientRes= 0;
            patientRes = recordMapper.deleteRecord(recordVO);
            if(patientRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String findRecordPk(String chart, String record, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart)){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }
        String pk = "";
        try {
            RecordVO recordVO = new RecordVO();
            recordVO.setChart(chart);
            recordVO.setRecord(record);
            if(comUtil.empty(record)){
                pk = recordMapper.findRecordInfoRPk(recordVO);
            }else{
                pk = recordMapper.findRecordInfoTPk(recordVO);
            }

            if(comUtil.empty(pk)){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }

        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("pk",pk);

        return resCode.getResultMapJson(accessToken, 0, resCode.resultMessage(0), resultMap, request.getRequestURI(), request);
    }
}
