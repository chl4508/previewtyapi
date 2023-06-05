package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.vo.v2.RecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecordMapper {


    List<Map<String,Object>> findRecordList(RecordVO recordVO);

    int findRecordListCount(RecordVO recordVO);

    int insertRecord(RecordVO recordVO);

    int updateRecord(RecordVO recordVO);

    int deleteRecord(RecordVO recordVO);

    String findRecordInfoRPk(RecordVO recordVO);

    String findRecordInfoTPk(RecordVO recordVO);


}

