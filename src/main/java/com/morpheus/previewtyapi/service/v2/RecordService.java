package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface RecordService {


    String findRecordList(int pageNo, int pageNum, String chart,String record, String type, HttpServletRequest request);

    String findRecordListCount(String chart,String record, String type, HttpServletRequest request);

    String insertRecord(RecordVO recordVO, HttpServletRequest request);

    String updateRecord(RecordVO recordVO, HttpServletRequest request);

    String deleteRecord(String chart,String record, String type, String up_id, HttpServletRequest request);

    String findRecordPk(String chart, String record, HttpServletRequest request);
}
