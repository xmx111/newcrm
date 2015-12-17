package com.ufo.core.service;

import java.io.Serializable;
import java.util.List;

import com.ufo.core.common.Paginator;
import com.ufo.core.dto.IIdDTO;

public interface IService<T extends IIdDTO> {
    public T saveOrUpdate(T dto);

    public Boolean delete(Serializable id);

    public T loadById(java.io.Serializable id);

    public List<T> list();

    public List<T> list(Paginator paginator);
}
