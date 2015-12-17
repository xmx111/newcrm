package com.ufo.core.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 统一定义id的entity基类.
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity implements IIdEntity {

    /** 
    *id PK
    */ 
    protected Integer id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@GeneratedValue(generator = "system-uuid")
    //@GenericGenerator(name = "system-uuid", strategy = "uuid")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
