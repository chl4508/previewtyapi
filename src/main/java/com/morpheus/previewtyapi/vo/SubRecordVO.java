package com.morpheus.previewtyapi.vo;

import lombok.Data;

@Data
public class SubRecordVO {
    private String selectName = "";
    private String match1 = "";
    private String unwind = "";
    private String match2 = "";
    private String group = "";
    private boolean count = false;
    private boolean sort = false;
    private String sortKey = "";
    private String sortValue = "";
}
