package com.ufo.core.web;

import com.google.common.collect.Sets;
import com.ufo.core.common.BaseRuntimeException;
import com.ufo.core.common.Paginator;
import com.ufo.core.common.ProcResult;
import com.ufo.core.common.WebServiceResult;
import com.ufo.core.web.converter.DatePropertyEdit;
import com.ufo.core.web.converter.TimePropertyEdit;
import com.ufo.core.web.converter.TimestampPropertyEdit;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public abstract class AbstractBaseController {
    //访问根路径
    private static final String PATH_ROOT = "/";
    //日记
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /** 
     * 返回模块视图(JSP文件)根目录路径,默认返回""
    * @return
    */
    protected String getModelViewPath() {
        return StringUtils.EMPTY;
    }

    /** 
     * 返回模块访问路径,默认返回"/"
    * @return
    */
    protected String getModelPath() {
        return PATH_ROOT;
    }

    /** 
     * 将模块相对路径转化为项目相对路径
    * @param path
    * @return
    */
    protected String converUrl(final String path) {
        String _path = StringUtils.trimToEmpty(path);
        StringBuffer buf = new StringBuffer();
        //获取默认模块返回路径 
        String modelPath = StringUtils.trimToEmpty(getModelPath());
        if (!_path.startsWith(modelPath)) {
            buf.append(modelPath);
            if (!modelPath.endsWith("/")) {
                buf.append("/");
            }
        }
        buf.append(_path);
        return normalizePath(buf.toString());
    }

    /** 
     * 将模块相对路径视图(JSP)转化为项目相对路径视图
    * @param path
    * @return
    */
    protected String converPath(final String path) {
        String _path = StringUtils.trimToEmpty(path);
        StringBuffer buf = new StringBuffer();
        //获取默认模块返回路径 
        String viewPath = StringUtils.trimToEmpty(getModelViewPath());
        if (!_path.startsWith(viewPath)) {
            buf.append(viewPath);
            if (!viewPath.endsWith("/")) {
                buf.append("/");
            }
        }
        buf.append(_path);
        return normalizePath(buf.toString());
    }

    /** 
     * 替换 \\ // .. 为 /
    * @param path
    * @return
    */
    protected String normalizePath(final String path) {
        String _path = StringUtils.trimToEmpty(path);
        _path = _path.replace("\\", "/").replace("\\", "/").replace("//", "/").replace("//", "/");
        return _path;
    }

    /** 
     * 前台json数据返回成功
    * @param data
    * @param paginator
    * @return
    */
    protected Object JsonSuccess(Object data, Paginator paginator) {
        WebServiceResult result = JsonResult(data, null);
        if (paginator != null) {
            result.setTotalRecords(paginator.getTotalRecords());
            result.setLastRecored(paginator.getEndIndex());
        }
        return result;
    }

    /** 
     * 前台json数据返回成功
    * @param data
    * @return
    */
    protected WebServiceResult JsonSuccess(Object data) {
        if (logger.isDebugEnabled()) {
            logger.debug("return data is " + data);
        }
        return JsonResult(data, null);
    }

    /** 
     * 前台json数据返回失败
    * @param ex
    * @return
    */
    protected WebServiceResult JsonFailed(Exception ex) {
        logger.error(ex.getMessage(), ex);
        WebServiceResult result = new WebServiceResult();
        result.setResult(false);
        result.setMessage(ex.getLocalizedMessage());
        if (ex instanceof BaseRuntimeException) {
            BaseRuntimeException e = (BaseRuntimeException) ex;
            result.setCode(e.getMessageCode());
            result.setData(null != e.getData() ? e.getData() : e.getErrors());
        }
        return result;
    }

    /** 
     * 前台json数据返回
    * @param data
    * @param msg
    * @return
    */
    protected WebServiceResult JsonResult(Object data, String msg) {
        WebServiceResult result = new WebServiceResult();
        if (data == null) {
            result.setResult(false);
        }
        result.setMessage(msg);
        result.setData(data);
        if (logger.isDebugEnabled()) {
            logger.debug("return  is " + result);
        }
        return result;
    }

    /** 
     * 后台dwz格式的json数据返回成功
     * @return
     */
    protected ProcResult DWZsuccess() {
        return DWZsuccess(null);
    }

    /**
     * 后台dwz格式的json数据返回 成功
    * @param obj
    * @return
    */
    protected ProcResult DWZsuccess(Object obj) {
        ProcResult result = new ProcResult();
        result.setCallbackType("refresh");
        result.setSmessage("操作成功!");
        result.setSdata(obj);
        return result;
    }

    /** 
     * 后台dwz格式的json数据返回 失败
    * @param msg
    * @return
    */
    protected ProcResult DWZFailed(String msg) {
        ProcResult result = new ProcResult();
        result.setSstatuscode(300);
        result.setSmessage(msg);
        return result;
    }

    /** 
     * 后台dwz格式的json数据返回 失败
    * @param e
    * @return
    */
    protected ProcResult DWZFailed(Exception e) {
        ProcResult result = new ProcResult();
        result.setForwardUrl("login.htm");
        result.setSmessage(e.getMessage());
        result.setSstatuscode(300);
        if (e instanceof BaseRuntimeException) {
            BaseRuntimeException ex = (BaseRuntimeException) e;
            result.setErrorCode(ex.getMessageCode());
            result.setSdata(null != ex.getData() ? ex.getData() : ex.getErrors());
        }
        return result;
    }
    
    protected ProcResult restSuccess(Object obj){
        return restSuccess(obj, "操作成功");
    }
    
    protected ProcResult restSuccess(Object obj, String msg){
        ProcResult result = new ProcResult();
        result.setType("success");
        result.setSmessage(msg);
        result.setSdata(obj);
        return result;
    }

    protected ProcResult restFailed(String err){
        ProcResult result = new ProcResult();
        result.setType("error");
        result.setSstatuscode(300);
        result.setSmessage(err);
        return result;
    }

    /**
     * 产生异常
     * @param msg
     * @return
     */
    protected ProcResult restException(String msg){
        ProcResult result = new ProcResult();
        result.setType("exception");
        result.setSstatuscode(300);
        result.setSmessage(msg);
        return result;
    }

    /**
     * false
     * @param msg
     * @return
     */
    protected ProcResult restFalse(String msg){
        ProcResult result = new ProcResult();
        result.setType("false");
        result.setSstatuscode(300);
        result.setSmessage(msg);
        return result;
    }

    /**
     * true
     * @param msg
     * @return
     */
    protected ProcResult restTrue(String msg){
        ProcResult result = new ProcResult();
        result.setType("true");
        result.setSmessage(msg);
        return result;
    }

    /** 
     * 数据绑定
    * @param binder
    */
    @InitBinder
    public void binder(WebDataBinder binder) {
        //java.util.Date
        DatePropertyEdit datePropertyEditor = new DatePropertyEdit(java.util.Date.class);
        binder.registerCustomEditor(java.util.Date.class, datePropertyEditor);
        //java.sql.Date
        DatePropertyEdit sDatePropertyEditor = new DatePropertyEdit(java.sql.Date.class);
        binder.registerCustomEditor(java.sql.Date.class, sDatePropertyEditor);
        //java.sql.Time
        binder.registerCustomEditor(java.sql.Time.class, new TimePropertyEdit());
        //java.sql.Timestamp
        binder.registerCustomEditor(java.sql.Timestamp.class, new TimestampPropertyEdit(java.sql.Timestamp.class));
    }

    /**
     * 一般用于如删除等批量操作
     * @return id字符串集合
     */
    protected String[] getParameterIds(HttpServletRequest request) {
        return getParameterIds(request, "ids");
    }

    /**
     * 一般用于如删除等批量操作
     * @return id字符串集合
     */
    protected String[] getParameterIds(HttpServletRequest request, String paramName) {
        Set<String> idSet = Sets.newHashSet();
        String[] params = request.getParameterValues(paramName);
        if (params != null) {
            for (String param : params) {
                for (String id : param.split(",")) {
                    String trimId = id.trim();
                    if (StringUtils.isNotBlank(trimId)) {
                        idSet.add(trimId);
                    }
                }
            }
        }
        return idSet.toArray(new String[] {});
    }

    /**
     * 获取必须参数值,如果参数为空则抛出异常
     *
     * @param name 参数名称
     * @return 参数值
     */
    protected String getRequiredParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtils.isBlank(value)) {
            throw new RuntimeException("参数为空: " + name);
        }
        return value;
    }

    /**
     * 常规方式获取请求参数值
     *
     * @param name  参数名称
     * @return 参数值
     */
    protected String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }
}
