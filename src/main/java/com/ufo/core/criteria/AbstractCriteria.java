package com.ufo.core.criteria;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbstractCriteria {
    private Integer id;
    private String keyword;
    private Boolean deleted = Boolean.FALSE;
    private boolean deep = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeep() {
        return deep;
    }

    public void setDeep(boolean deep) {
        this.deep = deep;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
