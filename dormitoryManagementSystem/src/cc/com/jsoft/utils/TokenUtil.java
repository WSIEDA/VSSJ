package cc.com.jsoft.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;


/**
 * ����ģʽ����֤��Ķ������ڴ���ֻ����һ����
 * 1������˽�еĹ��캯��
 * 2���Լ�����һ�������
 * 3�������ṩһ�������ķ���
 * @author wangshuai
 *
 */

public class TokenUtil {
	
	private static final TokenUtil instance = new TokenUtil();
	
	/**
	 * ���������
	 * @return
	 */
	public static TokenUtil getInstance () {
		return instance;
	}
	
	/**
	 * ����Token
	 * @return
	 */
	public String makeToken() {
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		BASE64Encoder encoder = null;
		byte md5[] = null;
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			 md5 = md.digest(token.getBytes());
			//base64����--��������Ʊ��������ַ�   adfsdfsdfsf
	        encoder = new BASE64Encoder();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return encoder.encode(md5);
	}
}
