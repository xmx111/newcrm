package com.ufo.erp.company.entity;

import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.UndeleteEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/***
 * 小贷公司
 *
 * @author hekang
 * @version $Id:$
 * @created 2015/12/17
 */
@MetaData(value = "小贷公司")
@Entity
@Table(name = "tb_erp_company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Company extends UndeleteEntity implements java.io.Serializable {

    private static final long serialVersionUID = 7864106981669900822L;

    @MetaData(value = "小贷公司名字")
    @EntityAutoCode(order = 5, listShow = true)
    private String name;

    @MetaData(value = "小贷公司名字缩写")
    @EntityAutoCode(order = 6, listShow = false)
    private String abbr;

    @MetaData(value = "电话")
    @EntityAutoCode(order = 10, listShow = true)
    private String tel;

    @Column(length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 80)
    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    @Column(length = 20)
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
