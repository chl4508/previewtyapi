package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.vo.v2.PatientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PatientMapper {


    List<Map<String,Object>> findPatientList(PatientVO patientVO);

    int findPatientListCount(PatientVO patientVO);

    int findPatientCount(PatientVO patientVO);
    int insertPatient(PatientVO patientVO);
    int insertMatching(PatientVO patientVO);

    int updatePatient(PatientVO patientVO);

    int deletePatient(PatientVO patientVO);
    int deleteMatching(PatientVO patientVO);


}

