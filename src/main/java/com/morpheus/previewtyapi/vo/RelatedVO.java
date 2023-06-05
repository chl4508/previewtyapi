package com.morpheus.previewtyapi.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RelatedVO {

    private String collectionName = "";
    private String match1 = "";
    private String project = "";
    private String chart = "";
    private String record = "";
    private String type = "";
    private String dataElemMatchName = "";
    private Map<String,Object> dataElemMatchData = new HashMap<>();
    private Map<String,Object> setData = new HashMap<>();

}
