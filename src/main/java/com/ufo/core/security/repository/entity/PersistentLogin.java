package com.ufo.core.security.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ufo.core.annotation.MetaData;
import com.ufo.core.entity.PersistableEntity;

@MetaData(value = "免登录")
@Entity
@Table(name = "persistent_logins")
public class PersistentLogin extends PersistableEntity<String> {
    
    /** 
     *serialVersionUID 
     */ 
    private static final long serialVersionUID = -1284371816819006247L;

    @MetaData(value = "token")
    private String id;

    @MetaData(value = "免登录名")
    private String loginName;

    @MetaData(value = "令牌")
    private String token;
    
    @MetaData(value = "最后一次登录时间")
    private Date lastUsed;

    @Override
    @Id
    @Column(name="series")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="username")
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Column(name="last_used")
    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

}
