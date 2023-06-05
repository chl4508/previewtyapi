package com.morpheus.previewtyapi.service;

import com.morpheus.previewtyapi.vo.UserVO;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public interface AdminLoginService {


    String selectAdminSignin(String userLoginId, String userPw, String accessToken,HttpServletRequest request);

    String selectAdminUserInfo(String userLoginId, String accessToken,HttpServletRequest request);

    String selectUserInfo(String userId, String accessToken,HttpServletRequest request);

}
