package com.ufo.config.sys.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.UndeleteEntity;

/** 
* 类名称：Role 
* 类描述：角色只定义权限 
* 
* 创建人：hekang
* 创建时间：2014-8-9 上午9:20:55  
* @version 
* 
*/
@MetaData(value = "角色")
@Entity
@Table(name = "tb_sys_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends UndeleteEntity implements java.io.Serializable {

    public final static String TB_ROLE_AUTH = "tb_sys_role_authority_ref";

    /** 
    *serialVersionUID 
    */
    private static final long serialVersionUID = -2272023312849722736L;

    @MetaData(value = "编码")
    @EntityAutoCode(order = 5, listShow = true)
    private String code;

    @MetaData(value = "名称 ")
    @EntityAutoCode(order = 10, listShow = true)
    private String name;

    @MetaData(value = "描述")
    @EntityAutoCode(order = 15, listShow = true)
    private String description;

    @MetaData(value = "权限 ")
    private Set<Authority> authorities;

    @MetaData(value = "用户 ")
    private Set<Manager> managers;
    
    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 24)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = TB_ROLE_AUTH, joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "authority_id", referencedColumnName = "id") })
    @ForeignKey(name = "fk_role_authority", inverseName = "fk_authority_role")
    @JsonIgnore
    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    @ManyToMany(mappedBy = "roles")
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    public Set<Manager> getUsers() {
        return managers;
    }

    public void setUsers(Set<Manager> managers) {
        this.managers = managers;
    }
    
    @JsonIgnore
    @Transient
    public Integer[] getAuthoritiesIds(){
    	Integer[] authoritiesIds = null;
    	if (authorities != null){
    		authoritiesIds = new Integer[authorities.size()];
    		Iterator<Authority> it = authorities.iterator();
    		int i = 0;
    		while(it.hasNext()){
    			authoritiesIds[i] = it.next().getId();
    			i ++;
    		}
    	}
    	return authoritiesIds;
    }

}
