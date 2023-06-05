package com.morpheus.previewtyapi.controller;

import com.morpheus.previewtyapi.dto.AdminInfoDto;
import com.morpheus.previewtyapi.dto.FacialWidthDto;
import com.morpheus.previewtyapi.service.AdminLoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class AdminLoginController {

	private static final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

	@Autowired
	private AdminLoginService adminLoginService;
//	@ApiResponses({
//			@ApiResponse(code = 100, message = "Result Not Found (결과 없음)"),
//			@ApiResponse(code = 101, message = "Argument Error (파라미터오류)"),
//			@ApiResponse(code = 102, message = "Internal Server Error (서버 오류)"),
//			@ApiResponse(code = 103, message = "Invalid Query (기타 입력 범위 오류)"),
//			@ApiResponse(code = 108, message = "Core Server Result Error (Avatar Ai 서버내부 실패)"),
//			@ApiResponse(code = 109, message = "Core Server Internal Error (Avatar Ai 서버 접속 실패)"),
//			@ApiResponse(code = 111, message = "You do not have access rights (승인대기)"),
//			@ApiResponse(code = 112, message = "Rejection of approval (승인거절)"),
//			@ApiResponse(code = 113, message = "Login Fail (로그인정보없음 or 비밀번호 틀림)"),
//			@ApiResponse(code = 114, message = "Invalid SecretKey (잘못된 인증키)"),
//			@ApiResponse(code = 0, message = "성공")
//	})

	/**
	 *
	 * selectAdminSignin.class
	 * previewty admin login 정보를 가져온다
	 * parameter : [userVO]
	 * returnType : java.lang.String
	 * @author 최연식
	 * @version 1.0.0
	 * 작성일 2021/08/25
	 **/
	@ApiOperation(value="어드민 로그인")
	@PostMapping(value = "/api/admin/signin", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	@ResponseBody
	public String selectAdminSignin(
			@Valid @RequestBody @ApiParam(value = "어드민 로그인 객체", required = true) AdminInfoDto adminInfoDto,
			HttpServletRequest request) {
		String accessToken = request.getHeader("access_token");
		return adminLoginService.selectAdminSignin(adminInfoDto.getUserLoginId(), adminInfoDto.getUserPw(),accessToken, request);
	}


	@GetMapping(value = "/api/search/admin/user-info", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value="어드민의 유저 조회")
	@ResponseBody
	public String adminUserInfo(
			@RequestParam(value = "userLoginId", required = true)
			@ApiParam(value = "로그인 아이디", required = true) String userLoginId,
			HttpServletRequest request){
		String accessToken = request.getHeader("access_token");
		return adminLoginService.selectAdminUserInfo(userLoginId, accessToken, request);
	}

	@GetMapping(value = "/api/search/user-info", produces = APPLICATION_JSON_VALUE)
	@ApiOperation(value="유저 조회")
	@ResponseBody
	public String userInfo(
			@RequestParam(value = "userId", required = true)
			@ApiParam(value = "유저 아이디", required = true) String userId,
			HttpServletRequest request){
		String accessToken = request.getHeader("access_token");
		return adminLoginService.selectUserInfo(userId, accessToken, request);
	}

}



