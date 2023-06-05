package com.morpheus.previewtyapi.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RecordVO {

    private boolean and = false;
    private String chart = "";
    private String record = "";
    private String typeNe = "";
    private String type = "";
    private boolean sort = false;
    private String sortKey = "";
    private String sortValue = "";
    private String dataFileId = "";
    private Map<String,Object> description = new HashMap<>();
}
