package com.ufo.config.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ufo.core.annotation.EntityAutoCode;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.UndeleteEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** 
* 类名称：Manager 
* 类描述：系统操作员实体 
* 
* 创建人：hekang
* 创建时间：2014-8-9 上午9:20:55 
* @version 
* 
*/
@MetaData(value = "系统操作员")
@Entity
@Table(name = "tb_sys_manager")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Manager extends UndeleteEntity implements UserDetails, java.io.Serializable {

    public static enum ManagerEnum {
        @MetaData(value = "供货商")
        SUPPLIER,

        @MetaData(value = "员工")
        EMPLOYEE,

        @MetaData(value = "客户")
        CUSTOM
    }

    public static enum ManagerStatusEnum {
        @MetaData(value = "正常")
        NORMAL,

        @MetaData(value = "已锁定")
        LOCK,

        @MetaData(value = "已注销")
        LOGOUT
    }

    @MetaData(value = "账号名(账号编码)")
    @EntityAutoCode(order = 5, listShow = true)
    private String loginName;

    @MetaData(value = "账号编码(暂时无用)")
    private String code;
    
    @MetaData(value = "账号名称")
    private String name;
    
    @MetaData(value = "账号密码")
    private String password;

    @MetaData(value = "账号类型")
    private ManagerEnum type;
    
    @MetaData(value = "账号状态")
    @EntityAutoCode(order = 20, listShow = true)
    private ManagerStatusEnum status = ManagerStatusEnum.NORMAL;
    
    @MetaData(value = "可用角色")
    private Set<Role> roles = new HashSet<Role>(0);

    @Transient
    private Collection<GrantedAuthority> authorities;
    
    @Column(name = "login_name", length = 30, unique = true)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password", length = 128)
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    @Enumerated(EnumType.ORDINAL)
    public ManagerEnum getType() {
        return type;
    }

    public void setType(ManagerEnum type) {
        this.type = type;
    }

    @Enumerated(EnumType.ORDINAL)
    public ManagerStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ManagerStatusEnum status) {
        this.status = status;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_sys_manager_role_ref", joinColumns = { @JoinColumn(name = "manager_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") })
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id")
    @ForeignKey(name = "fk_user_role", inverseName = "fk_role_user")
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    @Transient
    public String getUsername() {
        return name;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return status.equals(ManagerStatusEnum.NORMAL) ? true : false;
    }
}
