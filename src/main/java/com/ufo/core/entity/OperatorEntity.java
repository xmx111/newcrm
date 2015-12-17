package com.ufo.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ufo.config.sys.entity.Manager;
import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.web.json.LongTimestampJsonSerializer;

import javax.persistence.*;
import java.sql.Timestamp;

/** 
* 类名称：UndeleteEntity 
* 类描述：非物理删除的实体数据层 
* 
* 创建人：hekang
* 创建时间：2014-8-8 上午10:36:58 
* @version 
* 
*/
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class OperatorEntity extends UndeleteEntity {

    @MetaData(value = "录入时间")
    protected Timestamp createTime;

    @MetaData(value = "录入人")
    protected Manager createOperator;

    @MetaData(value = "修改时间")
    protected Timestamp updateTime;

    @MetaData(value = "修改人")
    protected Manager updateOperator;

    @MetaData(value = "删除时间")
    protected Timestamp deleteTime;

    @MetaData(value = "删除人")
    protected Manager deleteOperator;

    @Column(name="create_time")
    @JsonSerialize(using = LongTimestampJsonSerializer.class)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_operator_id")
    @JsonIgnore
    public Manager getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(Manager createOperator) {
        this.createOperator = createOperator;
    }

    @Column(name="update_time")
    @JsonSerialize(using = LongTimestampJsonSerializer.class)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_operator_id")
    @JsonIgnore
    public Manager getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(Manager updateOperator) {
        this.updateOperator = updateOperator;
    }

    @Column(name="delete_time")
    @JsonSerialize(using = LongTimestampJsonSerializer.class)
    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delete_operator_id")
    @JsonIgnore
    public Manager getDeleteOperator() {
        return deleteOperator;
    }

    public void setDeleteOperator(Manager deleteOperator) {
        this.deleteOperator = deleteOperator;
    }
}
