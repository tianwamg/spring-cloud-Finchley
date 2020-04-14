package com.cn.encrypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Arrays;

/**
 * AES对称加密和解密
 */
public class AES {
    // 算法名称
    final String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    final String algorithmStr = "AES/CBC/PKCS5Padding";
    //
    private SecretKeySpec key;
    private Cipher cipher;
    boolean isInited = false;

    byte[] iv = { 0x30, 0x31, 0x30, 0x32, 0x30, 0x33, 0x30, 0x34, 0x30, 0x35, 0x30, 0x36, 0x30, 0x37, 0x30, 0x38 };
    //    String iv = "1234567890123456";
    public void init(byte[] keyBytes) {

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr,"BC");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 加密方法
     *
     * @param content
     *            要加密的字符串
     * @param keyBytes
     *            加密密钥
     * @return
     */
    public byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }
    /**
     * 解密方法
     *
     * @param encryptedData
     *            要解密的字符串
     * @param keyBytes
     *            解密密钥
     * @return
     */
    public String decrypt(byte[] encryptedData, byte[] keyBytes) throws Exception{
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            byte[] ivBytes = Arrays.copyOfRange(encryptedData, 0, 16);
            encryptedData = Arrays.copyOfRange(encryptedData,16,encryptedData.length);
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new String(encryptedText,"utf-8");
    }

    public String decryptChapter(byte[] Body,byte[] Key) throws Exception{
        String result = null;
        java.security.Key key = new SecretKeySpec(Key, 0, Key.length, "AES");
        // 对编码过的body解码，body包含2部分，分别是前16位的iv和后面的content
        byte[] ivBytes = Arrays.copyOfRange(Body, 0, 16);
        byte[] contentBytes = Arrays.copyOfRange(Body, 16, Body.length);
        // 解密
        IvParameterSpec ips = new IvParameterSpec(ivBytes);
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, key, ips);
        byte[] decryptStr = new byte[0];
        try {
            decryptStr = c.doFinal(contentBytes);
        }catch (BadPaddingException e) {
            throw new BadPaddingException(e.getMessage());
        }
        result = new String(decryptStr,"utf-8");
        return result;
    }
}
