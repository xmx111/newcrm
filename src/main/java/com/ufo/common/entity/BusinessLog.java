package com.ufo.common.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.BaseEntity;

/** 
* 类名称：BusinessLog 
* 类描述： 业务日志
* 
* 
* 创建人：hekang
* 创建时间：2012-10-23 上午11:04:38 
* @version 
* 
*/
@MetaData(value = "业务日志")
@Entity
@Table(name = "tb_sys_business_log")
public class BusinessLog extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @MetaData(value = "访问IP")
    @EntityAutoCode(order = 5, listShow = true)
    private String clientIP;
    
    @MetaData(value = "访问地址")
    @EntityAutoCode(order = 10, listShow = true)
    private String requestUrl;
    /** 
     * 业务类型: @see com.ufo.common.common.BusinessLogType
     */
    @MetaData(value = "业务类型")
    @EntityAutoCode(order = 15, listShow = true)
    private Integer type;

    /** 
    *operType 操作类型,1--新增,2---修改,3--删除 ,4--登陆日志....
    */
    @MetaData(value = "操作类型")
    @EntityAutoCode(order = 20, listShow = true)
    private Integer operType;
    
    @MetaData(value = "操作日期")
    private Date date;
    
    @MetaData(value = "操作时间")
    @EntityAutoCode(order = 25, listShow = true)
    private Time time;
    
    @MetaData(value = "操作员")
    @EntityAutoCode(order = 30, listShow = true)
    private Manager operator;
    
    @MetaData(value = "操作内容")
    private String content;
    
    @MetaData(value = "操作结果")
    @EntityAutoCode(order = 35, listShow = true)
    private Boolean result;

    @Column(name = "client_ip")
    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    @Column(name = "request_url")
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Column
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "oper_type")
    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    @Column
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oper_id")
    @ForeignKey(name = "fk_businesslog_operator")
    public Manager getOperator() {
        return operator;
    }

    public void setOperator(Manager operator) {
        this.operator = operator;
    }

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column
    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

}
