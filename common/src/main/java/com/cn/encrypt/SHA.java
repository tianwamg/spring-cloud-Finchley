package com.cn.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

public class SHA {
    /**
     * sha1生成加密
     * @return
     */
    public static String generatorSign(String text){
        return DigestUtils.sha1Hex(text);
    }
}
