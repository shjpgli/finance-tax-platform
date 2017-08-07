package com.abc12366.uc.jrxt.model.util;


import com.google.code.springcryptoutils.core.cipher.symmetric.Cipherer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Security;

/**
 * ���ܽ��ܹ�����
 *
 * @author ziben
 * @version 1.0 11-3-29 ����11:36
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
     * �˺���ʹ���û�ѡ��ļ����㷨���ļ����м��ܣ�ĿǰΪ��DES���㷨
     *
     * @param key         �û��ṩ�ļ����㷨����Կ,���걨����,���ȹ̶�Ϊ8
     * @param inFileName  ��Ҫ���м��ܵ��ļ�����ȫ·����
     * @param outFileName ָ���ļ��ܺ�����ļ���ȫ·����
     * @return ����ɹ�����0������ļ����ܴ򿪷���1������ṩ����Կ�ĳ��Ȳ��Ϸ�����2������ṩ����Կ���ݲ��Ϸ�����3�����ѡ����㷨���Ϸ�����4
     */
    public int encryptFile(String key, String inFileName, String outFileName) {
        //todo:�ȴ��ֱܾ�׼ʵ��
        return -1;
    }

    /**
     * �˺�����ָ���ļ�ʹ��ָ���ļ����㷨���н��ܣ�ĿǰΪ��DES���㷨
     *
     * @param key         �û��ṩ�ļ����㷨����Կ,���걨����
     * @param inFileName  ��Ҫ���м��ܵ��ļ�����ȫ·����
     * @param outFileName ָ���ļ��ܺ�����ļ���ȫ·����
     * @return ����ɹ�����0������ļ����ܴ򿪷���1������ṩ����Կ�ĳ��Ȳ��Ϸ�����2������ṩ����Կ���ݲ��Ϸ�����3�����ѡ����㷨���Ϸ�����4
     */
    public int decryptFile(String key, String inFileName, String outFileName) {
        //todo:�ȴ��ֱܾ�׼ʵ��

        return -1;
    }

    /**
     * ����RSA�㷨���ַ������м���
     *
     * @param message ��Ҫ���ܵ���Ϣ
     * @return ���ܺ����Ϣ��base64��ʽ
     */
    public String b64AsymmetricEncrypt(String message) {
        return this.b64AsymmetricEncrypter.encrypt(message);
    }

    /**
     * ����RSA�㷨�����ַ���
     *
     * @param message ���������˵���Ϣ��base64��ʽ
     * @return ���ܺ���ַ���
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
     * �Դ�����ֽ������м���
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
