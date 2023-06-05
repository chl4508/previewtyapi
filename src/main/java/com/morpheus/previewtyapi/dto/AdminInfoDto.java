package com.morpheus.previewtyapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "AdminInfo 정보")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminInfoDto {


    @ApiModelProperty(value = "String 형태의 userLoginId 값 입력", example = "userLoginId", required = true)
    @NotBlank
    private String userLoginId;
    @ApiModelProperty(value = "String 형태의 userPw 값 입력", example = "userPw", required = true)
    @NotBlank
    private String userPw;

}

