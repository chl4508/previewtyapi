package com.morpheus.previewtyapi.vo.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

@Data
@EqualsAndHashCode(callSuper=false)
public class PatientVO extends CommonVO{

    private String chart;
    private String name;
    private String user_nm;
    private String birth;
    private String gender;
    private String address;
    private String email;
    private String phone;
    private String in_id;
    private String up_id;
    private String comment;
    private String content;
    //private String matching_id;
    private String matching_user_id;
    private String matching_con_id;

}
