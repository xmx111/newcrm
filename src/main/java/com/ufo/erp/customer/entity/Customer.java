package com.ufo.erp.customer.entity;

import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.UndeleteEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/***
 * 客户
 *
 * @author hekang
 * @version $Id:$
 * @created 2015/12/17
 */
@MetaData(value = "客户")
@Entity
@Table(name = "tb_erp_customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends UndeleteEntity implements java.io.Serializable {

    private static final long serialVersionUID = -299434535037278137L;

    public static enum SexEnum {
        @MetaData(value = "男")
        MAN,

        @MetaData(value = "女")
        WOMAN,

        @MetaData(value = "其它")
        OTHER
    }

    public static enum MaritalStatusEnum {
        @MetaData(value = "未婚")
        SINGLE,

        @MetaData(value = "已婚")
        MARRIED,

        @MetaData(value = "离异")
        DIVORCED,

        @MetaData(value = "丧偶")
        WIDOWED
    }

    @MetaData(value = "客户名字")
    @EntityAutoCode(order = 5, listShow = true)
    private String name;

    @MetaData(value = "客户名字缩写")
    @EntityAutoCode(order = 6, listShow = false)
    private String abbr;

    @MetaData(value = "手机号")
    @EntityAutoCode(order = 10, listShow = true)
    private String mobile;

    @MetaData(value = "身份证")
    @EntityAutoCode(order = 15, listShow = true)
    private String cardNo;

    @MetaData(value = "年龄")
    @EntityAutoCode(order = 20, listShow = true)
    private Integer age;

    @MetaData(value = "地址")
    @EntityAutoCode(order = 25, listShow = true)
    private String address;

    @MetaData(value = "性别")
    @EntityAutoCode(order = 30, listShow = true)
    private SexEnum sex;

    @MetaData(value = "婚否")
    @EntityAutoCode(order = 35, listShow = true)
    private MaritalStatusEnum maritalStatus;

    @MetaData(value = "是否有房")
    @EntityAutoCode(order = 40, listShow = true)
    private Boolean hasHouse;

    @MetaData(value = "房子是否贷款")
    @EntityAutoCode(order = 45, listShow = true)
    private Boolean isHouseCredit;

    @MetaData(value = "是否有车")
    @EntityAutoCode(order = 50, listShow = true)
    private Boolean hasCar;

    @MetaData(value = "车是否贷款")
    @EntityAutoCode(order = 55, listShow = true)
    private Boolean isCarCredit;

    @MetaData(value = "是否有流水")
    @EntityAutoCode(order = 60, listShow = true)
    private Boolean hasAccount;

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

    @Column(length = 16)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "card_no", length = 20)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(length = 128)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    @Column(name = "marital_status")
    @Enumerated(EnumType.ORDINAL)
    public MaritalStatusEnum getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusEnum maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Column(name = "has_house")
    public Boolean getHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(Boolean hasHouse) {
        this.hasHouse = hasHouse;
    }

    @Column(name = "is_house_credit")
    public Boolean getIsHouseCredit() {
        return isHouseCredit;
    }

    public void setIsHouseCredit(Boolean isHouseCredit) {
        this.isHouseCredit = isHouseCredit;
    }

    @Column(name = "has_car")
    public Boolean getHasCar() {
        return hasCar;
    }

    public void setHasCar(Boolean hasCar) {
        this.hasCar = hasCar;
    }

    @Column(name = "is_car_credit")
    public Boolean getIsCarCredit() {
        return isCarCredit;
    }

    public void setIsCarCredit(Boolean isCarCredit) {
        this.isCarCredit = isCarCredit;
    }

    @Column(name = "has_account")
    public Boolean getHasAccount() {
        return hasAccount;
    }

    public void setHasAccount(Boolean hasAccount) {
        this.hasAccount = hasAccount;
    }

    @Column(name = "is_good_credit")
    public Boolean getIsGoodCredit() {
        return isGoodCredit;
    }

    public void setIsGoodCredit(Boolean isGoodCredit) {
        this.isGoodCredit = isGoodCredit;
    }
}
