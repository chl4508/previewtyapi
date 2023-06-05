package com.morpheus.previewtyapi.service;

import com.morpheus.previewtyapi.dto.PatientDto;
import com.morpheus.previewtyapi.dto.SetRecordDto;
import com.morpheus.previewtyapi.vo.AggregateRecordVO;
import com.morpheus.previewtyapi.vo.PatientVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoRecordService {


    String searchFindRecord(RecordVO recordVO, HttpServletRequest request);

    String searchFindRecordCount(RecordVO recordVO, HttpServletRequest request);

    String searchAggregateRecord(AggregateRecordVO aggregateRecordVO, HttpServletRequest request);

    String insertRecord(SetRecordDto setRecordDto, HttpServletRequest request);

    String updateRecord(SetRecordDto setRecordDto, HttpServletRequest request);

    String deleteRecord(String chart,String record, String type, HttpServletRequest request);

}
