package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.util.ResCode;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController{



    @RequestMapping(value="/error", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String url = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String token = request.getHeader("access_token");
        ResCode resCode = new ResCode();
        return resCode.getResultErrorUrlJson(token, statusCode, httpStatus.getReasonPhrase().toString(),new Date(), url, request);
    }

}
