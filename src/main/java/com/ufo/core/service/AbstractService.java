package com.ufo.core.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ufo.core.common.Paginator;
import com.ufo.core.dao.IGenericDAO;
import com.ufo.core.dto.IIdDTO;
import com.ufo.core.entity.IBasicEntity;
import com.ufo.core.entity.IIdEntity;
import com.ufo.core.entity.IUserEntity;
import com.ufo.core.service.assembler.AssemblerMananger;
import com.ufo.core.service.exception.ServiceException;
import com.ufo.core.utils.NumberUtils;

@Transactional
public abstract class AbstractService<T extends IIdDTO, M extends IIdEntity, A extends IAssembler<T, M>> extends BaseService implements IService<T> {
    private Class<IAssembler<T, M>> assembler;

    @SuppressWarnings("unchecked")
    public AbstractService() {
        final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
        assembler = (Class<IAssembler<T, M>>) type;
    }

    @Override
    public T saveOrUpdate(T dto) {
        IGenericDAO<M> dao = getGenericDAO();
        final Number id = (Number) dto.getId();
        final boolean isNew = NumberUtils.isEmpty(id);
        M model = isNew ? getAssembler().newModel() : dao.load(id);
        if (null == model) {
            throw new ServiceException("load data failed!");
        }
        buildData(model, dto, isNew);
        if (isNew) {
            dao.create(model);
            dto.setId(model.getId());
        } else {
            dao.update(model);
        }
        return dto;
    }

    @Override
    public Boolean delete(Serializable id) {
        IGenericDAO<M> dao = getGenericDAO();
        M model = dao.load(id);
        if (null == model) {
            return Boolean.FALSE;
        }
        dao.delete(model);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(readOnly = true)
    public T loadById(Serializable id) {
        IGenericDAO<M> dao = getGenericDAO();
        M model = dao.load(id);
        return toDTO(model);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> list() {
        List<M> list = getGenericDAO().list();
        return toDTOList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> list(Paginator paginator) {
        List<M> list = getGenericDAO().list(paginator);
        return toDTOList(list);
    }

    protected List<T> toDTOList(java.util.Collection<M> coll) {
        return toDTOList(coll, false);
    }

    protected List<T> toDTOList(java.util.Collection<M> coll, boolean deep) {
        final List<T> result = getAssembler().toDTOList(coll, deep);
        return result;
    }

    protected T toDTO(M model) {
        return getAssembler().toDTO(model, true);
    }

    protected void buildData(M model, T dto, boolean isNew) {
        getAssembler().toModel(model, dto);
        if (model instanceof IBasicEntity) {
            IBasicEntity entity = (IBasicEntity) model;
//            final IUserEntity operation = getOperation();
//            if (null != operation) {
//                entity.setOperation(operation);
//            }
//            entity.setOperTime(getCurrentTime());
        }
    }

    protected IAssembler<T, M> getAssembler() {
        return AssemblerMananger.getAssembler(assembler);
    }

    protected abstract IGenericDAO<M> getGenericDAO();

}
