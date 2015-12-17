package com.ufo.core.dto;

import javax.persistence.Column;

public abstract class UndeleteDTO extends IdDTO {

    /** 
     *deleted 是否删除  0=false=未删除,1=true=已删除
     */
    protected Boolean deleted = Boolean.FALSE;

    @Column
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
