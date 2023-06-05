package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.vo.AuthVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {


    AuthVO selectAuthInfo(String accessToken);

    int selectAuthInfoCnt(String accessToken);

}

