package net.kang.util;

import java.security.MessageDigest;

public class Encryption { // MongoDB에서 비밀번호를 MD5로 암호화하여 저장을 하였기에 비밀번호를 받아와서 암호화를 하여 비교를 할 수 있도록 작성함
	public static final String SHA256="SHA-256";
	public static final String MD5="MD5";
	public static String encrypt(String s, String messageDigest) {
		try {
			MessageDigest md=MessageDigest.getInstance(messageDigest);
			byte[] passBytes=s.getBytes();
			md.reset();
			byte[] digested=md.digest(passBytes);
			StringBuffer sb=new StringBuffer();
			for(int k=0;k<digested.length;k++)
				sb.append(Integer.toHexString(0xff & digested[k]));
			return sb.toString();
		}catch(Exception e) {
			return s;
		}
	}
}