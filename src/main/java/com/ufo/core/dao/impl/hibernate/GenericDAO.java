package com.ufo.core.dao.impl.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.ufo.core.common.Paginator;
import com.ufo.core.dao.IGenericDAO;
import com.ufo.core.dao.exception.DAOException;
import com.ufo.core.dao.exception.ObjectNotFoundException;

/**
 * This class implements Data Access Object (DAO) interface using Hibernate.   
 * This is also an base class used to provide common methods to all DAOs.
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericDAO<T> extends HibernateDaoSupport implements IGenericDAO<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected Class<T> clz;

    /*
     * Construct method
     */
    public GenericDAO() {
        if (logger.isDebugEnabled()) {
            logger.debug("start getting class type of template class.");
        }
        clz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        if (logger.isDebugEnabled()) {
            logger.debug("end getting class type of template class:" + clz);
        }
    }

    @Override
    public List<T> all() {
        return list();
    }

    /*
     * (non-Javadoc)
     * @see com.ufo.core.dao.IGenericDAO#list()
     */
    public List<T> list() {
        try {
            return this.getHibernateTemplate().loadAll(clz);
        } catch (DataAccessException e) {
            logger.error("Error in getObjects.", e);
            throw new DAOException("errors.dao", new Object[] { "getObjects" }, "Error in getObjects", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ufo.core.dao.IGenericDAO#load(java.io.Serializable)
     */
    public T load(Serializable id) {
        try {
            T obj = (T) this.getHibernateTemplate().get(clz, id);
            if (obj == null) {
                throw new ObjectNotFoundException("errors.dao.objectNotFound",
                        new Object[] { clz.getSimpleName(), id }, "Can not find " + clz.getSimpleName() + " by id "
                                + id);
            }
            return obj;
        } catch (org.hibernate.ObjectNotFoundException e) {
            throw new ObjectNotFoundException("errors.dao.objectNotFound", new Object[] { clz.getSimpleName(), id },
                    "Can not find " + clz.getSimpleName() + " by id " + id, e);
        } catch (DataRetrievalFailureException e) {
            logger.error("Error in loadObject.", e);
            throw new ObjectNotFoundException("errors.dao.objectNotFound", new Object[] { clz.getSimpleName(), id },
                    "Can not find " + clz.getSimpleName() + " by id " + id, e);
        } catch (DataAccessException e) {
            logger.error("Error in loadObject.", e);
            throw new DAOException("errors.dao", new Object[] { "loadObject" }, "Error in loadObject", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ufo.core.dao.IGenericDAO#create(java.lang.Object)
     */
    public Serializable create(T obj) {
        try {
            return this.getHibernateTemplate().save(obj);
        } catch (DataAccessException e) {
            logger.error("Error in createObject.", e);
            throw new DAOException("errors.dao", new Object[] { "createObject" }, "Error in createObject", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ufo.core.dao.IGenericDAO#update(java.lang.Object)
     */
    public void update(T obj) {
        try {
            this.getHibernateTemplate().saveOrUpdate(obj);
        } catch (DataAccessException e) {
            logger.error("Error in updateObject.", e);
            throw new DAOException("errors.dao", new Object[] { "updateObject" }, "Error in updateObject", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ufo.core.dao.IGenericDAO#delete(java.lang.Object)
     */
    public void delete(T obj) {
        try {
            this.getHibernateTemplate().delete(obj);
        } catch (DataAccessException e) {
            logger.error("Error in deleteObject.", e);
            throw new DAOException("errors.dao", new Object[] { "deleteObject" }, "Error in deleteObject", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause
     * @param hql The hql clause
     * @return List of queried objects
     */
    public List<T> find(String hql) {
        try {
            return this.getHibernateTemplate().find(hql);
        } catch (DataAccessException e) {
            logger.error("Error in find(String).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause and a parameter
     * @param hql The hql clause
     * @param para The parameter for the hql clause
     * @return List of queried objects
     */
    public List<T> find(String hql, Object para) {
        try {
            return this.getHibernateTemplate().find(hql, para);
        } catch (DataAccessException e) {
            logger.error("Error in find(String, Object).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause and parameters
     * @param hql The hql clause
     * @param paras The parameters for the hql clause 
     * @return List of queried objects
     */
    public List<T> find(String hql, Object[] paras) {
        try {
            return this.getHibernateTemplate().find(hql, paras);
        } catch (DataAccessException e) {
            logger.error("Error in find(String, Object[]).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause and parameters
     * @param hql The hql clause
     * @param paraNames The parameter names for the hql clause
     * @param paraValues The parameter values for the hql clause
     * @return List of queried objects
     */
    public List<T> find(String hql, String[] paraNames, Object[] paraValues) {
        try {
            return this.getHibernateTemplate().findByNamedParam(hql, paraNames, paraValues);
        } catch (DataAccessException e) {
            logger.error("Error in find(String, String[], Object[]).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause and a parameter
     * @param hql The hql clause
     * @param paraName The parameter name for the hql clause
     * @param paraValue The parameter value for the hql clause
     * @return List of queried objects
     */
    public List<T> find(String hql, String paraName, Object paraValue) {
        try {
            return this.getHibernateTemplate().findByNamedParam(hql, paraName, paraValue);
        } catch (DataAccessException e) {
            logger.error("Error in find(String, String, Object).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause
     * @param hql The hql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<T> find(String hql, int firstResult, int maxResults) {
        return find(hql, null, firstResult, maxResults);
    }

    /**
     * Generic method to query objects according to hql clause and a parameter
     * @param hql The hql clause
     * @param para The parameter for the hql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<T> find(String hql, Object para, int firstResult, int maxResults) {
        return find(hql, new Object[] { para }, firstResult, maxResults);
    }

    /**
     * Generic method to query objects according to hql clause and parameters
     * @param hql The hql clause
     * @param paras The parameters for the hql clause 
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<T> find(final String hql, final Object[] paras, final int firstResult, final int maxResults) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (List) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createQuery(hql);
                    prepareQuery(queryObject, template);
                    queryObject.setFirstResult(firstResult);
                    queryObject.setMaxResults(maxResults);
                    if (paras != null) {
                        for (int i = 0; i < paras.length; i++) {
                            queryObject.setParameter(i, paras[i]);
                        }
                    }
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in find(String, Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query objects according to hql clause and a parameter
     * @param hql The hql clause
     * @param paraName The parameter name for the hql clause
     * @param paraValue The parameter value for the hql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<T> find(String hql, String paraName, Object paraValue, int firstResult, int maxResults) {
        return find(hql, new String[] { paraName }, new Object[] { paraValue }, firstResult, maxResults);
    }

    /**
     * Generic method to query objects according to hql clause and parameters
     * @param hql The hql clause
     * @param paraNames The parameter names for the hql clause
     * @param paraValues The parameter values for the hql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<T> find(final String hql, final String[] paraNames, final Object[] paraValues, final int firstResult,
            final int maxResults) {
        try {
            if (paraNames != null && paraValues != null && paraNames.length != paraValues.length) {
                throw new DAOException("Length of paramNames array must match length of values array");
            }

            final HibernateTemplate template = this.getHibernateTemplate();
            return (List) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createQuery(hql);
                    prepareQuery(queryObject, template);
                    queryObject.setFirstResult(firstResult);
                    queryObject.setMaxResults(maxResults);
                    if (paraValues != null) {
                        for (int i = 0; i < paraValues.length; i++) {
                            applyNamedParameterToQuery(queryObject, paraNames[i], paraValues[i]);
                        }
                    }
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in find(String, String[], Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.ufo.core.dao.IGenericDAO#list(com.ufo.core.support.Paginator)
     */
    public List<T> list(Paginator paginator) {
        return find("from " + clz.getSimpleName(), paginator);
    }

    /**
     * Generic method to query objects according to hql clause
     * @param hql The hql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<T> find(String hql, Paginator paginator) {
        return find(hql, (Object[]) null, paginator);
    }

    /**
     * Generic method to query objects according to hql clause and a parameter
     * @param hql The hql clause
     * @param para The parameter for the hql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<T> find(String hql, Object para, Paginator paginator) {
        return find(hql, new Object[] { para }, paginator);
    }

    /**
     * Generic method to query objects according to hql clause and parameters
     * @param hql The hql clause
     * @param paras The parameters for the hql clause 
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<T> find(final String hql, final Object[] paras, final Paginator paginator) {
        if (paginator == null) {
            return this.find(hql, paras);
        }
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (List<T>) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    if (paginator.getTotalRecords() == null
                            && (paginator.getStartIndex() != 0 || paginator.getPageSize() != Integer.MAX_VALUE)) {
                        Query countQuery = session.createQuery(prepareCountHql(hql));
                        applyParametersToQuery(countQuery, paras);
                        Object totalRecords = countQuery.uniqueResult();
                        paginator.setTotalRecords(Integer.parseInt(totalRecords.toString()));
                    }

                    String sort = getPaginatorFullSort(paginator);
                    Query queryObject;
                    if (StringUtils.isNotBlank(sort)) {
                        String orderHql = hql + " order by " + sort + " " + StringUtils.trimToEmpty(paginator.getDir());
                        queryObject = session.createQuery(orderHql);
                    } else {
                        queryObject = session.createQuery(hql);
                    }
                    prepareQuery(queryObject, template);
                    queryObject.setFirstResult(paginator.getStartIndex());
                    queryObject.setMaxResults(paginator.getPageSize());
                    applyParametersToQuery(queryObject, paras);
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in find(String, Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    private String getPaginatorFullSort(Paginator paginator) {
        if (StringUtils.isBlank(paginator.getSort())) {
            return null;
        }
        if (StringUtils.isNotBlank(paginator.getAlias())) {
            return paginator.getAlias() + "." + paginator.getSort();
        }
        return paginator.getSort();
    }

    /**
     * Generic method to query objects according to hql clause and a parameter
     * @param hql The hql clause
     * @param paraName The parameter name for the hql clause
     * @param paraValue The parameter value for the hql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<T> find(String hql, String paraName, Object paraValue, Paginator paginator) {
        return find(hql, new String[] { paraName }, new Object[] { paraValue }, paginator);
    }

    /**
     * Generic method to query objects according to hql clause and parameters
     * @param hql The hql clause
     * @param paraNames The parameter names for the hql clause
     * @param paraValues The parameter values for the hql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<T> find(final String hql, final String[] paraNames, final Object[] paraValues, final Paginator paginator) {
        try {
            if (paraNames != null && paraValues != null && paraNames.length != paraValues.length) {
                throw new DAOException("Length of paramNames array must match length of values array");
            }

            if (paginator == null) {
                return this.find(hql, paraNames, paraValues);
            }

            final HibernateTemplate template = this.getHibernateTemplate();
            return (List<T>) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    if (paginator.getTotalRecords() == null
                            && (paginator.getStartIndex() != 0 || paginator.getPageSize() != Integer.MAX_VALUE)) {
                        Query countQuery = session.createQuery(prepareCountHql(hql));
                        applyNamedParametersToQuery(countQuery, paraNames, paraValues);
                        Object totalRecords = countQuery.uniqueResult();
                        paginator.setTotalRecords(Integer.parseInt(totalRecords.toString()));
                    }

                    String sort = getPaginatorFullSort(paginator);
                    Query queryObject;
                    if (StringUtils.isNotBlank(sort)) {
                        String orderHql = hql + " order by " + sort + " " + StringUtils.trimToEmpty(paginator.getDir());
                        queryObject = session.createQuery(orderHql);
                    } else {
                        queryObject = session.createQuery(hql);
                    }
                    prepareQuery(queryObject, template);
                    queryObject.setFirstResult(paginator.getStartIndex());
                    queryObject.setMaxResults(paginator.getPageSize());
                    applyNamedParametersToQuery(queryObject, paraNames, paraValues);
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in find(String, String[], Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "find" }, "Error in find", e);
        }
    }

    /**
     * Generic method to query a single object according to hql clause
     * @param hql The hql clause
     * @param paras The parameters for the hql clause 
     * @return The single object
     */
    public Object uniqueFind(String hql) {
        return uniqueFind(hql, (Object[]) null);
    }

    /**
     * Generic method to query a single object according to hql clause and parameters
     * @param hql The hql clause
     * @param paraValue The parameters for the hql clause 
     * @return The single object
     */
    public Object uniqueFind(String hql, Object paraValue) {
        return uniqueFind(hql, new Object[] { paraValue });
    }

    /**
     * Generic method to query a single object according to hql clause and parameters
     * @param hql The hql clause
     * @param paraValues The parameters for the hql clause 
     * @param clzM the returned single object type
     * @return The single object
     */
    public Object uniqueFind(final String hql, final Object[] paraValues) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createQuery(hql);
                    prepareQuery(queryObject, template);
                    applyParametersToQuery(queryObject, paraValues);
                    return queryObject.uniqueResult();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in uniqueFind(String, Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "uniqueFind" }, "Error in uniqueFind", e);
        }
    }

    /**
     * Generic method to query a single object according to hql clause and parameters
     * @param hql The hql clause
     * @param paraName the name of parameter for the hql clause
     * @param paraValue The parameter for the hql clause 
     * @return The single object
     */
    public Object uniqueFind(String hql, String paraName, Object paraValue) {
        return uniqueFind(hql, new String[] { paraName }, new Object[] { paraValue });
    }

    /**
     * Generic method to query a single object according to hql clause and parameters
     * @param hql The hql clause
     * @param paraNames the names of parameters for the hql clause
     * @param paraValues The parameters for the hql clause 
     * @return The single object
     */
    public Object uniqueFind(final String hql, final String[] paraNames, final Object[] paraValues) {
        try {
            if (paraNames != null && paraValues != null && paraNames.length != paraValues.length) {
                throw new DAOException("Length of paramNames array must match length of values array");
            }

            final HibernateTemplate template = this.getHibernateTemplate();
            return template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createQuery(hql);
                    prepareQuery(queryObject, template);
                    applyNamedParametersToQuery(queryObject, paraNames, paraValues);
                    return queryObject.uniqueResult();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in uniqueFind(String, String[], Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "uniqueFind" }, "Error in uniqueFind", e);
        }
    }

    /**
     * Generic method to execute update/delete using hql clause and parameters
     * @param hql The hql clause
     * @param paraValue The parameter for the hql clause 
     * @return The number of affected records 
     */
    public int update(String hql, Object paraValue) {
        return update(hql, new Object[] { paraValue });
    }

    /**
     * Generic method to execute update/delete using hql clause and parameters
     * @param hql The hql clause
     * @param paraValues The parameters for the hql clause 
     * @return The number of affected records 
     */
    public int update(final String hql, final Object[] paraValues) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (Integer) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createQuery(hql);
                    prepareQuery(queryObject, template);
                    applyParametersToQuery(queryObject, paraValues);
                    return queryObject.executeUpdate();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in update(String, Object[]).", e);
            throw new DAOException("errors.dao", new Object[] { "update" }, "Error in update", e);
        }
    }

    /**
     * Generic method to execute update/delete using hql clause and parameters
     * @param hql The hql clause
     * @param paraName The name of parameter for the hql clause
     * @param paraValue The parameters for the hql clause 
     * @return The number of affected records 
     */
    public int update(String hql, String paraName, Object paraValue) {
        return update(hql, new String[] { paraName }, new Object[] { paraValue });
    }

    /**
     * Generic method to execute update/delete using hql clause and parameters
     * @param hql The hql clause
     * @param paraNames The names of parameters for the hql clause
     * @param paraValues The parameters for the hql clause 
     * @return The number of affected records 
     */
    public int update(final String hql, final String[] paraNames, final Object[] paraValues) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (Integer) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createQuery(hql);
                    prepareQuery(queryObject, template);
                    applyNamedParametersToQuery(queryObject, paraNames, paraValues);
                    return queryObject.executeUpdate();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in update(String, String[], Object[]).", e);
            throw new DAOException("errors.dao", new Object[] { "update" }, "Error in update", e);
        }
    }

    protected String prepareCountHql(String hql) {
        String lowerHql = hql.toLowerCase();
        boolean special = false;
        if (lowerHql.startsWith("select")) {
            int index = lowerHql.indexOf(" from ");
            if (index > 0) {
                if (lowerHql.indexOf(" distinct") > 0) {
                    hql = "select count(" + hql.substring(7, index) + ")" + hql.substring(index);
                    special = true;
                } else {
                    hql = hql.substring(index);
                }
            }
        }
        if (!special) {
            hql = ("select count(*) " + hql);
        }

        if (hql.indexOf("group by") > 0) {
            return "select count(*) from (" + hql + ") as r";
        } else {
            return hql;
        }
    }

    /**
     * Prepare the given Query object, applying cache settings and/or
     * a transaction timeout.
     * @param queryObject the Query object to prepare
     * @see #setCacheQueries
     * @see #setQueryCacheRegion
     * @see SessionFactoryUtils#applyTransactionTimeout
     */
    protected void prepareQuery(Query queryObject, HibernateTemplate template) {
        if (template.isCacheQueries()) {
            queryObject.setCacheable(true);
            if (template.getQueryCacheRegion() != null) {
                queryObject.setCacheRegion(template.getQueryCacheRegion());
            }
        }
        if (template.getFetchSize() > 0) {
            queryObject.setFetchSize(template.getFetchSize());
        }

        SessionFactoryUtils.applyTransactionTimeout(queryObject, getSessionFactory());
    }

    /**
     * Apply the given name parameter to the given Query object.
     * @param queryObject the Query object
     * @param paraName the name of the parameter
     * @param value the value of the parameter
     * @throws HibernateException if thrown by the Query object
     */
    protected void applyNamedParameterToQuery(Query queryObject, String paraName, Object value)
            throws HibernateException {

        if (value instanceof Collection) {
            queryObject.setParameterList(paraName, (Collection) value);
        } else if (value instanceof Object[]) {
            queryObject.setParameterList(paraName, (Object[]) value);
        } else {
            queryObject.setParameter(paraName, value);
        }
    }

    /**
     * Apply the given named parameter values to the given Query object.
     * @param queryObject
     * @param paraNames
     * @param paraValues
     */
    protected void applyNamedParametersToQuery(Query queryObject, String[] paraNames, Object[] paraValues) {
        if (paraNames != null && paraValues != null) {
            for (int i = 0; i < paraNames.length; i++) {
                applyNamedParameterToQuery(queryObject, paraNames[i], paraValues[i]);
            }
        }
    }

    /**
     * Apply the given parameter values to the given Query object.
     * @param queryObject
     * @param paraValues
     */
    protected void applyParametersToQuery(Query queryObject, Object[] paraValues) {
        if (paraValues != null) {
            for (int i = 0; i < paraValues.length; i++) {
                queryObject.setParameter(i, paraValues[i]);
            }
        }
    }

    /**
     * Generic method to query objects according to sql clause
     * @param sql The sql clause
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql) {
        return sqlFind(sql, null, 0, Integer.MAX_VALUE);
    }

    /**
     * Generic method to query objects according to sql clause and a parameter
     * @param sql The sql clause
     * @param para The parameter for the sql clause
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, Object para) {
        return sqlFind(sql, para, 0, Integer.MAX_VALUE);
    }

    /**
     * Generic method to query objects according to sql clause and parameters
     * @param sql The sql clause
     * @param paras The parameters for the sql clause 
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, Object[] paras) {
        return sqlFind(sql, paras, 0, Integer.MAX_VALUE);
    }

    /**
     * Generic method to query objects according to sql clause and a parameter
     * @param sql The sql clause
     * @param paraName The parameter name for the sql clause
     * @param paraValue The parameter value for the sql clause
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, String paraName, Object paraValue) {
        return sqlFind(sql, paraName, paraValue, 0, Integer.MAX_VALUE);
    }

    /**
     * Generic method to query objects according to sql clause and parameters
     * @param sql The sql clause
     * @param paraNames The parameter names for the sql clause
     * @param paraValues The parameter values for the sql clause
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, String[] paraNames, Object[] paraValues) {
        return sqlFind(sql, paraNames, paraValues, 0, Integer.MAX_VALUE);
    }

    /**
     * Generic method to query objects according to sql clause
     * @param sql The sql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, int firstResult, int maxResults) {
        return sqlFind(sql, null, firstResult, maxResults);
    }

    /**
     * Generic method to query objects according to sql clause and a parameter
     * @param sql The sql clause
     * @param para The parameter for the sql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, Object para, int firstResult, int maxResults) {
        return sqlFind(sql, new Object[] { para }, firstResult, maxResults);
    }

    /**
     * Generic method to query objects according to sql clause and parameters
     * @param sql The sql clause
     * @param paras The parameters for the sql clause 
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(final String sql, final Object[] paras, final int firstResult, final int maxResults) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (List<Object[]>) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createSQLQuery(sql);
                    prepareQuery(queryObject, template);
                    if (!"{call".equals(sql.trim().substring(0, 5).toLowerCase())) {
                        queryObject.setFirstResult(firstResult);
                        queryObject.setMaxResults(maxResults);
                    }
                    if (paras != null) {
                        for (int i = 0; i < paras.length; i++) {
                            queryObject.setParameter(i, paras[i]);
                        }
                    }
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlFind(String, Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlFind" }, "Error in sqlFind", e);
        }
    }

    /**
     * Generic method to query objects according to sql clause and a parameter
     * @param sql The sql clause
     * @param paraName The parameter name for the sql clause
     * @param paraValue The parameter value for the sql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, String paraName, Object paraValue, int firstResult, int maxResults) {
        return sqlFind(sql, new String[] { paraName }, new Object[] { paraValue }, firstResult, maxResults);
    }

    /**
     * Generic method to query objects according to sql clause and parameters
     * @param sql The sql clause
     * @param paraNames The parameter names for the sql clause
     * @param paraValues The parameter values for the sql clause
     * @param firstResult the index of the first record in returned list.  
     * @param maxResults the max size of records in returned list.
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(final String sql, final String[] paraNames, final Object[] paraValues,
            final int firstResult, final int maxResults) {
        try {
            if (paraNames != null && paraValues != null && paraNames.length != paraValues.length) {
                throw new DAOException("Length of paramNames array must match length of values array");
            }

            final HibernateTemplate template = this.getHibernateTemplate();
            return (List<Object[]>) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createSQLQuery(sql);
                    prepareQuery(queryObject, template);
                    if (!"{call".equals(sql.trim().substring(0, 5).toLowerCase())) {
                        queryObject.setFirstResult(firstResult);
                        queryObject.setMaxResults(maxResults);
                    }
                    if (paraValues != null) {
                        for (int i = 0; i < paraValues.length; i++) {
                            applyNamedParameterToQuery(queryObject, paraNames[i], paraValues[i]);
                        }
                    }
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlFind(String, String[], Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlFind" }, "Error in sqlFind", e);
        }
    }

    /**
     * Generic method to query objects according to sql clause
     * @param sql The sql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, Paginator paginator) {
        return sqlFind(sql, (Object[]) null, paginator);
    }

    /**
     * Generic method to query objects according to sql clause and a parameter
     * @param sql The sql clause
     * @param para The parameter for the sql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, Object para, Paginator paginator) {
        return sqlFind(sql, new Object[] { para }, paginator);
    }

    /**
     * Generic method to query objects according to sql clause and parameters
     * @param sql The sql clause
     * @param paras The parameters for the sql clause 
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(final String sql, final Object[] paras, final Paginator paginator) {
        if (paginator == null) {
            return sqlFind(sql, paras);
        }
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (List<Object[]>) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    if (paginator.getTotalRecords() == null
                            && (paginator.getStartIndex() != 0 || paginator.getPageSize() != Integer.MAX_VALUE)) {
                        Query countQuery = session.createSQLQuery(prepareCountHql(sql));
                        applyParametersToQuery(countQuery, paras);
                        Object totalRecords = countQuery.uniqueResult();
                        paginator.setTotalRecords(Integer.parseInt(totalRecords.toString()));
                    }

                    String sort = getPaginatorFullSort(paginator);
                    Query queryObject;
                    if (StringUtils.isNotBlank(sort)) {
                        String orderHql = sql + " order by " + sort + " " + StringUtils.trimToEmpty(paginator.getDir());
                        queryObject = session.createSQLQuery(orderHql);
                    } else {
                        queryObject = session.createSQLQuery(sql);
                    }
                    prepareQuery(queryObject, template);
                    if (!"{call".equals(sql.trim().substring(0, 5).toLowerCase())) {
                        queryObject.setFirstResult(paginator.getStartIndex());
                        queryObject.setMaxResults(paginator.getPageSize());
                    }
                    applyParametersToQuery(queryObject, paras);
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlFind(String, Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlFind" }, "Error in sqlFind", e);
        }
    }

    /**
     * Generic method to query objects according to sql clause and a parameter
     * @param sql The sql clause
     * @param paraName The parameter name for the sql clause
     * @param paraValue The parameter value for the sql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(String sql, String paraName, Object paraValue, Paginator paginator) {
        return sqlFind(sql, new String[] { paraName }, new Object[] { paraValue }, paginator);
    }

    /**
     * Generic method to query objects according to sql clause and parameters
     * @param sql The sql clause
     * @param paraNames The parameter names for the sql clause
     * @param paraValues The parameter values for the sql clause
     * @param paginator pagination information
     * @return List of queried objects
     */
    public List<Object[]> sqlFind(final String sql, final String[] paraNames, final Object[] paraValues,
            final Paginator paginator) {
        try {
            if (paraNames != null && paraValues != null && paraNames.length != paraValues.length) {
                throw new DAOException("Length of paramNames array must match length of values array");
            }
            if (paginator == null) {
                return sqlFind(sql, paraNames, paraValues);
            }

            final HibernateTemplate template = this.getHibernateTemplate();
            return (List<Object[]>) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    if (paginator.getTotalRecords() == null
                            && (paginator.getStartIndex() != 0 || paginator.getPageSize() != Integer.MAX_VALUE)) {
                        Query countQuery = session.createSQLQuery(prepareCountHql(sql));
                        applyNamedParametersToQuery(countQuery, paraNames, paraValues);
                        Object totalRecords = countQuery.uniqueResult();
                        paginator.setTotalRecords(Integer.parseInt(totalRecords.toString()));
                    }

                    String sort = getPaginatorFullSort(paginator);
                    Query queryObject;
                    if (StringUtils.isNotBlank(sort)) {
                        String orderHql = sql + " order by " + sort + " " + StringUtils.trimToEmpty(paginator.getDir());
                        queryObject = session.createSQLQuery(orderHql);
                    } else {
                        queryObject = session.createSQLQuery(sql);
                    }
                    prepareQuery(queryObject, template);
                    if (!"{call".equals(sql.trim().substring(0, 5).toLowerCase())) {
                        queryObject.setFirstResult(paginator.getStartIndex());
                        queryObject.setMaxResults(paginator.getPageSize());
                    }
                    applyNamedParametersToQuery(queryObject, paraNames, paraValues);
                    return queryObject.list();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlFind(String, String[], Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlFind" }, "Error in sqlFind", e);
        }
    }

    /**
     * Generic method to query a single object according to sql clause
     * @param sql The sql clause
     * @param paras The parameters for the sql clause 
     * @return The single object
     */
    public Object sqlUniqueFind(String sql) {
        return sqlUniqueFind(sql, (Object[]) null);
    }

    /**
     * Generic method to query a single object according to sql clause and parameters
     * @param sql The sql clause
     * @param paraValue The parameters for the sql clause 
     * @return The single object
     */
    public Object sqlUniqueFind(String sql, Object paraValue) {
        return sqlUniqueFind(sql, new Object[] { paraValue });
    }

    /**
     * Generic method to query a single object according to sql clause and parameters
     * @param sql The sql clause
     * @param paraValues The parameters for the sql clause 
     * @param clzM the returned single object type
     * @return The single object
     */
    public Object sqlUniqueFind(final String sql, final Object[] paraValues) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createSQLQuery(sql);
                    prepareQuery(queryObject, template);
                    applyParametersToQuery(queryObject, paraValues);
                    return queryObject.uniqueResult();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlUniqueFind(String, Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlUniqueFind" }, "Error in sqlUniqueFind", e);
        }
    }

    /**
     * Generic method to query a single object according to sql clause and parameters
     * @param sql The sql clause
     * @param paraName the name of parameter for the sql clause
     * @param paraValue The parameter for the sql clause 
     * @return The single object
     */
    public Object sqlUniqueFind(String sql, String paraName, Object paraValue) {
        return sqlUniqueFind(sql, new String[] { paraName }, new Object[] { paraValue });
    }

    /**
     * Generic method to query a single object according to sql clause and parameters
     * @param sql The sql clause
     * @param paraNames the names of parameters for the sql clause
     * @param paraValues The parameters for the sql clause 
     * @return The single object
     */
    public Object sqlUniqueFind(final String sql, final String[] paraNames, final Object[] paraValues) {
        try {
            if (paraNames != null && paraValues != null && paraNames.length != paraValues.length) {
                throw new DAOException("Length of paramNames array must match length of values array");
            }

            final HibernateTemplate template = this.getHibernateTemplate();
            return template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createSQLQuery(sql);
                    prepareQuery(queryObject, template);
                    applyNamedParametersToQuery(queryObject, paraNames, paraValues);
                    return queryObject.uniqueResult();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlUniqueFind(String, String[], Object[], int, int).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlUniqueFind" }, "Error in sqlUniqueFind", e);
        }
    }

    /**
     * Generic method to execute update/delete using sql clause and parameters
     * @param sql The sql clause
     * @param paraValue The parameter for the sql clause 
     * @return The number of affected records 
     */
    public int sqlUpdate(String sql, Object paraValue) {
        return sqlUpdate(sql, new Object[] { paraValue });
    }

    /**
     * Generic method to execute update/delete using sql clause and parameters
     * @param sql The sql clause
     * @param paraValues The parameters for the sql clause 
     * @return The number of affected records 
     */
    public int sqlUpdate(final String sql, final Object[] paraValues) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (Integer) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createSQLQuery(sql);
                    prepareQuery(queryObject, template);
                    applyParametersToQuery(queryObject, paraValues);
                    return queryObject.executeUpdate();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlUpdate(String, Object[]).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlUpdate" }, "Error in sqlUpdate", e);
        }
    }

    /**
     * Generic method to execute update/delete using sql clause and parameters
     * @param sql The sql clause
     * @param paraName The name of parameter for the sql clause
     * @param paraValue The parameters for the sql clause 
     * @return The number of affected records 
     */
    public int sqlUpdate(String sql, String paraName, Object paraValue) {
        return sqlUpdate(sql, new String[] { paraName }, new Object[] { paraValue });
    }

    /**
     * Generic method to execute update/delete using sql clause and parameters
     * @param sql The sql clause
     * @param paraNames The names of parameters for the sql clause
     * @param paraValues The parameters for the sql clause 
     * @return The number of affected records 
     */
    public int sqlUpdate(final String sql, final String[] paraNames, final Object[] paraValues) {
        try {
            final HibernateTemplate template = this.getHibernateTemplate();
            return (Integer) template.executeWithNativeSession(new HibernateCallback() {
                public Object doInHibernate(Session session) throws HibernateException {
                    Query queryObject = session.createSQLQuery(sql);
                    prepareQuery(queryObject, template);
                    applyNamedParametersToQuery(queryObject, paraNames, paraValues);
                    return queryObject.executeUpdate();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error in sqlUpdate(String, String[], Object[]).", e);
            throw new DAOException("errors.dao", new Object[] { "sqlUpdate" }, "Error in sqlUpdate", e);
        }
    }
}
