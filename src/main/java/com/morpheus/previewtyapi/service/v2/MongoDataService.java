package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.DataVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoDataService {

    String findDataList(String chart, String record, String type, int limit, int skip, HttpServletRequest request);

    String findData(String chart, String record, String type, String name, HttpServletRequest request);

    String insertData(DataVO dataVO, HttpServletRequest request);

    String deleteData(String chart, String record, String type, String name, String up_id, HttpServletRequest request);

}
