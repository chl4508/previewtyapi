package com.morpheus.previewtyapi.vo.v2;

import lombok.Data;

@Data
public class DataVO extends CommonVO{

    private String chart;
    private String record;
    private String type;
    private String name;
    private String format;
    private String file_id;
    private String in_id;
    private String up_id;

}
