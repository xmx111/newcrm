package com.ufo.core.utils;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.ufo.core.common.JsonResult;
import com.ufo.core.common.JsonResultCodeType;

public class WebServiceUtils {
    /** 
     * 返回api接口所需的数据 
    * @param obj
    * @return
    */
    public static String resultToApi(Object obj, String error) {
        JsonResult jsonResult = new JsonResult();
        if (obj == null) {
            jsonResult.setResultCode(JsonResultCodeType.FAIL.value());

        } else if (obj instanceof Collection) {
            Collection<?> coll = (Collection<?>) obj;
            if (CollectionUtils.isEmpty(coll)) {
                jsonResult.setResultCode(JsonResultCodeType.FAIL.value());
            }
        }
        if (StringUtils.isNotBlank(error)) {
            jsonResult.setResultException(error);
        }
        jsonResult.setResultValue(obj);
        StringBuffer buf = new StringBuffer();
        buf.append("{\"ResultCode\":").append(jsonResult.getResultCode());
        buf.append(",\"ResultException\":\"").append(jsonResult.getResultException());
        buf.append("\",\"ResultValue\":").append(JsonUtils.toJson(obj));
        buf.append("}");
        return buf.toString();
    }

    public static String resultToApi(Object obj) {
        return resultToApi(obj, null);
    }
}
