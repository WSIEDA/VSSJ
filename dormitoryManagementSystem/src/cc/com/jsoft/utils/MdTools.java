package cc.com.jsoft.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import cc.com.jsoft.pojo.PO.UserPO;

public class MdTools {
	// 生成随机的字符串
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	//指定散列算法为md5
    private static String algorithmName = "MD5";
    //散列迭代次数
    private static final int hashIterations = 1024;
    
    /**
     * 注册的时候，对密码进行加密（如果使用shiro认证，必须使用这个加密方式）
     * @param userPO
     * @return
     */
    public static UserPO getMD5Password (UserPO userPO) {
    	// stup 1. 生成盐  （时间戳加随机字符串）
    	userPO.setSalt(System.currentTimeMillis()+randomNumberGenerator.nextBytes().
    	        toHex());
    	// stup 2. 执行加密算法
    	String MD5password = 
    	        new SimpleHash(
    	        		algorithmName,
    	        		userPO.getPassword(),
    	        		ByteSource.Util.bytes(userPO.getPassword()+userPO.getSalt()),
    	        		hashIterations).toHex();
    	userPO.setPassword(MD5password);
    	return userPO;
    }
	
	/***
	 * 原始MD5加密
     * MD5加码 生成32位md5码
     */
    public static String getMD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
 
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
 
    }
 
    /**
     * 原始MD5解密
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){
 
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
 
    }

	/**
	 * 加盐的MD5加密
	 * @param password
	 * @return
	 */
	public static String getSaltMD5(String password) {  
		// stup 1. 第一次加盐， 自定义设置盐信息
        String salt = getSalt("123");
        password = md5Hex(password + salt);  
        char[] cs = new char[48];  
        for (int i = 0; i < 48; i += 3) {  
            cs[i] = password.charAt(i / 3 * 2);  
            char c = salt.charAt(i / 3);  
            cs[i + 1] = c;  
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);  
        }  
        return String.valueOf(cs);  
    }
	
	
	/**
	 *  MD5对盐进行处理
	 * @param str
	 * @return
	 */
	public static String getSalt (String str) {
		// stup 1. 第二次加盐
		String saltSource = "blog";
		// stup 2. 加密方式 MD5
		String hashAlgorithmName = "MD5";
		Object salt = new Md5Hash(saltSource);
		// stup 3. 加密次数
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, str, salt, hashIterations);
		return result.toString();
	}
	
	
	/** 
     * 使用Apache的Hex类实现Hex(16进制字符串和)和字节数组的互转 
     */  
    private static String md5Hex(String str) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            byte[] digest = md.digest(str.getBytes());  
            return new String(new Hex().encode(digest));  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println(e.toString());  
            return "";  
        }  
    } 
}
