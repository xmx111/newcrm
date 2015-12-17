package com.ufo.core.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

/** 
* 类名称：RandomUtils 
* 类描述： 随机数据工具类
* 
* 创建人：Duzj
* 创建时间：2012-7-24 下午3:58:53 
* @version 
* 
*/
public class RandomUtils {
    private static long seq = 0l;
    private static final int lenght = 4;
    private static final int max = 9999;
    
    /**
     * 生成唯一的序列数
     * @return
     */
    public static synchronized String randomNumber() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        seq++;
        String no = String.valueOf(seq);
        String next = StringUtils.leftPad(no, lenght, "0");
        seq = (seq >= max) ? 0 : seq;
        return simpleDateFormat.format(new Date()) + next;
    }

    /** 
     * 生成指定位数的随机数字
    * @param length
    * @return
    */
    public static String randomNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String generateSeriesData() {
        SecureRandom random = new SecureRandom();
        byte[] newSeries = new byte[PersistentTokenBasedRememberMeServices.DEFAULT_SERIES_LENGTH];
        random.nextBytes(newSeries);
        return new String(Base64.encode(newSeries));
    }

    public static String generateTokenData() {
        SecureRandom random = new SecureRandom();
        byte[] newToken = new byte[PersistentTokenBasedRememberMeServices.DEFAULT_TOKEN_LENGTH];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }
}
