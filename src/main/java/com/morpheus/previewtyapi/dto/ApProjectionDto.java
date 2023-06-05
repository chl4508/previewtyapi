package com.morpheus.previewtyapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "ApProjectionDto 생성정보")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApProjectionDto {

    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float pupilR;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float pupilL;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float nasion;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float pronasale;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float subnasale;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float upperLipPt;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float lowerLipPt;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float pogonion;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float cheilionR;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float cheilionL;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float zygomaticPtR;
    @ApiModelProperty(value = "Float 형태의 number 값 입력", example = "3.14", required = true)
    private float zygomaticPtL;

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

