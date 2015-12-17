package com.ufo.core.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ufo.core.entity.OperatorEntity;
import com.ufo.core.entity.UndeleteEntity;
import com.ufo.core.utils.CollectionUtils;
import com.ufo.core.utils.ExtStringUtils;
import com.ufo.core.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ufo.config.sys.entity.Manager;
import com.ufo.core.annotation.MetaData;
import com.ufo.core.common.BaseRuntimeException;
import com.ufo.core.common.ProcResult;
import com.ufo.core.entity.BaseEntity;
import com.ufo.core.entity.PersistableEntity;
import com.ufo.core.pagination.GroupPropertyFilter;
import com.ufo.core.pagination.PropertyFilter;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.utils.JsonUtils;
import com.ufo.core.web.json.DateJsonSerializer;
import com.ufo.core.web.json.DateTimeJsonSerializer;

public abstract class PersistableController<T extends PersistableEntity<ID>, ID extends Serializable> extends AbstractBaseController {

    private final Logger logger = LoggerFactory.getLogger(PersistableController.class);
    //返回首页路径 
    private static final String PATH_INDEX = "index.htm";

    /** Autocomplete组件传递的查询参数的名称 */
    protected static final String PARAM_NAME_FOR_AUTOCOMPLETE = "term";

    /** 请求URL可提供此参数指定转向特定JSP页面，如有相同处理方法返回相同数据，但是不同业务功能需要按照不同页面显示则可以指定此参数转向特定显示JSP页面*/
    protected static final String PARAM_NAME_FOR_FORWARD_TO = "_to_";

    /** 分页查询方法特定的数据处理格式标识参数，默认标识返回查询JSON数据，可指定如xls标识导出对应的（不分页）查询数据 */
    protected static final String PARAM_NAME_FOR_EXPORT_FORMAT = "_format_";

    /** 泛型对应的Class定义 */
    protected Class<T> entityClass;

    /** 泛型对应的Class定义 */
    protected Class<ID> entityIdClass;

    // 取服务
    protected abstract IBaseSpringDataService<T, ID> getEntityService();
    
    /**
     * 初始化构造方法，计算相关泛型对象
     */
    @SuppressWarnings("unchecked")
    public PersistableController() {
        super();
        // 通过反射取得Entity的Class.
        try {
            Object genericClz = getClass().getGenericSuperclass();
            if (genericClz instanceof ParameterizedType) {
                entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
                entityIdClass = (Class<ID>) ((ParameterizedType) getClass().getGenericSuperclass())
                        .getActualTypeArguments()[1];
            }
        } catch (Exception e) {
            throw new BaseRuntimeException(e.getMessage(), e);
        }
    }

    /** 
     * 跳转首页 ,浏览器显示跳转后的地址
    * @return
    */
    protected String redirect() {
        return redirect(PATH_INDEX);
    }

    /** 
     * 跳转到指定页 ,浏览器显示跳转后的地址
    * @param path
    * @return
    */
    protected String redirect(String path) {
        String url = converUrl(path);
        if (logger.isDebugEnabled()) {
            logger.debug("redirect url is " + url);
        }
        return "redirect:" + url;
    }

    /** 
     * 跳转到首页,浏览器显示原地址
    * @return
    */
    protected String forward() {
        return forward(PATH_INDEX);
    }

    /** 跳转到指定页 ,浏览器显示原地址
    * @param path
    * @return
    */
    protected String forward(String path) {
        String url = converUrl(path);
        if (logger.isDebugEnabled()) {
            logger.debug("forward url is " + url);
        }
        return "forward:" + url;
    }

    /** 
     * 将相对模块路径视图转化为相对项目路径视图
    * @param path
    * @return
    */
    protected String toView(String path) {
        String _path = converPath(path);
        if (logger.isDebugEnabled()) {
            logger.debug("jsp path is " + _path);
        }
        return _path;

    }

    protected ProcResult success(Object obj) {
        return DWZsuccess(obj);
    }

    protected ProcResult success() {
        return DWZsuccess(null);
    }

    protected ProcResult failed(String message) {
        return DWZFailed(message);
    }

    protected ProcResult failed(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return DWZFailed(ex);
    }

    protected T detail(ID id) {
        return getEntityService().findOne(id);
    }

    protected Object save(T t) {
        return restSuccess(getEntityService().save(t), "保存成功");
    }

//    protected Object delete(T t) {
//        if (t instanceof UndeleteEntity){
//            ((UndeleteEntity)t).setIsvalid(UndeleteEntity.ValidTypeEnum.INVALID);
//            getEntityService().save(t);
//        } else {
//            getEntityService().delete(t);
//        }
//        return restSuccess(true, "删除成功");
//    }

    protected Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    protected Object delete(HttpServletRequest request) {
        return delete(request, false);
    }

    protected Object delete(HttpServletRequest request, boolean isClear) {

        //删除失败的id和对应消息以Map结构返回，可用于前端批量显示错误提示和计算表格组件更新删除行项
        Map<ID, String> errorMessageMap = Maps.newLinkedHashMap();

        Collection<T> entities = this.getEntitiesByParameterIds(request);
        //对于批量删除,循环每个对象调用Service接口删除,则各对象删除事务分离
        //这样可以方便某些对象删除失败不影响其他对象删除
        //如果业务逻辑需要确保批量对象删除在同一个事务则请子类覆写调用Service的批量删除接口
        for (T entity : entities) {
            try {
                if (entity instanceof UndeleteEntity && !isClear){
                    ((UndeleteEntity)entity).setDeleted(UndeleteEntity.DeleteTypeEnum.DELETE);

                    if (entity instanceof OperatorEntity){
                        ((OperatorEntity)entity).setDeleteOperator(operation());
                        ((OperatorEntity)entity).setDeleteTime(getCurrentTime());
                    }
                    getEntityService().save(entity);
                } else {
                    getEntityService().delete(entity, true);
                }
            } catch (Exception e) {
                logger.warn("entity delete failure", e);
                errorMessageMap.put(entity.getId(), e.getMessage());
            }
        }
        int rejectSize = errorMessageMap.size();
        if (rejectSize == 0) {
            return this.restSuccess(true, "成功删除所选选取记录:" + entities.size() + "条");
        } else {
            if (rejectSize == entities.size()) {
                return this.restFailed("所有选取记录删除操作失败:" + errorMessageMap);
            } else {
                return this.restFailed("删除操作已处理. 成功:" + (entities.size() - rejectSize) + "条"
                        + ",失败:" + rejectSize + "条" + errorMessageMap);
            }
        }

//        if (t instanceof UndeleteEntity){
//            ((UndeleteEntity)t).setIsvalid(UndeleteEntity.ValidTypeEnum.INVALID);
//            getEntityService().save(t);
//        } else {
//            getEntityService().delete(t);
//        }
//        return restSuccess(true, "删除成功");
    }

    // ---------------------------------------------
    // -----------Delete删除数据处理相关逻辑------------
    // ----------------------------------------------
    /**
     *
     * 将ids=123,234,345等格式参数按照逗号切分并转换查询对应的Entity对象集合，方便使用
     * 一般用于如删除等批量操作
     * @return 实体对象集合
     */
    @SuppressWarnings("unchecked")
    protected Collection<T> getEntitiesByParameterIds(HttpServletRequest request) {
        Collection<T> entities = new ArrayList<T>();
        for (String id : getParameterIds(request)) {
            Object realId = null;
            if (String.class.isAssignableFrom(entityIdClass)) {
                realId = id;
            } else if (Integer.class.isAssignableFrom(entityIdClass)) {
                realId = Integer.valueOf(id);
            } else {
                throw new IllegalStateException("Undefine entity ID class: " + entityIdClass);
            }
            T entity = getEntityService().findOne((ID) realId);
            entities.add(entity);
        }
        return entities;
    }

    // --------------------------------------------- 
    // -----------findByPage分页查询处理相关逻辑------------
    // ----------------------------------------------
    /**
     * 分页列表显示数据
     * 为了避免由于权限配置不严格，导致未授权的Controller数据操作访问，父类提供protected基础实现，子类根据需要覆写public然后调用基类方法
     * @return JSON集合数据
     */
    @MetaData(value = "查询")
    protected Object findByPage(HttpServletRequest request, Boolean ...notDeleted) {
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(request);
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, request);
        appendFilterProperty(groupFilter);
        if (notDeleted == null || notDeleted.length == 0){
            // 查询有效
            PropertyFilter filter = new PropertyFilter(PropertyFilter.MatchType.EQ, "deleted", UndeleteEntity.DeleteTypeEnum.UNDELETE);
            groupFilter.forceAnd(filter);
        }

        String foramt = request.getParameter(PARAM_NAME_FOR_EXPORT_FORMAT);
        if ("xls".equalsIgnoreCase(foramt)) {
            exportXlsForGrid(groupFilter, pageable.getSort());
        } else {
            return this.getEntityService().findByPage(groupFilter, pageable);
        }
        return null;
    }
    
    protected Object findByParam(HttpServletRequest request){
    	GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(entityClass, request);
        // 查询有效
        PropertyFilter filter = new PropertyFilter(PropertyFilter.MatchType.EQ, "deleted", UndeleteEntity.DeleteTypeEnum.UNDELETE);
        groupFilter.forceAnd(filter);

    	appendFilterProperty(groupFilter);
    	return this.getEntityService().findByFilters(groupFilter);
    }
    
    /**
     * 如果业务功能支持对分页查询导出Excel组件，子类覆写此方法
     * 基类的findByPage会根据{@link #PARAM_NAME_FOR_EXPORT_FORMAT} 自动回调此方法进行Excel数据导出
     * @param sort 已基于Request组装好的排序对象
     * @param groupFilter 已基于Request组装好高级查询条件的集合对象
     */
    protected void exportXlsForGrid(GroupPropertyFilter groupFilter, Sort sort) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * 子类额外追加过滤限制条件的入口方法，一般基于当前登录用户强制追加过滤条件
     * 注意：凡是基于当前登录用户进行的控制参数，一定不要通过页面请求参数方式传递，存在用户篡改请求数据访问非法数据的风险
     * 因此一定要在Controller层面通过覆写此回调函数或自己的业务方法中强制追加过滤条件
     * @param groupPropertyFilter 已基于Request组装好查询条件的集合对象
     */
    protected void appendFilterProperty(GroupPropertyFilter groupPropertyFilter) {

    }

    protected String inputTabs() {
        return this.toView("inputTabs");
    }

    /** 
     * 通用异常处理,如果是页面,返回500出错而,否则返回dwz JSON格式错误
    * @param ex
    * @param req
    * @param res
    * @throws IOException
    */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HttpServletRequest req, HttpServletResponse res) throws IOException {
        logger.error(ex.getMessage(), ex);
        String requestType = req.getHeader("X-Requested-With");
        String url = req.getRequestURI();
        if ("XMLHttpRequest".equals(requestType) && url.endsWith(".json")) {
            res.setContentType("application/json;charset=UTF-8");
            ProcResult obj = failed(ex);
            res.getOutputStream().write(JsonUtils.toJson(obj).getBytes("UTF-8"));
            return null;
        } else {
            ModelAndView mav = new ModelAndView("500");
            final StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            mav.addObject("msg", ex.getMessage());
            mav.addObject("error", sw.toString());
            return mav;
        }
    }

    private static Map<Class<?>, Map<String, Object>> entityValidationRulesMap = Maps.newHashMap();
    
    /**
     * 支持的转换规则列表：
     * <ul>
     * <li>@Email   email电子邮件格式</li>
     * <li>@Column(nullable=false)   required数据必须</li>
     * </ul> 
     */
    @SuppressWarnings("finally")
    @MetaData(value = "表格数据编辑校验规则")
    public Object buildValidateRules() {
        Map<String, Object> nameRules = entityValidationRulesMap.get(entityClass);
        try {
            if (nameRules == null) {
                nameRules = Maps.newHashMap();
                entityValidationRulesMap.put(entityClass, nameRules);

                Class<?> clazz = entityClass;
                Set<Field> fields = Sets.newHashSet(clazz.getDeclaredFields());
                clazz = clazz.getSuperclass();
                while (!clazz.equals(BaseEntity.class) && !clazz.equals(Object.class)) {
                    fields.addAll(Sets.newHashSet(clazz.getDeclaredFields()));
                    clazz = clazz.getSuperclass();
                }

                for (Field field : fields) {
                    if (Modifier.isStatic(field.getModifiers()) || !Modifier.isPrivate(field.getModifiers())
                            || Collection.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    String name = field.getName();
                    if ("id".equals(name)) {
                        continue;
                    }
                    Map<String, Object> rules = Maps.newHashMap();

                    MetaData metaData = field.getAnnotation(MetaData.class);
                    if (metaData != null) {
                        String tooltips = metaData.tooltips();
                        if (StringUtils.isNotBlank(tooltips)) {
                            rules.put("tooltips", tooltips);
                        }
                    }

                    Method method = MethodUtils.getAccessibleMethod(entityClass, "get" + StringUtils.capitalize(name), null);

                    if (method != null) {
                        Class<?> retType = method.getReturnType();
                        Column column = method.getAnnotation(Column.class);

                        if (column != null) {
                            if (retType != Boolean.class && column.nullable() == false) {
                                rules.put("required", true);
                            }
                            if (column.unique() == true) {
                                rules.put("unique", true);
                            }
                            if (column.updatable() == false) {
                                rules.put("readonly", true);
                            }
                            if (column.length() > 0 && retType == String.class
                                    && method.getAnnotation(Lob.class) == null) {
                                rules.put("maxlength", column.length());
                            }
                        }

                        JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
                        if (joinColumn != null) {
                            if (joinColumn.nullable() == false) {
                                rules.put("required", true);
                            }
                        }

                        if (retType == Date.class) {
                            JsonSerialize jsonSerialize = method.getAnnotation(JsonSerialize.class);
                            if (jsonSerialize != null) {
                                if (DateJsonSerializer.class == jsonSerialize.using()) {
                                    rules.put("date", true);
                                } else if (DateTimeJsonSerializer.class == jsonSerialize.using()) {
                                    rules.put("timestamp", true);
                                }
                            } else {
                                rules.put("date", true);
                            }
                        } else if (retType == BigDecimal.class) {
                            rules.put("number", true);
                        } else if (retType == Integer.class || retType == Long.class) {
                            rules.put("integer", true);
                        }

                        Size size = method.getAnnotation(Size.class);
                        if (size != null) {
                            if (size.min() > 0) {

                            }
                            if (size.max() < Integer.MAX_VALUE) {
                            }
                        }

                        Email email = method.getAnnotation(Email.class);
                        if (email != null) {
                            rules.put("email", true);
                        }

                        Pattern pattern = method.getAnnotation(Pattern.class);
                        if (pattern != null) {
                            rules.put("regex", pattern.regexp());
                        }

                        if (rules.size() > 0) {
                            nameRules.put(name, rules);
                            //如果是实体对象类型，一般表单元素name都定义为entity.id，因此额外追加对应id属性校验规则
                            if (PersistableEntity.class.isAssignableFrom(field.getType())) {
                                nameRules.put(name + ".id", rules);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return this.DWZFailed("系统处理异常");
        } finally {
            return nameRules;
        }
    }

    /** 
     * 操作员
    * @return
    */
    protected Manager operation() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object obj = auth == null ? null : auth.getPrincipal();
        return obj instanceof Manager ? (Manager) obj : null;
    }

    /**
     * 字段值重复性校验
     * 唯一性验证URL示例：id=1&element=masterId&masterId=ABC&additional=referenceId
     * &referenceId=XYZ 处理额外补充参数，有些数据是通过两个字段共同决定唯一性，可以通过additional参数补充提供
     */
    public Boolean checkUnique(HttpServletRequest request) {
        String element = this.getParameter(request, "element");
        Assert.notNull(element);
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();

        String value = request.getParameter(element);
        if (!ExtStringUtils.hasChinese(value)) {
            value = ExtStringUtils.encodeUTF8(value);
        }

        groupPropertyFilter.append(new PropertyFilter(entityClass, "EQ_" + element, value));

        groupPropertyFilter.append(new PropertyFilter(entityClass, "EQ_deleted", "UNDELETE"));

//        // 处理额外补充参数，有些数据是通过两个字段共同决定唯一性，可以通过additional参数补充提供
//        String additionalName = request.getParameter("additional");
//        if (StringUtils.isNotBlank(additionalName)) {
//            String additionalValue = request.getParameter(additionalName);
//            if (!ExtStringUtils.hasChinese(additionalValue)) {
//                additionalValue = ExtStringUtils.encodeUTF8(additionalValue);
//            }
//            groupPropertyFilter.append(new PropertyFilter(entityClass, additionalName, additionalValue));
//        }
//        String additionalName2 = request.getParameter("additional2");
//        if (StringUtils.isNotBlank(additionalName2)) {
//            String additionalValue2 = request.getParameter(additionalName2);
//            if (!ExtStringUtils.hasChinese(additionalValue2)) {
//                additionalValue2 = ExtStringUtils.encodeUTF8(additionalValue2);
//            }
//            groupPropertyFilter.append(new PropertyFilter(entityClass, additionalName2, additionalValue2));
//        }
        // 附加参数
        uniqueAddParams(groupPropertyFilter);

        List<T> entities = getEntityService().findByFilters(groupPropertyFilter);
        if (entities == null || entities.size() == 0) {// 未查到重复数据
            return Boolean.TRUE;
        } else {
            if (entities.size() == 1) {// 查询到一条重复数据
                String id = request.getParameter("id");
                if (StringUtils.isNotBlank(id)) {
                    Serializable entityId = entities.get(0).getId();
                    logger.debug("Check Unique Entity ID = {}", entityId);
                    if (id.equals(entityId.toString())) {// 查询到数据是当前更新数据，不算已存在
                        return Boolean.TRUE;
                    } else {// 查询到数据不是当前更新数据，算已存在
                        return Boolean.FALSE;
                    }
                } else {// 没有提供Sid主键，说明是创建记录，则算已存在
                    return Boolean.FALSE;
                }
            } else {// 查询到多余一条重复数据，说明数据库数据本身有问题
                return Boolean.FALSE;
            }
        }
    }

    protected void uniqueAddParams(GroupPropertyFilter groupPropertyFilter){
    }

    public static String getRoot(HttpServletRequest request){
        ServletContext context = request.getSession().getServletContext();
        final String absolutePath = FileUtils.getAbsolutePath(context, "");
        final String upath = FileUtils.normalizePath(absolutePath);
        return upath;
    }
}
