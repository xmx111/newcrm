package com.ufo.core.dao.impl.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ufo.core.entity.IIdEntity;

public abstract class AbstractDAO<T extends IIdEntity> extends GenericDAO<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
