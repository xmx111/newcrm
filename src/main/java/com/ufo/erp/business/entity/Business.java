package com.ufo.erp.business.entity;

import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.UndeleteEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/***
 * 贷款业务
 *
 * @author hekang
 * @version $Id:$
 * @created 2015/12/17
 */
@MetaData(value = "小贷公司")
@Entity
@Table(name = "tb_erp_business")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Business extends UndeleteEntity implements java.io.Serializable {

    private static final long serialVersionUID = 7305538476847670298L;

    public static enum BusinessType {

        @MetaData(value = "抵押贷")
        MORTGAGED,

        @MetaData(value = "无抵押贷")
        CREDIT,

        @MetaData(value = "生意贷")
        BUSINESS

    }

    @MetaData(value = "业务名字")
    @EntityAutoCode(order = 5, listShow = true)
    private String name;

    @MetaData(value = "业务名字缩写")
    @EntityAutoCode(order = 6, listShow = false)
    private String abbr;

    @MetaData(value = "是否需要征信")
    @EntityAutoCode(order = 10, listShow = false)
    private Boolean needCredit;

    @MetaData(value = "是否需要房产证明")
    @EntityAutoCode(order = 10, listShow = false)
    private Boolean needHouse;

    @MetaData(value = "是否需要车子证明")
    @EntityAutoCode(order = 10, listShow = false)
    private Boolean needCar;

    @MetaData(value = "是否需要流水")
    @EntityAutoCode(order = 10, listShow = false)
    private Boolean needAccount;

    @MetaData(value = "贷款额度下限")
    @EntityAutoCode(order = 10, listShow = false)
    private BigDecimal creditLowerLimit;

    @MetaData(value = "贷款额度上限")
    @EntityAutoCode(order = 10, listShow = false)
    private BigDecimal creditUpperLimit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public Boolean getNeedCredit() {
        return needCredit;
    }

    public void setNeedCredit(Boolean needCredit) {
        this.needCredit = needCredit;
    }

    public Boolean getNeedHouse() {
        return needHouse;
    }

    public void setNeedHouse(Boolean needHouse) {
        this.needHouse = needHouse;
    }

    public Boolean getNeedCar() {
        return needCar;
    }

    public void setNeedCar(Boolean needCar) {
        this.needCar = needCar;
    }

    public Boolean getNeedAccount() {
        return needAccount;
    }

    public void setNeedAccount(Boolean needAccount) {
        this.needAccount = needAccount;
    }

    public BigDecimal getCreditLowerLimit() {
        return creditLowerLimit;
    }

    public void setCreditLowerLimit(BigDecimal creditLowerLimit) {
        this.creditLowerLimit = creditLowerLimit;
    }

    public BigDecimal getCreditUpperLimit() {
        return creditUpperLimit;
    }

    public void setCreditUpperLimit(BigDecimal creditUpperLimit) {
        this.creditUpperLimit = creditUpperLimit;
    }
}
