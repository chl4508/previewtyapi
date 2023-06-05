package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.ConsultingVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface ConsultingService {


    String findConsultingList(int pageNo, int pageNum, String consulting_record, String in_id, HttpServletRequest request);

    String findConsultingCount(String consulting_record, String in_id, HttpServletRequest request);

    String insertConsulting(ConsultingVO consultingVO, HttpServletRequest request);

    String updateConsulting(ConsultingVO consultingVO, HttpServletRequest request);

    String deleteConsulting(int consulting_id, String up_id, HttpServletRequest request);


}
