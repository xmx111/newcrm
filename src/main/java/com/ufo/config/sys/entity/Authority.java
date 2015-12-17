package com.ufo.config.sys.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
* 权限
* 类名称：Authority 
* 
* 创建人：hekang
* 创建时间：2014-8-9 上午9:20:55  
* 修改备注： 
* @version 
* 
*/
@MetaData(value = "权限")
@Entity
@Table(name = "tb_sys_authority")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Authority extends UndeleteEntity implements java.io.Serializable {
    
    public static enum AuthorityTypeEnum {

        @MetaData(value = "权限")
        AUTH,

        @MetaData(value = "菜单")
        MENU;

    }
    
    public static enum SysTypeEnum {

        @MetaData(value = "基础数据")
        BASEDATA,
 
        @MetaData(value = "基础数据-企业宣传资料")
        BASEDATA_PUBLICITY,

        @MetaData(value = "项目管理")
        PROJECT,

        @MetaData(value = "工程管理")
        ENGINEE,

        @MetaData(value = "用户中心")
        UCENTER,

        @MetaData(value = "系统管理")
        SYSTEM;

    }
    
    /** 
     *serialVersionUID 
     */ 
    private static final long serialVersionUID = -8242707739317279783L;
    
    @MetaData(value = "编码")
    @EntityAutoCode(order = 5, listShow = true)
    private String code;
    
    @MetaData(value = "名称")
    @EntityAutoCode(order = 10, listShow = true)
    private String name;
    
    @MetaData(value = "排序")
    @EntityAutoCode(order = 15, listShow = true)
    private Integer sequence = Integer.valueOf(0);

    @MetaData(value = "手动更新时间")
    @EntityAutoCode(order = 20, listShow = true)
    private Timestamp updateTime;

    @MetaData(value = "角色 ")
    private Set<Role> roles;

    @MetaData(value = "上级权限")
    @EntityAutoCode(order = 25, listShow = true)
    private Authority parent;

    @MetaData(value = "子权限")
    private List<Authority> children = new ArrayList<Authority>();

    @MetaData(value = "类型")
    private AuthorityTypeEnum type = AuthorityTypeEnum.AUTH;

    @MetaData(value = "类型")
    private SysTypeEnum sysType;

    @MetaData(value = "URL")
    private String url;
    
    @MetaData(value = "是否打开")
    private Boolean checked;
    
    @Column(unique = true, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @ForeignKey(name = "fk_authority_parent")
    @JsonIgnore
    public Authority getParent() {
        return parent;
    }

    public void setParent(Authority parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sequence")
    @JsonIgnore
    public List<Authority> getChildren() {
        return children;
    }

    public void setChildren(List<Authority> children) {
        this.children = children;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Column
    public AuthorityTypeEnum getType() {
        return type;
    }

    public void setType(AuthorityTypeEnum type) {
        this.type = type;
    }

    @Column(name = "sys_type")
    public SysTypeEnum getSysType() {
		return sysType;
	}

	public void setSysType(SysTypeEnum sysType) {
		this.sysType = sysType;
	}

	@Column
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	@Column
    public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	@Transient
    public Boolean getHasChildren() {
        return children != null && children.size() != 0;
    }

    @Transient
    public void removeChildren(Authority dto) {
        if (this.children != null) {
            this.children.remove(dto);
        }
    }

}
