package com.ufo.core.service;

import com.ufo.core.annotation.MetaData;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.pagination.GroupPropertyFilter;
import com.ufo.core.pagination.PropertyFilter;
import org.hibernate.Criteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IBaseSpringDataService<T extends Persistable<? extends Serializable>, ID extends Serializable> {

    /** 子类设置具体的DAO对象实例 */
    public BaseDao<T, ID> getEntityDao();

    /**
     * 创建数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     * 
     * @param entity
     *            待创建数据对象
     */
    public void preInsert(T entity);
    
    /**
     * 更新数据保存数据之前额外操作回调方法 默认为空逻辑，子类根据需要覆写添加逻辑即可
     * 
     * @param entity
     *            待更新数据对象
     */
    public void preUpdate(T entity);

    /**
     * 数据保存操作
     * 
     * @param entity
     * @return
     */
    public T save(T entity);

    /**
     * 批量数据保存操作 其实现只是简单循环集合每个元素调用 {@link #save(Persistable)}
     * 因此并无实际的Batch批量处理，如果需要数据库底层批量支持请自行实现
     * 
     * @param entities
     *            待批量操作数据集合
     * @return
     */
    public List<T> save(Iterable<T> entities);

    /**
     * 基于主键查询单一数据对象
     * 
     * @param id
     * @return
     */
    public T findOne(ID id);

    /**
     * 基于主键查询单一数据对象
     * 
     * @param id 主键
     * @param initLazyPropertyNames 需要预先初始化的lazy集合属性名称
     * @return
     */
    public T findDetachedOne(ID id, String... initLazyPropertyNames);
    
    /**
     * 查询所有数据对象
     * 
     * @return
     */
    public Iterable<T> findAll();

    /**
     * 基于主键集合查询集合数据对象
     * 
     * @param ids 主键集合
     * @return
     */
    public List<T> findAll(final ID... ids);

    /**
     * 数据删除操作
     *
     * @param entity
     *            待操作数据
     */
    public void delete(T entity);

    /**
     * 数据删除操作
     * 
     * @param entity
     *            待操作数据
     */
    public void delete(T entity, boolean isClear);

    /**
     * 批量数据删除操作 其实现只是简单循环集合每个元素调用 {@link #delete(Persistable)}
     * 因此并无实际的Batch批量处理，如果需要数据库底层批量支持请自行实现
     * 
     * @param entities
     *            待批量操作数据集合
     * @return
     */
    public void delete(Iterable<T> entities);

    /**
     * 根据泛型对象属性和值查询唯一对象
     * 
     * @param property 属性名，即对象中数量变量名称
     * @param value 参数值
     * @return 未查询到返回null，如果查询到多条数据则抛出异常
     */
    public T findByProperty(final String property, final Object value);

    /**
     * 根据泛型对象属性和值查询唯一对象
     * 
     * @param property 属性名，即对象中数量变量名称
     * @param value 参数值
     * @return 未查询到返回null，如果查询到多条数据则返回第一条
     */
    public T findFirstByProperty(final String property, final Object value);

    /**
     * 通用的对象属性和值查询接口，根据泛型参数确定返回类型数据
     * 
     * @param baseDao
     *            泛型参数对象DAO接口
     * @param property
     *            属性名，即对象中数量变量名称
     * @param value
     *            参数值
     * @return 未查询到返回null，如果查询到多条数据则抛出异常
     */
    public <X> X findByProperty(BaseDao<X, ID> baseDao, final String property, final Object value);

    /**
     * 单一条件对象查询数据集合
     * 
     * @param propertyFilter
     * @return
     */
    public List<T> findByFilter(PropertyFilter propertyFilter);

    /**
     * 基于动态组合条件对象查询数据集合
     * 
     * @param groupPropertyFilter
     * @return
     */
    public List<T> findByFilters(GroupPropertyFilter groupPropertyFilter);

    /**
     * 基于动态组合条件对象和排序定义查询数据集合
     * 
     * @param groupPropertyFilter
     * @param sort
     * @return
     */
    public List<T> findByFilters(GroupPropertyFilter groupPropertyFilter, Sort sort);

    @SuppressWarnings("rawtypes")
    public <X extends Persistable> List<X> findByFilters(Class<X> clazz, GroupPropertyFilter groupPropertyFilter,
            Sort sort);

    /**
    * 基于动态组合条件对象和排序定义，限制查询数查询数据集合
    * 主要用于Autocomplete这样的查询避免返回太多数据
    * @param groupPropertyFilter
    * @param sort
    * @return
    */
    public List<T> findByFilters(GroupPropertyFilter groupPropertyFilter, Sort sort, int limit);

    /**
     * 基于动态组合条件对象和分页(含排序)对象查询数据集合
     * 
     * @param groupPropertyFilter
     * @param pageable
     * @return
     */
    public Page<T> findByPage(GroupPropertyFilter groupPropertyFilter, Pageable pageable);

    public String toSql(Criteria criteria);

    /**
     * 分组聚合统计，常用于类似按账期时间段统计商品销售利润，按会计科目总帐统计等
     * 
     * @param clazz  ROOT实体类型
     * @param groupFilter 过滤参数对象
     * @param pageable 分页排序参数对象，TODO：目前有个限制未实现总记录数处理，直接返回一个固定大数字
     * @param properties 属性集合，判断规则：属性名称包含"("则标识为聚合属性，其余为分组属性 
     * 属性语法规则：sum = + , diff = - , prod = * , quot = / , case(condition,when,else)
     * 示例：
     *     sum(amount)
     *     sum(diff(amount,costAmount))
     *     min(case(equal(amount,0),-1,quot(diff(amount,costAmount),amount)))
     *     case(equal(sum(amount),0),-1,quot(sum(diff(amount,costAmount)),sum(amount)))
     * @return Map结构的集合分页对象
     */
    @SuppressWarnings("rawtypes")
    public Page<Map<String, Object>> findByGroupAggregate(Class clazz, GroupPropertyFilter groupFilter,
            Pageable pageable, String... properties);

    /**
     * 基于当前泛型实体对象类型，调用分组统计接口
     * @param groupFilter
     * @param pageable
     * @param properties
     * @return
     */
    public Page<Map<String, Object>> findByGroupAggregate(GroupPropertyFilter groupFilter, Pageable pageable,
            String... properties);

    /**
     * 基于Native SQL和分页(不含排序，排序直接在native sql中定义)对象查询数据集合
     * 
     * @param pageable 分页(不含排序，排序直接在native sql中定义)对象
     * @param sql Native SQL(自行组装好动态条件和排序的原生SQL语句，不含order by部分)
     * @return Map结构的集合分页对象
     */
    @SuppressWarnings("rawtypes")
    public Page<Map> findByPageNativeSQL(Pageable pageable, String sql);

    /**
     * 基于Native SQL和分页(不含排序，排序直接在native sql中定义)对象查询数据集合
     * 
     * @param pageable 分页(不含排序，排序直接在native sql中定义)对象
     * @param sql Native SQL(自行组装好动态条件和排序的原生SQL语句，不含order by部分)
     * @param orderby order by部分
     * @return Map结构的集合分页对象
     */
    @SuppressWarnings("rawtypes")
    public Page<Map> findByPageNativeSQL(Pageable pageable, String sql, String orderby);

    /**
     * 基于JPA通用的查询条件count记录数据
     * 
     * @param spec
     * @return
     */
    public long count(Specification<T> spec);

    @SuppressWarnings("rawtypes")
    public Predicate buildPredicatesFromFilters(GroupPropertyFilter groupPropertyFilter, Root root,
            CriteriaQuery<?> query, CriteriaBuilder builder, Boolean having);
    
    class GroupAggregateProperty {
        @MetaData(value = "字面属性", comments = "最后用于前端JSON输出的key")
        private String label;
        @MetaData(value = "JPA表达式", comments = "传入JPA CriteriaBuilder组装的内容")
        private String name;
        @MetaData(value = "JPA表达式alias", comments = "用于获取聚合值的别名")
        private String alias;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

    }
    
    /**
     * 子类额外追加过滤限制条件的入口方法，一般基于当前登录用户强制追加过滤条件
     * 
     * @param root
     * @param query
     * @param builder
     */
    List<Predicate> appendPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder);

    /**
     * 供子类调用的关联对象关联关系操作辅助方法
     * 
     * @param id
     *            当前关联主对象主键，如User对象主键
     * @param r2EntityIds
     *            关联对象的主键集合，如用户关联角色的Role对象集合的主键
     * @param r2PropertyName
     *            主对象中关联集合对象属性的名称，如User对象中定义的userR2Roles属性名
     * @param r2EntityPropertyName
     *            被关联对象在R2关联对象定义中的属性名称，如UserR2Role中定义的role属性名
     * @param op
     *            关联操作类型，如add、del等， @see #R2OperationEnum
     */
    public void updateRelatedR2s(ID id, Collection<? extends Serializable> r2EntityIds, String r2PropertyName,
            String r2EntityPropertyName, R2OperationEnum op);

    /**
     * 供子类调用的关联对象关联关系操作辅助方法
     * 
     * @param id
     *            当前关联主对象主键，如User对象主键
     * @param r2EntityIds
     *            关联对象的主键集合，如用户关联角色的Role对象集合的主键
     * @param r2PropertyName
     *            主对象中关联集合对象属性的名称，如User对象中定义的userR2Roles属性名
     * @param r2EntityPropertyName
     *            被关联对象在R2关联对象定义中的属性名称，如UserR2Role中定义的role属性名
     */
    public void updateRelatedR2s(ID id, Serializable[] r2EntityIds, String r2PropertyName,
            String r2EntityPropertyName);

    @SuppressWarnings("rawtypes")
    public Object findEntity(Class entityClass, Serializable id);

    public void detachEntity(Object entity);

    /**
     * 基于Native SQL返回Map结构集合数据
     */
    public List<Map<String, Object>> queryForMapDatasByNativeSQL(String sql);
}
