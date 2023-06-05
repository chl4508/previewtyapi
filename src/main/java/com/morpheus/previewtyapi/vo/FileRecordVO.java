package com.morpheus.previewtyapi.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FileRecordVO {
   private String chart = "";
   private String record = "";
   private String type = "";
//   private Map<String,Object> pushData = new HashMap<>();
   private String pushData;
   private String dataFormat;
   private String elemMatchKeys;
   private String elemMatchValues;
}
