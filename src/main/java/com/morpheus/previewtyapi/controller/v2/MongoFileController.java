package com.morpheus.previewtyapi.controller.v2;

import com.morpheus.previewtyapi.service.v2.MongoFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller("v2.MongoFileController")
public class MongoFileController {

    @Qualifier("v2.MongoFileService")
    @Autowired
    MongoFileService mongoFileService;

    /**
     *
     * uploadFile.class 의 설명을 하단에 작성한다.
     * file 업로드
     * parameter : [request, multipartFile]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/09
     **/
    @PostMapping(value="/v2/api/mongo/upload/file", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="file 업로드")
    public String uploadFile(HttpServletRequest request,
                             @RequestParam(value = "file") MultipartFile multipartFile){

        return mongoFileService.uploadFile(multipartFile,request);
    }


    /**
     *
     * downloadFile.class 의 설명을 하단에 작성한다.
     * file 다운로드
     * parameter : [request, fileId]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/09
     **/
    @GetMapping(value ="/v2/api/mongo/download/file", produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="file 다운로드")
    public String downloadFile(HttpServletRequest request,
                               @RequestParam("fileId")String fileId) {

        return mongoFileService.downloadFile(fileId,request);
    }

    /**
     *
     * deleteFile.class 의 설명을 하단에 작성한다.
     * file 삭제
     * parameter : [request, fileId]
     * returnType : java.lang.String
     * @author 최연식
     * @version 1.0.0
     * 작성일 2022/06/21
     **/
    @DeleteMapping(value="/v2/api/mongo/set/file", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value="file 삭제")
    public String deleteFile(HttpServletRequest request,
                             @RequestParam("fileId")String fileId) {
        return  mongoFileService.deleteFile(fileId,request);
    }
}
