package com.morpheus.previewtyapi.vo.v2;

import lombok.Data;

@Data
public class ConsultingVO extends CommonVO{
    private int consulting_id;
    private String consulting_record;
    private String consulting_title;
    private String consulting_contents;
    private String in_id;
    private String up_id;
}
