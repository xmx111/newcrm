package com.ufo.core.utils;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.CharUtils;

public class StringUtils extends org.apache.commons.lang.StringUtils {

    /**
     * 合法手机号验证
     * 
     * @param mobile
     * @return
     */
    public static boolean isMoblile(String mobile) {
        String mobileString = "^0{0,1}(12[0-9]|13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$";
        return mobile.matches(mobileString);
    }

    /** 
     * 判断字符串编码
    * @param str
    * @param encode
    * @return
    */
    public static boolean isEncoding(String str, String encode) {
        try {
            encode = isBlank(encode) ? "utf-8" : encode;
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return true;
            }
        } catch (Exception exception) {
        }
        return false;
    }

    /**
     * DBC to SBC 
     * space SBC 12288 = DBC 32
     * other SBC(33-126) + 65248 = DBC(65281-65374)
     * @param input
     * @return
     */
    public static String toSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**  
     * SBC to DBC
     * @param input  
     * @return  
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 中文转拼音首字符  
     * @param input
     * @return
     */
    public static String pinYinIndex(String input) {
        if (isBlank(input)) {
            return EMPTY;
        } else {
            StringBuffer buf = new StringBuffer();
            for (char c : trimToEmpty(input).toLowerCase().toCharArray()) {
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(c);
                if (!ArrayUtils.isEmpty(strs)) {
                    buf.append(strs[0].toLowerCase().charAt(0));
                } else if (CharUtils.isAsciiAlphanumeric(c)) {
                    buf.append(c);
                }
            }
            return buf.toString();
        }
    }

    /**
    * 转换成拼音
    * @param str
    * @return
    */
    public static String toPinYin(String str) {
        if (isBlank(str)) {
            return EMPTY;
        } else {
            StringBuffer buf = new StringBuffer();
            for (char c : trimToEmpty(str).toLowerCase().toCharArray()) {
                String[] strs = PinyinHelper.toHanyuPinyinStringArray(c);
                if (!ArrayUtils.isEmpty(strs)) {
                    String item = strs[0];
                    String val = item.toUpperCase().substring(0, item.length() - 1);
                    buf.append(val);
                } else if (CharUtils.isAsciiAlphanumeric(c)) {
                    buf.append(String.valueOf(c).toUpperCase());
                }
            }
            return buf.toString();
        }
    }

    /**
     * 截断字符
     * @param input
     * @param length
     * @return
     */
    public static String truncate(String input, final Integer length) {
        Integer len = NumberUtils.isEmpty(length) ? 5 : length;
        return null != input && input.length() > len ? input.substring(0, len) : input;
    }

    /**
     * 字符转整型
     * @param str
     * @return
     */
    public static List<Integer> splitToInteger(String str) {
        return splitToInteger(str, ",");
    }

    /**
     * 字符转整型
     * @param str
     * @return
     */
    public static List<Integer> splitToInteger(String str, String separtor) {
        List<Integer> values = new ArrayList<Integer>();
        if (isEmpty(str)) {
            return values;
        }
        for (String value : split(str, separtor)) {
            values.add(Integer.valueOf(value));
        }
        return values;
    }

    /**
     * 数字补零
     * @param num
     * @param size
     * @return
     */
    public static String addZero(int num, int size){
        String str = String.valueOf(num);
        while (str.length() < size){
            str = "0" + str;
        }
        return str;
    }
}
