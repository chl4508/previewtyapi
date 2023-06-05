package com.morpheus.previewtyapi.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PatientVO {


    private boolean sort = false;
    private String sortKey = "";
    private String sortValue = "";


    private boolean or = false;
    private Map<String,Object> chart = new HashMap<>();
    private Map<String,Object> name = new HashMap<>();

    private String singleChart = "";

}
