package com.morpheus.previewtyapi.vo;

import lombok.Data;

@Data
public class UserVO {
	
	//private static final long serialVersionUID = -4086869747130410600L;
    private int columCnt;
	private String userLoginId;
	private String rn;
    private String userId;
    private String userId2;

    private String inDt;
    private String upDt;
    private String inNm;
    private String upNm;
    private String inId;
    private String upId;


    private String userNm;

    private String userPw;
    private String changePw;
    
    private String newPw;
    private String confirmPw;
    
    private String userGroup;
    private String userGroupNm;
    
    private String email;
    private String phone;
    private String countryNumber;
    
    private String userToken;
    private String gender;
    private String birth;
    private String language;
    
    private String aboutMe;
    
    private String approved;
    private String profileImg;
    private String imgUrl;

    private String customerCnt;
    
    
    //컨시어지
    private String userIdC;
    private String userNmC;
    private String emailC;
    private String phoneC;
    private String imgUrlC;
    

    //매칭개수
    private String monthMatch;
    private String monthTotalMatch;
    
    
    //평점
    private String averageScore;
    
    
    //매칭 유저
    private String mcUserId;
    
    //해쉬테그
    private String hashTagId;
    private String hashTagContents; 
    private String uhId;
    private String uhUserId;
    
    
    
    // 팀 
    private String teamId;
    private String teamNm;
    private String teamExplanation;
    
    
    // 조건처리용 그룹    private String 팀
    private String ConditionTeamId;
    private String ConditionUserGroup;
    
    
    
    // 고객 scan 분석 정보 
    private String analysisId;
    private String analysisRecord;
    private String analysisStatus;
    private String analysisAverage;
    
    private String upperFace;
    private String middleFace;
    private String lowerFace;
    private String upperFaceR;
    private String upperFaceL;
    private String middleFaceR;
    private String middleFaceL;
    private String lowerFaceR;
    private String lowerFaceL;
    private String totalFace;
    private String wholeFace;
    private String pupilR;
    private String pupilL;
    private String nasion;
    private String pronasale;
    private String subnasale;
    private String upperLipPt;
    private String lowerLipPt;
    private String pogonion;
    private String cheilionR;
    private String cheilionL;
    private String zygomaticPtR;
    private String zygomaticPtL;
    private String pfWidthR;
    private String pfWidthL;
    private String icWidth;
    private String pfHeightR;
    private String pfHeightL;
    private String ueHeightR;
    private String ueHeightL;
    private String nasalBridgeLength;
    private String nasalHeight;
    private String eLineToUpperLip;
    private String eLineToLowerLip;
    private String nasofrontal;
    private String nasolabial;
    private String labiomental;
    private String tExR;
    private String tAiR;
    private String tChR;
    private String tGnR;
    private String tExL;
    private String tAiL;
    private String tChL;
    private String tGnL;
    
    private String upperFaceAv;
    private String middleFaceAv;
    private String lowerFaceAv;
    private String upperFaceRAv;
    private String upperFaceLAv;
    private String middleFaceRAv;
    private String middleFaceLAv;
    private String lowerFaceRAv;
    private String lowerFaceLAv;
    private String totalFaceAv;
    private String wholeFaceAv;
    private String pupilRAv;
    private String pupilLAv;
    private String nasionAv;
    private String pronasaleAv;
    private String subnasaleAv;
    private String upperLipPtAv;
    private String lowerLipPtAv;
    private String pogonionAv;
    private String cheilionRAv;
    private String cheilionLAv;
    private String zygomaticPtRAv;
    private String zygomaticPtLAv;
    private String pfWidthRAv;
    private String pfWidthLAv;
    private String icWidthAv;
    private String pfHeightRAv;
    private String pfHeightLAv;
    private String ueHeightRAv;
    private String ueHeightLAv;
    private String nasalBridgeLengthAv;
    private String nasalHeightAv;
    private String eLineToUpperLipAv;
    private String eLineToLowerLipAv;
    private String nasofrontalAv;
    private String nasolabialAv;
    private String labiomentalAv;
    private String tExRAv;
    private String tAiRAv;
    private String tChRAv;
    private String tGnRAv;
    private String tExLAv;
    private String tAiLAv;
    private String tChLAv;
    private String tGnLAv;
    
    private String analysisTemp;
    
    

    
    
}