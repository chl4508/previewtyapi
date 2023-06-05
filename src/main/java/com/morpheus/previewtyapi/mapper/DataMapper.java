package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.vo.v2.DataVO;
import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DataMapper {


    List<Map<String,Object>> findDataList(DataVO dataVO);

    int findDataListCount(DataVO dataVO);

    int insertData(DataVO dataVO);

    int updateData(DataVO dataVO);

    int deleteData(DataVO dataVO);


}

