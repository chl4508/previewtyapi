package com.morpheus.previewtyapi.config;

import com.morpheus.previewtyapi.service.AdminLoginService;
import com.morpheus.previewtyapi.service.AuthService;
import com.morpheus.previewtyapi.service.impl.AuthServiceImpl;
import com.morpheus.previewtyapi.util.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    AuthService authService;

    @Autowired
    ResCode resCode;

    @Value("${spring.profiles.active:}")
    private String activeProfile;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURI();
        if(url.equals("/favicon.ico")){
            return true;
        }


        // swagger ui 호출시 나오는 특정 url
        if(!activeProfile.equals("real")) {
            if (url.equals("/") || url.equals("/csrf")) {
                response.sendRedirect(request.getContextPath() + "/swagger-ui.html");
                return false;
            }
        }


        String accessToken = request.getHeader("access_token");

        if (accessToken == null) {
            throw new AccessException(url, accessToken, 112, new Throwable());
        }

        try {
            boolean authBoolean = authService.checkAuthInfo(accessToken, request);

            if(!authBoolean){
                //throw new AccessException(url, accessToken, 114, new Throwable());
                throw new Exception();
            }
        } catch (Exception e) {
            //LOG.error("[JwtInterceptor] JwtException Throw");
            throw new AccessException(url, accessToken, 114, new Throwable());
            //throw e;
        }
        return true;
    }

}