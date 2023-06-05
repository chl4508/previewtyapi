package com.morpheus.previewtyapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "CurvedLengthDto 생성정보")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CurvedLengthDto {

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tExR;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tAiR;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tChR;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tGnR;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tExL;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tAiL;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tChL;

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float tGnL;

    @ApiModelProperty(value = "String 형태의 userId 값 입력", example = "userId", required = true)
    @NotBlank
    private String userId;
    @ApiModelProperty(value = "String 형태의 analysisRecord 값 입력", example = "analysisRecord", required = true)
    @NotBlank
    private String analysisRecord;
    @ApiModelProperty(value = "String 형태의 등록일자 값 입력", example = "20210512153611", required = true)
    @NotBlank
    private String inDt;
    @ApiModelProperty(value = "String 형태의 등록자 값 입력", example = "inId", required = true)
    @NotBlank
    private String inId;
    @ApiModelProperty(value = "String 형태의 수정일자 값 입력", example = "20210512153611", required = true)
    @NotBlank
    private String upDt;
    @ApiModelProperty(value = "String 형태의 수정자 값 입력", example = "upId", required = true)
    @NotBlank
    private String upId;

    private String analysisId;
}

