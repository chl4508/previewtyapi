package com.morpheus.previewtyapi.vo.v2;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RecordVO extends CommonVO{

   private String chart;
   private String record;
   private String type;
   private String description;
   private String in_id;
   private String up_id;
}
