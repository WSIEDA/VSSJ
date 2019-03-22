package cc.com.jsoft.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import sun.misc.BASE64Encoder;


/**
 * 单利模式（保证类的对象在内存中只存在一个）
 * 1，定义私有的构造函数
 * 2，自己创建一个类对象
 * 3，对外提供一个公共的方法
 * @author wangshuai
 *
 */

public class TokenUtil {
	
	private static final TokenUtil instance = new TokenUtil();
	
	/**
	 * 返回类对象
	 * @return
	 */
	public static TokenUtil getInstance () {
		return instance;
	}
	
	/**
	 * 生成Token
	 * @return
	 */
	public String makeToken() {
		String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
		BASE64Encoder encoder = null;
		byte md5[] = null;
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			 md5 = md.digest(token.getBytes());
			//base64编码--任意二进制编码明文字符   adfsdfsdfsf
	        encoder = new BASE64Encoder();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return encoder.encode(md5);
	}
}
