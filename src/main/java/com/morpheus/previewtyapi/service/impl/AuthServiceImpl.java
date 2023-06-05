package com.morpheus.previewtyapi.service.impl;

import com.morpheus.previewtyapi.mapper.AuthMapper;
import com.morpheus.previewtyapi.service.AuthService;
import com.morpheus.previewtyapi.util.ComUtil;
import com.morpheus.previewtyapi.util.ConvertUtil;
import com.morpheus.previewtyapi.util.ResCode;
import com.morpheus.previewtyapi.vo.AuthVO;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("AuthService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    ConvertUtil convertUtil;

    @Autowired
    ResCode resCode;

    @Autowired
    ComUtil comUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);


    @Override
    public boolean checkAuthInfo(String accessToken, HttpServletRequest request) {
        boolean res = false;
        AuthVO authVO = authMapper.selectAuthInfo(accessToken);
        if(authVO.getAuthCnt() == 1){
                res = true;
        }
        return res;
    }

    @Override
    public String makeAuthInfo(HttpServletRequest reequest) {

        // hs256 암호화키
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("ch14508@Previewty");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // header
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        //만료기간 1분 : 1000 * 60
        // 1일 : 1000 * 86400
        // 7일 : 1000 * 604800
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60);

        // payload(내용)
        Map<String, Object> payloads = new HashMap<String, Object>();
        String accessToken = reequest.getParameter("access_token");
        payloads.put("access_token", accessToken);
        payloads.put("expireTime", expireTime);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(payloads)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    @Override
    public boolean checkJwt(String jwt) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("ch14508@Previewty"))
                    .parseClaimsJws(jwt).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰

            logger.info("expireTime :" + claims.getExpiration());
            logger.info("access_token :" + claims.get("access_token"));
            logger.info("expireTime :" + claims.get("expireTime"));

            return true;
        } catch (ExpiredJwtException exception) {
            logger.info("토큰 만료");
            return false;
        } catch (JwtException exception) {
            logger.info("잘못된 토큰");
            return false;
        }
    }
}
