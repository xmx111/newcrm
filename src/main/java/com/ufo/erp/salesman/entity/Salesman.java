package com.ufo.erp.salesman.entity;

import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.UndeleteEntity;
import com.ufo.erp.customer.entity.Customer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/***
 * Describe
 *
 * @author hekang
 * @version $Id:$
 * @created 2015/12/17
 */
@MetaData(value = "业务员")
@Entity
@Table(name = "tb_erp_salesman")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Salesman extends UndeleteEntity implements java.io.Serializable {

    private static final long serialVersionUID = -2935769858742352663L;

    @MetaData(value = "业务员名字")
    @EntityAutoCode(order = 5, listShow = true)
    private String name;

    @MetaData(value = "业务员名字缩写")
    @EntityAutoCode(order = 6, listShow = false)
    private String abbr;

    @MetaData(value = "手机号")
    @EntityAutoCode(order = 10, listShow = true)
    private String mobile;

    @MetaData(value = "性别")
    @EntityAutoCode(order = 15, listShow = true)
    private Customer.SexEnum sex;

    @MetaData(value = "微信号")
    @EntityAutoCode(order = 20, listShow = true)
    private String weixin;

    @MetaData(value = "QQ号")
    @EntityAutoCode(order = 25, listShow = true)
    private String qq;

    @Column(length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 32)
    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    @Column(length = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    public Customer.SexEnum getSex() {
        return sex;
    }

    public void setSex(Customer.SexEnum sex) {
        this.sex = sex;
    }

    @Column(length = 32)
    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Column(length = 32)
    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
