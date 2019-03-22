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
	// ����������ַ���
	private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	//ָ��ɢ���㷨Ϊmd5
    private static String algorithmName = "MD5";
    //ɢ�е�������
    private static final int hashIterations = 1024;
    
    /**
     * ע���ʱ�򣬶�������м��ܣ����ʹ��shiro��֤������ʹ��������ܷ�ʽ��
     * @param userPO
     * @return
     */
    public static UserPO getMD5Password (UserPO userPO) {
    	// stup 1. ������  ��ʱ���������ַ�����
    	userPO.setSalt(System.currentTimeMillis()+randomNumberGenerator.nextBytes().
    	        toHex());
    	// stup 2. ִ�м����㷨
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
	 * ԭʼMD5����
     * MD5���� ����32λmd5��
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
     * ԭʼMD5����
     * ���ܽ����㷨 ִ��һ�μ��ܣ����ν���
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
	 * ���ε�MD5����
	 * @param password
	 * @return
	 */
	public static String getSaltMD5(String password) {  
		// stup 1. ��һ�μ��Σ� �Զ�����������Ϣ
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
	 *  MD5���ν��д���
	 * @param str
	 * @return
	 */
	public static String getSalt (String str) {
		// stup 1. �ڶ��μ���
		String saltSource = "blog";
		// stup 2. ���ܷ�ʽ MD5
		String hashAlgorithmName = "MD5";
		Object salt = new Md5Hash(saltSource);
		// stup 3. ���ܴ���
		int hashIterations = 1024;
		Object result = new SimpleHash(hashAlgorithmName, str, salt, hashIterations);
		return result.toString();
	}
	
	
	/** 
     * ʹ��Apache��Hex��ʵ��Hex(16�����ַ�����)���ֽ�����Ļ�ת 
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
