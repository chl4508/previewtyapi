package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.util.encrypt.EncryptParam;
import com.morpheus.previewtyapi.util.encrypt.EncryptionException;
import com.morpheus.previewtyapi.util.encrypt.MD5HashEncryptor;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Controller
@ApiIgnore
public class MainController{


//    @ApiOperation(value="api 생성 테스트 등록", notes="api 생성 테스트 등록")
//    @GetMapping(value="/api/create/test", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String encrypteKey(String test, HttpServletRequest request) throws EncryptionException {
//        // QjAxQUQ4NzE5RkFERTgyNEJDQ0MwRUVDNThGMEY0NzU=
//
//        MD5HashEncryptor hashEncryptor = new MD5HashEncryptor();
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String key = dateFormat.format(date);
//        key += "ch14508/morphues";
//        EncryptParam param = new EncryptParam();
//        param.setTargetString(key);
//
//        //base64 encoding
//        String text = hashEncryptor.encrypt(param);
//        byte[] targetBytes = text.getBytes();
//        Base64.Encoder encoder = Base64.getEncoder();
//        byte[] encodedBytes = encoder.encode(targetBytes);
//
//        //base65 deconding
//        //Base64.Decoder decoder = Base64.getDecoder();
//        //byte[] decodedBytes = decoder.decode(encodedBytes);
//
//
//        return new String(encodedBytes);
//    }
}
