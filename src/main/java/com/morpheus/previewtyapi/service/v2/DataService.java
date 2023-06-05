package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.DataVO;
import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface DataService {


    String findDataList(int pageNo, int pageNum, String chart,String record, String type, String name, HttpServletRequest request);

    String findDataListCount(String chart,String record, String type, String name, HttpServletRequest request);

    String insertData(DataVO dataVO, HttpServletRequest request);

    String updateData(DataVO dataVO, HttpServletRequest request);

    String deleteData(String chart,String record, String type, String name, String up_id, HttpServletRequest request);
}
