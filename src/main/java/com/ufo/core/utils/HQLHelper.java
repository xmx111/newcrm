package com.ufo.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HQLHelper {
    /** 
    * @param buf
    */
    public static void appendAndToWhereClause(StringBuffer buf) {
        if (buf.toString().toLowerCase().indexOf("where") == -1) {
            buf.append(" where ");
        } else {
            buf.append(" and ");
        }
    }

    public static void appendOrToWhereClause(StringBuffer buf) {
        if (buf.toString().toLowerCase().indexOf("where") == -1) {
            buf.append(" where ");
        } else {
            buf.append(" or ");
        }
    }

    /**
     * Append and to where clause if needed
     * @param whereBuilder
     */
    public static void appendAndToWhereClause(StringBuilder whereBuilder) {
        if (whereBuilder.length() > 0) {
            whereBuilder.append(" and ");
        }
    }

    /**
     * Build hql where clause using given propertyName/value pair conditions
     * @param conditions
     * @param alias
     * @param operation
     * @param outParas
     * @return
     */
    public static String buildWhereClause(Map<String, Object> conditions, String alias, HQLOperation operation,
            List<Object> outParas) {
        if (null == conditions || conditions.isEmpty()) {
            return "";
        }
        StringBuilder whereClause = new StringBuilder();
        for (String paraName : conditions.keySet()) {
            if (whereClause.length() > 0) {
                whereClause.append(" and ");
            }
            whereClause.append(alias);
            whereClause.append(".");
            whereClause.append(paraName);
            whereClause.append(" ");
            whereClause.append(operation.symbol());
            whereClause.append(" ?");
            outParas.add(conditions.get(paraName));
        }
        return whereClause.toString();
    }
}

enum HQLOperation {
    equals, like;

    private static Map<HQLOperation, String> operationSymbolMap = new HashMap<HQLOperation, String>();

    static {
        operationSymbolMap.put(equals, "=");
        operationSymbolMap.put(like, "like");
    }

    public String symbol() {
        return operationSymbolMap.get(this);
    }
}