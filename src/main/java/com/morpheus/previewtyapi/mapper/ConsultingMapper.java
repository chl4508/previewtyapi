package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.vo.v2.ConsultingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ConsultingMapper {

    List<Map<String,Object>> findConsultingList(ConsultingVO consultingVO);

    int findConsultingCount(ConsultingVO consultingVO);

    int insertConsulting(ConsultingVO consultingVO);

    int findConsultingIdCount(ConsultingVO consultingVO);
    int updateConsulting(ConsultingVO consultingVO);
    int deleteConsulting(ConsultingVO consultingVO);




}

