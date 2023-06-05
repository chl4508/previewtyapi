package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoRecordService {


    String findRecordList(String chart, int limit, int skip, String sort, HttpServletRequest request);

    String findRecord(String chart, String record, String type, HttpServletRequest request);

    String findRecordListCount(String chart, HttpServletRequest request);

    String insertRecord(RecordVO recordVO, HttpServletRequest request);

    String updateRecord(RecordVO recordVO, HttpServletRequest request);

    String deleteRecord(String chart, String record, String type, String up_id, HttpServletRequest request);
}
