package com.morpheus.previewtyapi.mapper;

import com.morpheus.previewtyapi.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminLoginMapper {

	UserVO selectAdminSignin(UserVO userVO);

	UserVO selectAdminUserInfo(String userLoginId);

	UserVO selectUserInfo(String userId);

}

