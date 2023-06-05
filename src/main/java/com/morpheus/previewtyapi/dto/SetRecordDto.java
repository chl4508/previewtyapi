package com.morpheus.previewtyapi.dto;

import lombok.Data;

@Data
public class SetRecordDto {

    private String chart = "";
    private String record = "";
    private String type = "";
    private String searchChart = "";
    private String searchRecord = "";
    private String searchType = "";
    private String description = "";
    private String study = "";
    private String[] data;
    private String[] volume;
    private String[] bone;
    private String[] previewty;

}
