package com.ufo.core.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TreeDTO<T extends TreeDTO> extends IdDTO {

    /** 
     *code 编码
     */
    protected String code;
    /** 
     *name 名称 
     */
    protected String name;
    /** 
     *value 值 
     */
    protected String value;

    /** 
     *checked 是否选中
     */
    protected boolean checked = false;
    /** 
     *parent 自关联父对象
     */
    protected T parent;
    /** 
    *children 自关联子对象
    */
    protected List<T> children;
    
    /**
     * zTree 当前节点的父节点id属性
     */
    protected Integer pId;
    
    /**
     * zTree 是否打开
     */
    protected Boolean open;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonBackReference()
    public T getParent() {
        return parent;
    }

    public void setParent(T parent) {
        if (parent != null) {
            parent.addChildren(this);
        } else if (this.parent != null) {
            this.parent.removeChildren(this);
        }
        this.parent = parent;
    }

    @JsonManagedReference()
    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public void addChildren(T dto) {
        if (getId() != null) {
            if (this.children == null) {
                this.children = new ArrayList<T>();
            }
            if (!this.children.contains(dto)) {
                this.children.add(dto);
            }
        }
    }

    public void removeChildren(T dto) {
        if (this.children != null) {
            this.children.remove(dto);
        }
    }

    public boolean hasChilds() {
        return CollectionUtils.isNotEmpty(children);
    }

    public void addChildrens(List<T> coll) {
        if (CollectionUtils.isNotEmpty(coll)) {
            for (T dto : coll) {
                addChildren(dto);
            }
        }
    }

	public Integer getPId() {
		return pId;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

}
