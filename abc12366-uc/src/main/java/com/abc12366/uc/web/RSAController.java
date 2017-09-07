package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.LoginBO;
import com.abc12366.uc.model.bo.RSAResponse;
import com.abc12366.uc.wsbssoa.utils.RSA;
import com.abc12366.uc.wsbssoa.utils.RSAUtil;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-05
 * Time: 19:44
 */
@RestController
@RequestMapping(path = "/rsa", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class RSAController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    /**
     * 获取组装PublicKey对象（公钥）所需参数接口
     * @return
     */
    @GetMapping
    public ResponseEntity selectOne() {
        RSAResponse rsaResponse = new RSAResponse();
        try {
            RSAPublicKey publicKey = RSA.getDefaultPublicKey();
            if (publicKey != null) {
                rsaResponse.setCode("2000");
                rsaResponse.setMessage("生成公钥成功");
                rsaResponse.setFormat(publicKey.getFormat());
                rsaResponse.setAlgorithm(publicKey.getAlgorithm());
                rsaResponse.setModulus(new String(Hex.encodeHex(publicKey.getModulus().toByteArray())));
                rsaResponse.setExponent(new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));
            } else {
                rsaResponse.setCode("1000");
                rsaResponse.setMessage("生成公钥失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(rsaResponse);
    }

    @GetMapping(path = "/{str}")
    public ResponseEntity decode(@PathVariable String str) throws Exception {
        RSAPublicKey publicKey = RSA.getDefaultPublicKey();
        RSAPrivateKey privateKey = RSA.getDefaultPrivateKey();

        byte[] bytes = RSA.encrypt(publicKey, str.getBytes());
        String s = new BASE64Encoder().encode(bytes);
        byte[] bytes2 = new BASE64Decoder().decodeBuffer(s);

        String response2 = new String(RSA.decrypt(privateKey, bytes2));
        System.out.println(response2);
        return ResponseEntity.ok(response2);
    }

    public String decode2(String str) throws Exception {
        RSAPublicKey publicKey = RSA.getDefaultPublicKey();
        RSAPrivateKey privateKey = RSA.getDefaultPrivateKey();

        byte[] bytes = RSA.encrypt(publicKey, str.getBytes());
        String s = new BASE64Encoder().encode(bytes);
        byte[] bytes2 = new BASE64Decoder().decodeBuffer(s);

        String response2 = new String(RSA.decrypt(privateKey, bytes2));
        System.out.println(response2);
        return response2;
    }


}
