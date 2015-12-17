package com.ufo.core.common;

import java.util.Date;

import com.ufo.core.utils.DateUtils;

/** 
*  控制操作成功, 返回的结果
* 类名称：ProcResult 
* 创建人：Duzj
* 创建时间：2011-12-28 下午8:47:42 
* @version 
* 
*/
public class ProcResult {

    /** 
    *sstatuscode 状态码
    */ 
    private Integer sstatuscode = 200;
    /** 
    *message 消息
    */ 
    private String smessage;
    /**
     *stime 响应时间
     */
    private String stime=DateUtils.formatSDate(new Date());
    /**
     *sdata 数据
     */
    private Object sdata;
    
    private String errorCode;
    private String navTabId;
    private String rel;
    private String callbackType;
    private String forwardUrl;    
    private String type;

    public ProcResult() {
    }

    public Integer getSstatuscode() {
        return sstatuscode;
    }

    public void setSstatuscode(Integer sstatuscode) {
        this.sstatuscode = sstatuscode;
    }

    public String getSmessage() {
        return smessage;
    }

    public void setSmessage(String smessage) {
        this.smessage = smessage;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public Object getSdata() {
        return sdata;
    }

    public void setSdata(Object sdata) {
        this.sdata = sdata;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getNavTabId() {
        return navTabId;
    }

    public void setNavTabId(String navTabId) {
        this.navTabId = navTabId;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public String getForwardUrl() {
        return forwardUrl;
    }

    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
