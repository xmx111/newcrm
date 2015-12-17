package com.ufo.core.entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.ufo.core.annotation.MetaData;

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
public abstract class UndeleteEntity extends BaseEntity {
    
    public static enum DeleteTypeEnum {

        @MetaData(value = "未删")
        UNDELETE,

        @MetaData(value = "已删")
        DELETE;

    }
    
    @MetaData(value = "是否删除")
    protected DeleteTypeEnum deleted = DeleteTypeEnum.UNDELETE;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="deleted")
    public DeleteTypeEnum getDeleted() {
        return deleted;
    }

    public void setDeleted(DeleteTypeEnum deleted) {
        this.deleted = deleted;
    }

}
