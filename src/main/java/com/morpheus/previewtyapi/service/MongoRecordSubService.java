package com.morpheus.previewtyapi.service;

import com.morpheus.previewtyapi.dto.SetRecordDto;
import com.morpheus.previewtyapi.dto.SetSubRecordDto;
import com.morpheus.previewtyapi.vo.AggregateRecordVO;
import com.morpheus.previewtyapi.vo.RecordVO;
import com.morpheus.previewtyapi.vo.SubRecordVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoRecordSubService {


    String searchAggregateRecordSub(SubRecordVO subRecordVO, HttpServletRequest request);

    String updateRecordSub(SetSubRecordDto setSubRecordDto, HttpServletRequest request);

    String deleteRecordSub(SetSubRecordDto setSubRecordDto, HttpServletRequest request);



}
