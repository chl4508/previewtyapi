package com.morpheus.previewtyapi.util;

import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Cipher;

@Component
public class ComUtil {


	private static SecretKeySpec secretKey;
	private static byte[] key;
	
// ==============================================================
//	 							    sha256
// ==============================================================
	
	
	public static String sha256(String input){
	
		String toReturn = null;
		try {
		    MessageDigest digest = MessageDigest.getInstance("SHA-256");
		    digest.reset();
		    digest.update(input.getBytes("utf8"));
		    toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return toReturn;
	}

	
// ==============================================================
//		 							    랜덤 비밀번호 생성
// ==============================================================

	public static String randomPw(){
		String randomPw = "";
	    int pwdLength = 8;
	    final char[] passwordTable1 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
                                        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                                        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                                        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
                                        'w', 'x', 'y', 'z' };
	    final char[] passwordTable2 = { '!', '@', '#', '$', '%', '^', '&', '*',
							    		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

        Random random = new Random(System.currentTimeMillis());
        int tablelength1 = passwordTable1.length;
        int tablelength2 = passwordTable2.length;
        
        for(int i = 0; i < pwdLength; i++) {
        	if (i % 2 == 0) {
                randomPw += passwordTable1[random.nextInt(tablelength1)];
            } else {
            	randomPw += passwordTable2[random.nextInt(tablelength2)];
            }
        }
        
        return randomPw;

	}
	
// ==============================================================
//		 							    인증번호 생성
// ==============================================================
	
	public static String certification(){
		String number = "";
		int numLength = 6;
		final char[] stringTable = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		
		Random random = new Random(System.currentTimeMillis());
		int tablelength = stringTable.length;
		
		for(int i = 0; i < numLength; i++) {
			number += stringTable[random.nextInt(tablelength)];
		}
		return number;
		
	}

// ==============================================================
//		 							    사용자 IP 확인
// ==============================================================
		

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("X-Real-IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("X-RealIP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getRemoteAddr(); 
		}
		InetAddress ipp = null;
		try {
			ipp = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		String result = "";
		InetAddress macId;

		try {
			macId = InetAddress.getLocalHost();
		   
			NetworkInterface network = NetworkInterface.getByInetAddress(macId);
			byte[] mac = network.getHardwareAddress();
		   
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
				result = sb.toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e){
			e.printStackTrace();
		}
		
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		Date dt = new Date();
		String dateTime = format.format(dt);
		System.out.println("User - "+dateTime+" [" + ipp.getHostName() + "] [" + ipp.getHostAddress() +"] ["+ip+"] ["+result+"]");

	    return ip;
	}
	
	
	
// ==============================================================
//		 							    사용자 인증 E-mail
// ==============================================================
	

	public static String getEmailCertification(String certification) {

		//문의 사항 보내는 email 필요
		String emailAddress = "morpheus@morpheus3d.co.kr";

		//로고 url 변경해야함
		String logoUrl = "https://dev.morpheus3d.co.kr/user/img/logo.png";
		
		String innerHtml = "";
		innerHtml += "<div class='bg-light'>";
		innerHtml += "  <table style='table-layout:fixed;border-collapse:collapse;width:100%;background-color:#f7f7f7'>";
		innerHtml += "    <tbody>";
		innerHtml += "      <tr>";
		innerHtml += "        <td style='padding:0;height:50px'></td>";
		innerHtml += "      </tr>";
		innerHtml += "      <tr>";
		innerHtml += "        <td style='padding:0 14px' align='center'>";
		innerHtml += "          <div style='margin:0 auto;max-width:520px'>";
		innerHtml += "            <table style='margin:0 auto;table-layout:fixed;border-collapse:collapse;max-width:520px;width:100%;background-color:#fff'>";
		innerHtml += "              <tbody><tr>";
		innerHtml += "                <td style='padding:50px 20px 0;text-align:center' align='center'>";
		innerHtml += "                  <img src='"+logoUrl+"' style='display:block;margin:0 auto;width:120px;' width='107' height='' class='CToWUd'>";
		innerHtml += "                  <p style='margin:46px auto 0;max-width:420px;font-size:24px;font-weight:700;line-height:1.42;letter-spacing:-0.1px;color:#222'>Check your Verification Code</p>";
		innerHtml += "                  <p style='margin:20px auto 0;max-width:420px;font-size:14px;line-height:1.71;color:#666;word-break:keep-all;letter-spacing:-0.1px'>Enter the code below to complete the Email verification.</p>";
		innerHtml += "                </td>";
		innerHtml += "              </tr>";
		innerHtml += "              <tr>";
		innerHtml += "                <td style='padding:16px 20px 50px' align='center'>";
		innerHtml += "                   <table style='table-layout:fixed;border-collapse:collapse;width:260px;height:66px;text-align:center'>";
		innerHtml += "                     <tbody>";
		innerHtml += "                       <tr>";
		innerHtml += "                         <td style='margin:0 auto;padding:0 16px;height:66px;border-radius:4px;background-color:#ad1f25;font-size:28px;font-weight:600;letter-spacing:4px;color:#f6f6f6' align='center'>";
		innerHtml +=                              certification;
		innerHtml += "                         </td>";
		innerHtml += "                       </tr>";
		innerHtml += "                     </tbody>";
		innerHtml += "                   </table>";
		innerHtml += "                 </td>";
		innerHtml += "              </tr>";
		innerHtml += "            </tbody></table>";
		innerHtml += "          </div>";
		innerHtml += "        </td>";
		innerHtml += "      </tr>";
		innerHtml += "      <tr>";
		innerHtml += "        <td style='padding:0 14px'>";
		innerHtml += "          <p style='margin:32px 0 0;font-size:12px;line-height:1.64;;color:#999;text-align:center;letter-spacing:-0.1px'>Please do not reply to this email.</p>";
		innerHtml += "          <p style='margin:6px 0 0;font-size:12px;color:#c1c1c1;text-align:center;'>"+emailAddress+"</p>";
		innerHtml += "          <p style='margin:6px 0 0;font-size:12px;color:#c1c1c1;text-align:center;'>© Morpheus3d.</p>";
		innerHtml += "        </td>";
		innerHtml += "      </tr>";
		innerHtml += "      <tr>";
		innerHtml += "        <td style='padding:0;height:50px'></td>";
		innerHtml += "      </tr>";
		innerHtml += "    </tbody>";
		innerHtml += "  </table>";
		innerHtml += "</div>";

	    return innerHtml;
	}
	

// ==============================================================
//		    사용자 인증 Phone Message
//==============================================================
	
	
	public static String getPhoneCertification(String certification) {

		String messageContent = "";
		
		messageContent += "[Previewty] This is the ID verification number ["
				+ certification
				+ "]. [Do not expose others!]";
		
	    return messageContent;
	}



	/**
	*
	* setKey.class 의 설명을 하단에 작성한다.
	* 암호화키 생성성	* parameter :
	* returnType :
	* @author 최연식
	* @version 1.0.0
	* 작성일 2021/08/25
	**/
	public static void setKey(String myKey)
	{
		MessageDigest sha = null;
		try {
			key = myKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


	/**
	*
	* encrypt.class
	*	AES 256 암호화
	* parameter : strToEncrypt(암호화할 파라미터), secret (암호화키)
	* returnType : 암호화한 파라미터
	* @author 최연식
	* @version 1.0.0
	* 작성일 2021/08/25
	**/
	public static String encrypt(String strToEncrypt, String secret) {
		try
		{
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
		}
		catch (Exception e)
		{
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	/**
	*
	* decrypt.class 의 설명을 하단에 작성한다.
	*	AES 256 복호화
	* parameter : strToDecrypt(복호화할 파라미터), secret (암호화키)
	* returnType :  복호화한 파라미터
	* @author 최연식
	* @version 1.0.0
	* 작성일 2021/08/25
	**/
	public static String decrypt(String strToDecrypt, String secret) {
		try
		{
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		}
		catch (Exception e)
		{
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public static Boolean empty(Object obj) {
		if (obj instanceof String) return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List) return obj == null || ((List) obj).isEmpty();
		else if (obj instanceof Map) return obj == null || ((Map) obj).isEmpty();
		else if (obj instanceof Object[]) return obj == null || Array.getLength(obj) == 0;
		else return obj == null;
	}

}



	

