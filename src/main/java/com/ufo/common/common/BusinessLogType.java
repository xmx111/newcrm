package com.ufo.common.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* 类名称：BusinessLogType 
* 类描述：业务日志类型定义 
* 
* 创建人：Duzj
* 创建时间：2012-12-6 下午4:10:06 
* @version 
* 
*/
public enum BusinessLogType {
    LOGIN, //登陆日志
    JOB, //时间机器人
    ;
    private static Map<BusinessLogType, Integer> typeMap = new HashMap<BusinessLogType, Integer>();
    private static Map<Integer, BusinessLogType> valueMap = new HashMap<Integer, BusinessLogType>();
    private static Map<BusinessLogType, String> nameMap = new HashMap<BusinessLogType, String>();
    static {
        typeMap.put(BusinessLogType.LOGIN, 1);
        typeMap.put(BusinessLogType.JOB, 2);
        
        nameMap.put(BusinessLogType.LOGIN, "登陆日志");
        nameMap.put(BusinessLogType.JOB, "时间机器人");
        for (BusinessLogType state : typeMap.keySet()) {
            valueMap.put(typeMap.get(state), state);
        }
    }

    public Integer value() {
        return typeMap.get(this);
    }

    public static BusinessLogType parse(int value) {
        if (!valueMap.containsKey(value)) {
            throw new IllegalArgumentException(value + " is not a valid BusinessLogType");
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

    public static List<BusinessLogType> list() {
        final ArrayList<BusinessLogType> list = new ArrayList<BusinessLogType>(typeMap.keySet());
        Collections.sort(list, new java.util.Comparator<BusinessLogType>() {
            @Override
            public int compare(BusinessLogType o1, BusinessLogType o2) {
                return o1.value().compareTo(o2.value());
            }

        });
        return list;
    }
}
