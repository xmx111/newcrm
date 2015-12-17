package com.ufo.core.utils;

import java.util.ArrayList;
import java.util.List;

import com.ufo.common.common.BusinessLogType;
import com.ufo.common.entity.BusinessLog;
import com.ufo.config.sys.entity.Manager;
import com.ufo.core.context.RuntimeContext;
import com.ufo.core.context.RuntimeContextThreadLocal;

/** 
* 类名称：LogUtils 
* 类描述：业务日志工具类 
* 
* 创建人：Duzj
* 创建时间：2012-12-6 下午3:50:07 
* @version 
*/
public class LogUtils {

    private static List<BusinessLog> cache = new ArrayList<BusinessLog>(5);

    /** 
     * 创建日志
     * @param checkinIdentity 
    * @return
    */
    public static BusinessLog buildLog(BusinessLogType type) {
        BusinessLog dto = new BusinessLog();
        RuntimeContext runtimeContext = RuntimeContextThreadLocal.get();
        if (null != runtimeContext) {
            dto.setClientIP(runtimeContext.getClientIp());
            dto.setRequestUrl(runtimeContext.getRequestUrl());
            Object user = runtimeContext.getUser();
            if (user instanceof Manager) {
                dto.setOperator((Manager) user);
            }
        }
        java.sql.Time operTime = new java.sql.Time(System.currentTimeMillis());
        java.sql.Date operDate = new java.sql.Date(System.currentTimeMillis());
        dto.setDate(operDate);
        dto.setTime(operTime);
        dto.setType(type.value());
        return dto;
    }

    /** 
     * 添加登陆日志
    * @param content
    * @param result
    */
    public static void addLogin(String content, Boolean result) {
        BusinessLog log = buildLog(BusinessLogType.LOGIN);
        log.setContent(content);
        log.setResult(result);
        addLog(log);
    }

    /** 
     * 添加时间机器人执行任务
    * @param content
    */
    public static void addJob(String content, Boolean result) {
        BusinessLog log = buildLog(BusinessLogType.JOB);
        log.setContent(content);
        log.setResult(result);
        addLog(log);
    }
    

    /** 
    * @param log
    */
    private static void addLog(BusinessLog log) {
        synchronized (cache) {
            cache.add(log);
        }
    }

    public static List<BusinessLog> pop(int size) {
        synchronized (cache) {
            size = cache.size() <= size ? cache.size() : size;
            final List<BusinessLog> subList = new ArrayList<BusinessLog>(size);
            for (int i = size - 1; i >= 0; i--) {
                subList.add(cache.remove(i));
            }
            return subList;
        }
    }

}
