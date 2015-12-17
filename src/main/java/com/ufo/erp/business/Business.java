package com.ufo.erp.business;

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

        @MetaData(value = "信用贷")
        CREDIT,

        @MetaData(value = "房贷")
        HOUSE,

        @MetaData(value = "车贷押证")
        CAR1,

        @MetaData(value = "车贷押车")
        CAR2

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

    private BigDecimal creditUpperLimit;

    private BigDecimal creditLowerLimit;

}
