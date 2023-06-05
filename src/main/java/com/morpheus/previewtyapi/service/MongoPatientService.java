package com.morpheus.previewtyapi.service;

import com.morpheus.previewtyapi.dto.PatientDto;
import com.morpheus.previewtyapi.vo.PatientVO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public interface MongoPatientService {

    String searchFindPatient(PatientVO patientVO, HttpServletRequest request);

    String countPatient(PatientVO patientVO, HttpServletRequest request);

    String insertPatient(PatientDto patientDto, HttpServletRequest request);

    String updatePatient(PatientDto patientDto, HttpServletRequest request);

    String deletePatient(String chart, HttpServletRequest request);

}
