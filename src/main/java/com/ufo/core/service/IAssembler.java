package com.ufo.core.service;

import java.util.Collection;
import java.util.List;

import com.ufo.core.dto.IIdDTO;
import com.ufo.core.entity.IIdEntity;

public interface IAssembler<T extends IIdDTO, M extends IIdEntity> {
    /** 
    * @return 
    */
    M newModel();

    /** 
    * @return  
    */
    T newDTO();

    /**  实体对象转交换数据对象.
    * @param model
    * @return
    */
    T toDTO(M model);

    /** 
     *  实体对象转交换数据对象.
    * @param model
    * @param deep
    * @return
    */
    T toDTO(M model, boolean deep);

    /** 
     * 实体对象转交换数据对象.只转换基本数据对象
    * @param model
    * @return
    */
    T toSameDTO(M model);

    /** 
     * 实体数据集转交换数据集
    * @param coll
    * @return
    */
    List<T> toDTOList(Collection<M> coll);

    /**
     *实体数据集转交换数据集 
    * @param coll
    * @param deep
    * @return
    */
    List<T> toDTOList(Collection<M> coll, boolean deep);

    /** 
     * 交换数据转实体数据
    * @param model
    * @param dto
    */
    void toModel(M model, T dto);

    /** 
    *清空缓存  
    */
    void cleanCache();

}
