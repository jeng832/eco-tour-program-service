package com.kakaopay.ecotour.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CodeUtil {
	public static String makeMd5Code(String str) {
		String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(str.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
		}catch(NoSuchAlgorithmException e){ 
			MD5 = null; 
		}
		return MD5;
	}
}
