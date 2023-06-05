package com.morpheus.previewtyapi.service.v2;

import com.morpheus.previewtyapi.vo.v2.PatientVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface MongoPatientService {

    String findPatientList(int limit, int skip, String sort, HttpServletRequest request);

    String findPatient(String chart, HttpServletRequest request);

    String findPatientListCount(HttpServletRequest request);

    String updatePatient(PatientVO patientVO, HttpServletRequest request);

    String insertPatient(PatientVO patientVO, HttpServletRequest request);

    String deletePatient(String chart, String up_id, HttpServletRequest request);

}
