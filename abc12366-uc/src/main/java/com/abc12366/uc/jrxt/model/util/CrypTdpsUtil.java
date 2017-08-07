package com.abc12366.uc.jrxt.model.util;


import com.google.code.springcryptoutils.core.cipher.symmetric.Cipherer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Security;

/**
 * 加密解密工具类
 *
 * @author ziben
 * @version 1.0 11-3-29 上午11:36
 */
@Component
public class CrypTdpsUtil implements InitializingBean {
    @Autowired
    private Cipherer symmetricEncrypter;

    @Autowired
    private Cipherer symmetricDecrypter;

    @Autowired
    private com.google.code.springcryptoutils.core.cipher.asymmetric.Base64EncodedCipherer b64AsymmetricEncrypter;

    @Autowired
    private com.google.code.springcryptoutils.core.cipher.asymmetric.Base64EncodedCipherer b64AsymmetricDecrypter;

    private static byte[] iv = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};

    public void afterPropertiesSet() {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 此函数使用用户选择的加密算法对文件进行加密，目前为“DES”算法
     *
     * @param key         用户提供的加密算法的密钥,即申报密码,长度固定为8
     * @param inFileName  需要进行加密的文件名（全路径）
     * @param outFileName 指定的加密后输出文件（全路径）
     * @return 如果成功返回0；如果文件不能打开返回1；如果提供的密钥的长度不合法返回2；如果提供的密钥内容不合法返回3；如果选择的算法不合法返回4
     */
    public int encryptFile(String key, String inFileName, String outFileName) {
        //todo:等待总局标准实现
        return -1;
    }

    /**
     * 此函数对指定文件使用指定的加密算法进行解密，目前为“DES”算法
     *
     * @param key         用户提供的加密算法的密钥,即申报密码
     * @param inFileName  需要进行加密的文件名（全路径）
     * @param outFileName 指定的加密后输出文件（全路径）
     * @return 如果成功返回0；如果文件不能打开返回1；如果提供的密钥的长度不合法返回2；如果提供的密钥内容不合法返回3；如果选择的算法不合法返回4
     */
    public int decryptFile(String key, String inFileName, String outFileName) {
        //todo:等待总局标准实现

        return -1;
    }

    /**
     * 利用RSA算法对字符串进行加密
     *
     * @param message 需要加密的信息
     * @return 加密后的消息，base64格式
     */
    public String b64AsymmetricEncrypt(String message) {
        return this.b64AsymmetricEncrypter.encrypt(message);
    }

    /**
     * 利用RSA算法解密字符串
     *
     * @param message 经过加密了的信息，base64格式
     * @return 解密后的字符串
     */
    public String b64AsymmetricDecrypt(String message) {
        return this.b64AsymmetricDecrypter.encrypt(message);
    }

    public byte[] symmetricEncrypt(byte[] key, byte[] message) {
        return this.symmetricEncrypter.encrypt(key, iv, message);
    }

    public byte[] symmetricDecrypt(byte[] key, byte[] message) {
        return this.symmetricDecrypter.encrypt(key, iv, message);
    }


    /**
     * 对传入的字节流进行加密
     *
     * @param byteContent
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] encryptContent(byte[] byteContent, byte[] key) throws Exception {
        return this.symmetricEncrypt(key, byteContent);
    }

    public byte[] decryptContent(String password, String crypt, byte[] contentBytes) {
        byte[] key = password.getBytes();
        return this.symmetricDecrypt(key, contentBytes);
    }
}
