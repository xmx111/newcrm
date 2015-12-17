package com.ufo.core.web;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.common.ProcResult;
import com.ufo.core.dto.IIdDTO;
import com.ufo.core.service.IService;
import com.ufo.core.utils.JsonUtils;
import com.ufo.core.utils.NumberUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public abstract class AbstractController<T extends IIdDTO> extends AbstractBaseController {
    //返回首页路径 
    private static final String PATH_INDEX = "index.htm";
    
    // 取服务
    protected abstract IService<T> getEntityService();

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
    
    protected void detail(Integer id, ModelMap map, T t) {
        if (NumberUtils.isNotEmpty(id)) {
            t = getEntityService().loadById(id);
        }
        map.put("dto", t);
    }
    
    protected Object save(T t){
        getEntityService().saveOrUpdate(t);
        return success(true);
    }
    
    protected Object delete(Serializable id){
        getEntityService().delete(id);
        return success(true);
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

    /** 
     * 操作员
    * @return
    */
    protected Manager operation() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object obj = auth == null ? null : auth.getPrincipal();
        return obj instanceof Manager ? (Manager) obj : null;
    }

}
