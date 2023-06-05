package com.morpheus.previewtyapi.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AggregateRecordVO {

    private Map<String, Object> match = new HashMap<>();
    private Map<String, Object> sort = new HashMap<>();

}
