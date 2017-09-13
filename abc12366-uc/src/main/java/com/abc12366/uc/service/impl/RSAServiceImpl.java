package com.abc12366.uc.service.impl;

import com.abc12366.uc.service.RSAService;
import com.abc12366.uc.wsbssoa.utils.RSA;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-11
 * Time: 16:55
 */
@Service
public class RSAServiceImpl implements RSAService {

    @Override
    public String decode(String str) {
        try {
            RSAPrivateKey privateKey = RSA.getDefaultPrivateKey();
            byte[] bytes = new BASE64Decoder().decodeBuffer(str);
            String base64 = new String(bytes);
            String[] test = base64.split(" ");
            String json = "";
            for (String s : test) {
                json += RSA.decryptString(privateKey, s);
            }
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String decodeStringFromJs(String str) {
        try {
            byte[] bytes = new BASE64Decoder().decodeBuffer(str);
            String base64 = new String(bytes);
            String[] test = base64.split(" ");
            String json = "";
            for (String s : test) {
                json += RSA.decryptStringByJs(s);
            }
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
