package com.morpheus.previewtyapi.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SetSubRecordDto {

    private String chart = "";
    private String record = "";
    private String dataName = "";
    private String type = "";
    private String name = "";
    private String format = "";
    private String fileid = "";
    private String adddate = "";
    private Map<String,Object> setKeyVal = new HashMap<>();

}
