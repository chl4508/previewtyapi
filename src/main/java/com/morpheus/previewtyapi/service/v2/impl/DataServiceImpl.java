package com.morpheus.previewtyapi.service.v2.impl;

import com.morpheus.previewtyapi.mapper.DataMapper;
import com.morpheus.previewtyapi.service.v2.DataService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.v2.DataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("v2.DataService")
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    ResCode resCode;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ComUtil comUtil;


    @Override
    public String findDataList(int pageNo, int pageNum, String chart, String record, String type, String name, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(pageNo <= 0 || pageNum <= 0 || pageNum > 200 || comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        DataVO dataVO = new DataVO();
        dataVO.paging(pageNo,pageNum);
        dataVO.setChart(chart);
        dataVO.setRecord(record);
        dataVO.setType(type);
        dataVO.setName(name);
        try {
            resultList = dataMapper.findDataList(dataVO);
            if(resultList.size() == 0){
                return resCode.getResultErrorJson(accessToken,100,resCode.resultMessage(100),request.getRequestURI(), request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        Map<String, Object> pagingInfo = new HashMap<String, Object>();
        pagingInfo.put("pageNo",pageNo);
        pagingInfo.put("pageNum",pageNum);
        pagingInfo.put("pageStart",dataVO.getPageStart());
        pagingInfo.put("pageEnd",dataVO.getPageEnd());

        return resCode.getResultListObjectJson(accessToken, 0, resCode.resultMessage(0), pagingInfo, resultList, request.getRequestURI(), request);
    }

    @Override
    public String findDataListCount(String chart, String record, String type, String name, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type)){
            return resCode.getResultErrorJson(accessToken,101,resCode.resultMessage(101),request.getRequestURI(), request);
        }
        int cnt = 0;
        try {
            DataVO dataVO = new DataVO();
            dataVO.setChart(chart);
            dataVO.setRecord(record);
            dataVO.setType(type);
            dataVO.setName(name);
            cnt = dataMapper.findDataListCount(dataVO);

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
    public String insertData(DataVO dataVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(dataVO.getChart()) || comUtil.empty(dataVO.getRecord()) || comUtil.empty(dataVO.getType())
                || comUtil.empty(dataVO.getName()) || comUtil.empty(dataVO.getFormat()) || comUtil.empty(dataVO.getFile_id())
                || comUtil.empty(dataVO.getUp_id()) || comUtil.empty(dataVO.getIn_id())){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            int dataCnt = dataMapper.findDataListCount(dataVO);
            if(dataCnt != 0){
                return resCode.getResultErrorJson(accessToken,105, resCode.resultMessage(105),request.getRequestURI(),request);
            }

            int dataRes = 0;
            dataRes = dataMapper.insertData(dataVO);
            if(dataRes == 0){
                resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (DuplicateKeyException e) {
            return resCode.getResultErrorJson(accessToken,105, resCode.resultMessage(105),request.getRequestURI(),request);
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String updateData(DataVO dataVO, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(dataVO.getChart()) || comUtil.empty(dataVO.getRecord()) || comUtil.empty(dataVO.getType())
                || comUtil.empty(dataVO.getName()) || comUtil.empty(dataVO.getUp_id())){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            int dataCnt = dataMapper.findDataListCount(dataVO);
            if(dataCnt == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

            int dataRes= 0;
            dataRes = dataMapper.updateData(dataVO);
            if(dataRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }

    @Override
    public String deleteData(String chart, String record, String type, String name, String up_id, HttpServletRequest request) {
        String accessToken = request.getHeader("access_token");
        if(comUtil.empty(chart) || comUtil.empty(record) || comUtil.empty(type)
                || comUtil.empty(name) || comUtil.empty(up_id)){
            return resCode.getResultErrorJson(accessToken,101, resCode.resultMessage(101),request.getRequestURI(),request);
        }

        try {
            DataVO dataVO = new DataVO();
            dataVO.setChart(chart);
            dataVO.setRecord(record);
            dataVO.setType(type);
            dataVO.setName(name);
            dataVO.setUp_id(up_id);

            int dataCnt = dataMapper.findDataListCount(dataVO);
            if(dataCnt == 0){
                return resCode.getResultErrorJson(accessToken,100, resCode.resultMessage(100),request.getRequestURI(),request);
            }

            int dataRes= 0;
            dataRes = dataMapper.deleteData(dataVO);
            if(dataRes == 0){
                return resCode.getResultErrorJson(accessToken,104, resCode.resultMessage(104),request.getRequestURI(),request);
            }
        } catch (Exception e) {
            return resCode.getResultErrorJson(accessToken,102, resCode.resultMessage(102),request.getRequestURI(),request);
        }
        return resCode.getResultJson(accessToken,0,resCode.resultMessage(0),request.getRequestURI(), request);
    }
}
