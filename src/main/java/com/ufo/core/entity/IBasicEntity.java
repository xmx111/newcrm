package com.ufo.core.entity;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

/** 
* 类名称：IBasicEntity 
* 类描述： 
* 
* 
* 创建人：Duzj
* 创建时间：2012-2-5 下午6:25:19 
* @version 
* 
*/
@MappedSuperclass
public interface IBasicEntity extends IIdEntity {
    /**
     * 获得操作员
    * @return
     */
    public IUserEntity getOperation();

    /**
     * 设置操作员
    * @param operation
     */
    public void setOperation(IUserEntity operation);

    /**
     * 获得操作时间
    * @return
     */
    public Timestamp getOperTime();

    /**
     * 设置操作时间
    * @param operTime
     */
    public void setOperTime(Timestamp operTime);
}
