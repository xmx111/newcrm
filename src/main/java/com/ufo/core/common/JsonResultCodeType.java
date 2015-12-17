package com.ufo.core.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* 类名称：JsonResultCodeType 
* 类描述： 
* 
* 
* 创建人：HuZhiGang
* 创建时间：2012-3-26 下午04:14:59 
* @version 
* 
*/
public enum JsonResultCodeType {

    SUCCESS, //成功
    FAIL, //失败
    ;
    private static Map<JsonResultCodeType, Integer> typeMap = new HashMap<JsonResultCodeType, Integer>();
    private static Map<Integer, JsonResultCodeType> valueMap = new HashMap<Integer, JsonResultCodeType>();
    private static Map<JsonResultCodeType, String> nameMap = new HashMap<JsonResultCodeType, String>();
    static {
        typeMap.put(JsonResultCodeType.SUCCESS, 0);
        typeMap.put(JsonResultCodeType.FAIL, -1);
        nameMap.put(JsonResultCodeType.SUCCESS, "成功");
        nameMap.put(JsonResultCodeType.FAIL, "失败");
        for (JsonResultCodeType state : typeMap.keySet()) {
            valueMap.put(typeMap.get(state), state);
        }
    }

    public Integer value() {
        return typeMap.get(this);
    }

    public static JsonResultCodeType parse(int value) {
        if (!valueMap.containsKey(value)) {
            throw new IllegalArgumentException(value + " is not a valid CardStatus");
        }
        return valueMap.get(value);
    }

    public String toName() {
        return nameMap.get(this);
    }

    public Integer getValue() {
        return value();
    }

    public String getName() {
        return toName();
    }

    public static List<JsonResultCodeType> list() {
        final ArrayList<JsonResultCodeType> list = new ArrayList<JsonResultCodeType>(typeMap.keySet());
        Collections.sort(list, new java.util.Comparator<JsonResultCodeType>() {
            @Override
            public int compare(JsonResultCodeType o1, JsonResultCodeType o2) {
                return o1.value().compareTo(o2.value());
            }

        });
        return list;
    }
}
