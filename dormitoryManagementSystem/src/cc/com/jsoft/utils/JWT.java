package cc.com.jsoft.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import cc.com.jsoft.pojo.JwtSecret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//@Configuration
//@PropertySource("classpath:env.properties")
public class JWT {
	
	@Autowired
	private JwtSecret jwtSecret;
	
//	@Value("${JWT_SECRET}")
//	public String secret;
	
	
	/**
     *    ����jwt
     * @param id
     * @param subject
     * @param ttlMillis ���ڵ�ʱ�䳤��
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //ָ��ǩ����ʱ��ʹ�õ�ǩ���㷨��Ҳ����header�ǲ��֣�jjwt�Ѿ����ⲿ�����ݷ�װ���ˡ�
        long nowMillis = System.currentTimeMillis();//����JWT��ʱ��
        Date now = new Date(nowMillis);
        Map<String,Object> claims = new HashMap<String,Object>();//����payload��˽�������������ض���ҵ����Ҫ��ӣ����Ҫ���������֤��һ������Ҫ��jwt�Ľ��շ���ǰ��ͨ����֤��ʽ�ģ�
        claims.put("uid", "DSSFAWDWADAS...");
        claims.put("user_name", "admin");
        claims.put("nick_name","DASDA121");
        SecretKey key = generalKey();//����ǩ����ʱ��ʹ�õ���Կsecret,����������ط�װ�˵ģ�һ����Դӱ��������ļ��ж�ȡ���м������Կ������¶Ŷ�������������˵�˽Կ�����κγ�������Ӧ����¶��ȥ��һ���ͻ��˵�֪���secret, �Ǿ���ζ�ſͻ����ǿ�������ǩ��jwt�ˡ�
        //���������Ϊpayload��Ӹ��ֱ�׼������˽��������
        JwtBuilder builder = Jwts.builder() //������ʵ����newһ��JwtBuilder������jwt��body
                .setClaims(claims)          //�����˽��������һ��Ҫ����������Լ�������˽�е�����������Ǹ�builder��claim��ֵ��һ��д�ڱ�׼��������ֵ֮�󣬾��Ǹ�������Щ��׼��������
                .setId(id)                  //����jti(JWT ID)����JWT��Ψһ��ʶ������ҵ����Ҫ�������������Ϊһ�����ظ���ֵ����Ҫ������Ϊһ����token,�Ӷ��ر��طŹ�����
                .setIssuedAt(now)           //iat: jwt��ǩ��ʱ��
                .setSubject(subject)        //sub(Subject)���������JWT�����壬�����������ˣ������һ��json��ʽ���ַ��������Դ��ʲôuserid��roldid֮��ģ���Ϊʲô�û���Ψһ��־��
                .signWith(signatureAlgorithm, key);//����ǩ��ʹ�õ�ǩ���㷨��ǩ��ʹ�õ���Կ
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);     //���ù���ʱ��
        }
        return builder.compact();           //�Ϳ�ʼѹ��Ϊxxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx������jwt
        //��ӡ��һ����ȷʵ��������������
        //eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJ1c2VyX25hbWUiOiJhZG1pbiIsIm5pY2tfbmFtZSI6IkRBU0RBMTIxIiwiZXhwIjoxNTE3ODI4MDE4LCJpYXQiOjE1MTc4Mjc5NTgsImp0aSI6Imp3dCJ9.xjIvBbdPbEMBMurmwW6IzBkS3MPwicbqQa2Y5hjHSyo
    }
    
    /**
     * ����jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();  //ǩ����Կ�������ɵ�ǩ������Կһģһ��
        Claims claims = Jwts.parser()  //�õ�DefaultJwtParser
           .setSigningKey(key)         //����ǩ������Կ
           .parseClaimsJws(jwt).getBody();//������Ҫ������jwt
        return claims;
    }
    
    /**
     * ���ַ������ɼ���key
     * @return
     */
    public static SecretKey generalKey(){
    	
      String stringKey = "7786df7fc3a34e26a61c034d5ec8245d";//���������ļ��м��ܵ�����7786df7fc3a34e26a61c034d5ec8245d
//        String stringKey = jwtSecret.getSecret();
        byte[] encodedKey = Base64.decodeBase64(stringKey);//���ص��������[B@152f6e2
        System.out.println(encodedKey);//[B@152f6e2
        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));//7786df7fc3a34e26a61c034d5ec8245d
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");// ���ݸ������ֽ�����ʹ��AES�����㷨����һ����Կ��ʹ�� encodedKey�е�ʼ���Ұ��� 0 ��ǰ leng ���ֽ����ǵ�Ȼ�����С�����������������ϻ��Ƴ�����Java���ܺͽ��ܵ�һЩ�㷨��
        return key;
    }

    
//	private static final String SECRET = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";
//	
//	private static final String EXP = "exp";
//	
//	private static final String PAYLOAD = "payload";
	
	// ���ܣ�����һ�������
//	public static <T> String sign (T object, long maxAge) {
//	try {
//		 final JWTSigner signer = new JWTSigner(SECRET);
//		 final Map<String, Object> claims = new HashMap<String, Object>();
//         ObjectMapper mapper = new ObjectMapper();
//         String jsonString = mapper.writeValueAsString(object);
//         claims.put(PAYLOAD, jsonString);
//         claims.put(EXP, System.currentTimeMillis() + maxAge);
//         return signer.sign(claims);
//     } catch(Exception e) {
//         return null;
//     }
//	}
	
	//���ܣ�����һ�����ܺ��token�ַ����ͽ��ܺ������
//    public static<T> T unsign(String jwt, Class<T> classT) {
//        final JWTVerifier verifier = new JWTVerifier(SECRET);
//        try {
//            final Map<String,Object> claims= verifier.verify(jwt);
//            if (claims.containsKey(EXP) && claims.containsKey(PAYLOAD)) {
//                long exp = (Long)claims.get(EXP);
//                long currentTimeMillis = System.currentTimeMillis();
//                if (exp > currentTimeMillis) {
//                    String json = (String)claims.get(PAYLOAD);
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    return objectMapper.readValue(json, classT);
//                }
//            }
//            return null;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
