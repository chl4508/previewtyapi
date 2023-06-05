package com.morpheus.previewtyapi.vo;

import lombok.Data;

@Data
public class AuthVO {

    private int idx;
    private String secretKey;
    private String accessToken;
    private String previewtyInfo;
    private String idDt;
    private String upDt;
    private int authCnt;
}
