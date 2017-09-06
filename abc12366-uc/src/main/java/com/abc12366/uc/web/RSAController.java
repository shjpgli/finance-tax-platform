package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.RSAResponse;
import com.abc12366.uc.wsbssoa.utils.RSA;
import com.abc12366.uc.wsbssoa.utils.RSAUtil;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-05
 * Time: 19:44
 */
@RestController
@RequestMapping(path = "/rsa", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class RSAController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAController.class);

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


//            mav.addObject("format",publicKey.getFormat());
//            mav.addObject("algorithm",publicKey.getAlgorithm());
//            mav.addObject("modulus",new String(Hex.encodeHex(publicKey.getModulus().toByteArray())));
//            mav.addObject("exponent",new String(Hex.encodeHex(publicKey.getPublicExponent().toByteArray())));

            //KeyPair keyPair = RSAUtil.generateKeyPair();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(rsaResponse);
    }
}
