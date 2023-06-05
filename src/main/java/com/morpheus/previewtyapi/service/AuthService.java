package com.morpheus.previewtyapi.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface AuthService {


    boolean checkAuthInfo(String accessToken, HttpServletRequest request);

    String makeAuthInfo(HttpServletRequest reequest);

    boolean checkJwt(String jwt);

}
