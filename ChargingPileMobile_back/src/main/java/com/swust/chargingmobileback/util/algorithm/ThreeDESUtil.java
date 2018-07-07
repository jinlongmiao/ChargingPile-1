package com.swust.chargingmobileback.util.algorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author 73559
 * @Date 2018/7/7 10:58
 * 3DS 加解密算法
 */
public class ThreeDESUtil {
    private static String hexStr = "0123456789ABCDEF";

    /**
     * keybyte为加密密钥，长度为24字节
     */
    private final static byte[] keybyte = "123456788765432112345678".getBytes();
    private static SecretKey deskey = new SecretKeySpec(keybyte, "DESede");

    /**
     * 加密(data.getBytes("utf-8")-->Base64)
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            return Base64.encode(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e) {
            // 加密失败，打日志
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            return new String(cipher.doFinal(Base64.decode(data)), "utf-8");
        } catch (Exception e) {
            // 解密失败，打日志
            e.printStackTrace();
        }
        return null;
    }

}
