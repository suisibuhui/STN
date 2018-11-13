package com.example.stn.stn.utils;

import android.util.Base64;

import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Name: EnCode
 * Author: xulong
 * Comment: //TODO
 * Date: 2016-08-22 19:44.
 */
public class EnCode  {

    // 加密密钥
    private static final String code = "asiainfomboss";
    private static final byte[] keyBytes = code.getBytes();

    // 定义加密算法
    private static final String Algorithm = "DESede/ECB/PKCS5Padding";
    // 加密密钥
    private static final String PASSWORD_CRYPT_KEY = "asiainfomboss";


    /**
     * 3DES加密算法
     * @param secretKey
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] tripleDesEncrypt(String secretKey, byte[] data) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, genTripleDesKey(secretKey));
            byte[] encode = cipher.doFinal(data);
            return encode;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }



    /**
     * Base64位编码
     * @param secretKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String tripleDesEncryptBase64(String secretKey, String data) throws Exception {
        if(data == null) return "";
        byte[] encode = tripleDesEncrypt(secretKey, data.getBytes("utf8"));
        return new String(Base64.encode(encode, Base64.DEFAULT),"utf8");
    }

    /**
     * URL编码
     * @param secretKey
     * @param data
     * @return
     * @throws Exception
     */
    public static String tripleDesEncryptUrl(String secretKey, String data) throws Exception {
        String encode = tripleDesEncryptBase64(secretKey, data);
        return URLEncoder.encode(encode,"utf8");
    }

    public static String tripleDesEncryptUrl2(String secretKey, String data) throws Exception {
        String encode = tripleDesEncryptBase64(secretKey, data);
        return encode;
    }


    /**
     * 生成3DES密钥.
     * @param strKey
     * @return
     * @throws Exception
     */
    private static SecretKey genTripleDesKey(String strKey) throws Exception {
        if (strKey == null) {
            return null;
        }
        byte[] keys = new byte[24];
        byte[] srcArr = strKey.getBytes("utf8");
        if(srcArr.length >= 24)
            System.arraycopy(srcArr,0,keys,0, 24);
        else{
            //补满24字节，右补0
            System.arraycopy(srcArr,0,keys,0, srcArr.length);
        }
        return new SecretKeySpec(keys, "DESede");
    }








}
