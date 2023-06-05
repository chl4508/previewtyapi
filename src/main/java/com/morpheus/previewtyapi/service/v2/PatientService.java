package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.PatientVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface PatientService {

    String findPatientList(int pageNo, int pageNum, String matching_con_id, String content,String chart, HttpServletRequest request);

    String findPatientListCount(String matching_con_id, String content, String chart, HttpServletRequest request);

    String insertPatient(PatientVO patientVO, HttpServletRequest request);

    String updatePatient(PatientVO patientVO, HttpServletRequest request);

    String deletePatient(String chart,String matching_user_id, String up_id, HttpServletRequest request);
}
