package com.morpheus.previewtyapi.vo;

import lombok.Data;

@Data
public class AnalysisVO {

//    analysis_facial_width
//    analysis_asymmetry_by_volume
//    analysis_eye_width_balance
//    analysis_facial_height_balance
//    analysis_ap_projection
//    analysis_eyes_length
//    analysis_nose_lips_length
//    analysis_nose_lips_angle
//    analysis_curved_length

    private String userId;
    private String analysisRecord;
    private String tableName;
    private String analysisId;
    private String delYn;
    private String inDt;
    private String inId;
    private String upDt;
    private String upId;

    //analysis_facial_width
    private float upperFace;
    private float middleFace;
    private float lowerFace;

    //analysis_asymmetry_by_volume
    private float upperFaceR;
    private float upperFaceL;
    private float middleFaceR;
    private float middleFaceL;
    private float lowerFaceR;
    private float lowerFaceL;
    private float totalFace;

    //analysis_eye_width_balance
    private String wholeFace;

    //analysis_facial_height_balance
    private String StringLowerFace;

    //analysis_ap_projection
    private float pupilR;
    private float pupilL;
    private float nasion;
    private float pronasale;
    private float subnasale;
    private float upperLipPt;
    private float lowerLipPt;
    private float pogonion;
    private float cheilionR;
    private float cheilionL;
    private float zygomaticPtR;
    private float zygomaticPtL;

    //analysis_eyes_length
    private float pfWidthR;
    private float pfWidthL;
    private float icWidth;
    private float pfHeightR;
    private float pfHeightL;
    private float ueHeightR;
    private float ueHeightL;

    //analysis_nose_lips_length
    private float nasalBridgeLength;
    private float nasalHeight;
    private float eLineToUpperLip;
    private float eLineToLowerLip;

    //analysis_nose_lips_angle
    private float nasofrontal;
    private float nasolabial;
    private float labiometntal;

    //analysis_curved_length
    private float tExR;
    private float tAiR;
    private float tChR;
    private float tGnR;
    private float tExL;
    private float tAiL;
    private float tChL;
    private float tGnL;


}
