package com.ufo.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

@SuppressWarnings("unchecked")
public class NumberUtils {

    /** 
     * 为空判断 
    * @param n
    * @return
    */
    public static boolean isEmpty(Number n) {
        return null == n || 0 == n.doubleValue();
    }

    /** 
     * 不为空判断 
    * @param n
    * @return
    */
    public static boolean isNotEmpty(Number n) {
        return null != n && n.doubleValue() != 0;
    }

    /** 
     * 安全处理,为空是返回零,防止空指针
    * @param n
    * @return
    */
    public static <T extends Number> T safe(T n, Class<T> cls) {
        if (cls.isAssignableFrom(Short.class)) {
            return (T) safe(n, Short.valueOf("0"));
        } else if (cls.isAssignableFrom(Float.class)) {
            return (T) safe(n, Float.valueOf(0));
        } else if (cls.isAssignableFrom(Long.class)) {
            return (T) safe(n, Long.valueOf(0));
        } else if (cls.isAssignableFrom(Double.class)) {
            return (T) safe(n, Double.valueOf(0.0d));
        } else if (cls.isAssignableFrom(BigInteger.class)) {
            return (T) safe(n, BigInteger.ZERO);
        } else if (cls.isAssignableFrom(BigDecimal.class)) {
            return (T) safe(n, BigDecimal.ZERO);
        } else if (cls.isAssignableFrom(Integer.class)) {
            return (T) safe(n, Integer.valueOf(0));
        } else {
            throw new RuntimeException("不支持的数据类型:" + cls.getName());
        }
    }

    /** 
     * 安全处理,为空是返回默认值 ,防止空指针
    * @param n
    * @param def
    * @return
    */
    public static <T extends Number> T safe(T n, T def) {
        return null == n ? def : n;
    }

    /** 
     * safe add 
    * @param n1
    * @param n2
    * @return
    */
    public static <T extends Number> T safeAdd(T n1, T n2, Class<T> cls) {
        n1 = (T) safe(n1, cls);
        n2 = (T) safe(n2, cls);
        if (n1 instanceof BigInteger) {
            return (T) ((BigInteger) n1).add(BigInteger.valueOf(n2.longValue()));
        } else if (n1 instanceof BigDecimal) {
            return (T) ((BigDecimal) n1).add(BigDecimal.valueOf(n2.doubleValue()));
        } else if (n1 instanceof Double) {
            Double d = n1.doubleValue() + n2.doubleValue();
            return (T) d;
        } else if (n1 instanceof Float) {
            Float d = n1.floatValue() + n2.floatValue();
            return (T) d;
        } else if (n1 instanceof Long) {
            Long val = n1.longValue() + n2.longValue();
            return (T) val;
        } else if (n1 instanceof Integer) {
            Integer val = n1.intValue() + n2.intValue();
            return (T) val;
        } else {
            throw new RuntimeException("不支持的数据类型:" + cls.getName());
        }
    }

    /** 
     * safe subtract
    * @param n1
    * @param n2
    * @return
    */
    public static <T extends Number> T safeSubtract(T n1, T n2, Class<T> cls) {
        n1 = (T) safe(n1, cls);
        n2 = (T) safe(n2, cls);
        if (n1 instanceof BigInteger) {
            return (T) ((BigInteger) n1).subtract(BigInteger.valueOf(n2.longValue()));
        } else if (n1 instanceof BigDecimal) {
            return (T) ((BigDecimal) n1).subtract(BigDecimal.valueOf(n2.doubleValue()));
        } else if (n1 instanceof Double) {
            Double d = n1.doubleValue() - n2.doubleValue();
            return (T) d;
        } else if (n1 instanceof Float) {
            Float d = n1.floatValue() - n2.floatValue();
            return (T) d;
        } else if (n1 instanceof Long) {
            Long val = n1.longValue() - n2.longValue();
            return (T) val;
        } else if (n1 instanceof Integer) {
            Integer val = n1.intValue() - n2.intValue();
            return (T) val;
        } else {
            throw new RuntimeException("不支持的数据类型:" + cls.getName());
        }
    }

    /** 
     * safe min
    * @param n1
    * @param n2
    * @return
    */
    public static <T extends Number> T min(T n1, T n2, Class<T> cls) {
        n1 = (T) safe(n1, cls);
        n2 = (T) safe(n2, cls);
        if (n1 instanceof BigInteger) {
            return (T) ((BigInteger) n1).min(BigInteger.valueOf(n2.longValue()));
        } else if (n1 instanceof BigDecimal) {
            return (T) ((BigDecimal) n1).min(BigDecimal.valueOf(n2.doubleValue()));
        } else if (n1 instanceof Double) {
            Double d = Math.min(n1.doubleValue(), n2.doubleValue());
            return (T) d;
        } else if (n1 instanceof Float) {
            Float d = Math.min(n1.floatValue(), n2.floatValue());
            return (T) d;
        } else if (n1 instanceof Long) {
            Long val = Math.min(n1.longValue(), n2.longValue());
            return (T) val;
        } else if (n1 instanceof Integer) {
            Integer val = Math.min(n1.intValue(), n2.intValue());
            return (T) val;
        } else {
            throw new RuntimeException("不支持的数据类型:" + cls.getName());
        }
    }

    /** 
     * safe  max
    * @param n1
    * @param n2
    * @return
    */
    public static <T extends Number> T max(T n1, T n2, Class<T> cls) {
        n1 = (T) safe(n1, cls);
        n2 = (T) safe(n2, cls);
        if (n1 instanceof BigInteger) {
            return (T) ((BigInteger) n1).max(BigInteger.valueOf(n2.longValue()));
        } else if (n1 instanceof BigDecimal) {
            return (T) ((BigDecimal) n1).max(BigDecimal.valueOf(n2.doubleValue()));
        } else if (n1 instanceof Double) {
            Double d = Math.max(n1.doubleValue(), n2.doubleValue());
            return (T) d;
        } else if (n1 instanceof Float) {
            Float d = Math.max(n1.floatValue(), n2.floatValue());
            return (T) d;
        } else if (n1 instanceof Long) {
            Long val = Math.max(n1.longValue(), n2.longValue());
            return (T) val;
        } else if (n1 instanceof Integer) {
            Integer val = Math.max(n1.intValue(), n2.intValue());
            return (T) val;
        } else {
            throw new RuntimeException("不支持的数据类型:" + cls.getName());
        }
    }

    /**
     * safe  String to Integer 
    * @param val
    * @param defalut
    * @return
    */
    public static Integer toInt(String val, Integer defalut) {
        try {
            return Integer.parseInt(val, 10);
        } catch (Exception e) {
            return defalut;
        }
    }

    public static void main(String[] args) {
        BigDecimal a = null;
        BigDecimal safe = safe(a, BigDecimal.class);
        System.out.println(safe);
    }
}
