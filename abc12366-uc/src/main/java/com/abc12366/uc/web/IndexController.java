package com.abc12366.uc.web;

import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.job.DzsbJob;
import com.abc12366.uc.webservice.AcceptClient;
import com.alibaba.fastjson.JSONObject;
import com.google.code.springcryptoutils.core.cipher.asymmetric.Base64EncodedCipherer;
import com.google.code.springcryptoutils.core.cipher.symmetric.Base64EncodedKeyGenerator;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 3:58 PM
 * @since 1.0.0
 */
@RestController
public class IndexController {
	
	@Autowired
	private AcceptClient client;

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    @Qualifier("b64AsymmetricEncrypter")
    private Base64EncodedCipherer encrypter;

    @Autowired
    @Qualifier("b64AsymmetricDecrypter")
    private Base64EncodedCipherer decrypter;
    
    @Autowired
    private Base64EncodedKeyGenerator generator;

    @GetMapping("/")
    public ResponseEntity index() throws Exception {
    	System.out.println("Amazon.com, Inc. is an American electronic commerce company withAmazon.com, Inc. is an American electronic com".getBytes().length);
    	String b64encryptedMessage = encrypter.encrypt("Amazon.com, Inc. is an American electronic commerce company withAmazon.com, Inc. is an American electronic com");
    	
    	System.out.println(b64encryptedMessage);
    	
        String decryptedMessage = decrypter.encrypt(b64encryptedMessage);
    	
        System.out.println(decryptedMessage);
        
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new ClassPathResource("tdps.jks").getInputStream(), "hnabc4000".toCharArray());
        
        Certificate certificate = keyStore.getCertificate("tdps");
        RSAPublicKey publicKey = (RSAPublicKey) certificate.getPublicKey();
        
        RSAPublicKey publickey1 =com.abc12366.uc.wsbssoa.utils.RSA.getRSAPublidKey(
        		new String(Hex.encodeHex(publicKey.getModulus().toByteArray())), new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));
        
        //System.out.println("jks文件中的公钥:" + new String(Base64.getEncoder().encode(publicKey.getEncoded())));
        
        //String rsastr = com.abc12366.uc.wsbssoa.utils.RSA.encryptString(publicKey, "AAAAAAA");
        //System.out.println(decrypter.encrypt(new String(Base64.getEncoder().encode(rsastr.getBytes()))));
        
        /*Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publickey1);  
        byte[] output = cipher.doFinal("!@#$%^&*()！@#￥%……&*（）".getBytes());  */
        //System.out.println(decrypter.encrypt("Wnl1ARnRvPtl2uDNlySdkMJRe/mFMbBkQlGKpVtx/FK9hbWpzKa5GqTaGzypshMYo8BT33v8ya5purc+7g4aJzbBeCkhd4Uqade/wNkL/oM0+zGgnggfDVv5PZ9vbYETFX3DNzaUHFMcWClhk30jWMNvVoPmM/XrX3v/0JIXDWI="));
        
        //KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        //keyStore.load(new ClassPathResource("tdps.jks").getInputStream(), "hnabc4000".toCharArray());
    	
        RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey("tdps", "hnabc4000".toCharArray());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey); 
        byte[] output = cipher.doFinal(Base64.getDecoder().decode("Wnl1ARnRvPtl2uDNlySdkMJRe/mFMbBkQlGKpVtx/FK9hbWpzKa5GqTaGzypshMYo8BT33v8ya5purc+7g4aJzbBeCkhd4Uqade/wNkL/oM0+zGgnggfDVv5PZ9vbYETFX3DNzaUHFMcWClhk30jWMNvVoPmM/XrX3v/0JIXDWI="));
        System.out.println(new String(output));
        
        /*LinkedMultiValueMap<String, String> map=new LinkedMultiValueMap<String, String>();
        map.add("url", "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcc69b5b49f2f232e&redirect_uri=http%3A%2F%2Ftestsys.chabc.net%2Fwx%2Fauthorize%2Fwxbind%2FZDdlMjRmYzQtMTc1YS00YWEzLWEwNzAtYzFmMTM2OTZiNDli&response_type=code&scope=snsapi_base&state=https%3A%2F%2F192.168.1.233%2Fweixin%2Fcallback.html#wechat_redirect");
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application","x-www-form-urlencoded",Charset.forName("utf-8"));
        headers.setContentType(mediaType);
   	    HttpEntity<LinkedMultiValueMap> httpEntity = new HttpEntity<LinkedMultiValueMap>(map,headers);
   	    int readTimeout =30000;
        int connectTimeout=10000;
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(connectTimeout);
        clientHttpRequestFactory.setReadTimeout(readTimeout);
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
        
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://dwz.cn/create.php", HttpMethod.POST, httpEntity, String.class);
        
        JSONObject loginInfoObj=JSONObject.parseObject(responseEntity.getBody());
        
        System.out.println(loginInfoObj.getString("tinyurl"));*/
        return ResponseEntity.ok(null);
    }

    @GetMapping("/test")
    public ResponseEntity test() {
    	 Map<String, String> map = new HashMap<>();
         map.put("serviceid", "GY01");
         map.put("ywid", "NOTIFY_CJXX");
         map.put("lrrq", "2017-11-02 15:26:24");
         map.put("maxcount", "20");
         DzsbJob job=client.processYw(map);
         System.out.println(JSONObject.toJSONString(job));
         return ResponseEntity.ok(Utils.kv("message", "OK"));
    }
}
