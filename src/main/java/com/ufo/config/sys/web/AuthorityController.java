package com.ufo.config.sys.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.service.interfaces.IAuthorityService;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@Controller("configSysAuthorityController")
@RequestMapping("/config/sys/authority/")
@Description(code = "system.authority", value = "权限设置")
@Secured("system.authority")
public class AuthorityController extends PersistableController<Authority, Integer> {

    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/config/sys/authority/";

    @Autowired
    private IAuthorityService authorityService;

    @Override
    public IBaseSpringDataService<Authority, Integer> getEntityService() {
        return authorityService;
    }

    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("system.authority.index")
    public String index(HttpServletRequest request) {
        return this.toView("authority-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.authority.add", "system.authority.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("authority-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.authority.add", "system.authority.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("authority-inputBasic");
    }

    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("system.authority.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }

    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("system.authority.edit")
    @ResponseBody
    public Object update(Authority dto) {
        return super.save(dto);
    }

    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.authority.add", "system.authority.edit" })
    @ResponseBody
    public Object save(Authority dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("system.authority.delete")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        return super.delete(request);
    }
    
    @RequestMapping("buildValidateRules.json")
    @ResponseBody
    public Object buildValidateRules() {
        return super.buildValidateRules();
    }
    
    /** 
     * 重写方法 
     * @see com.ufo.core.web.AbstractBaseController#getModelPath() 
     */
     @Override
     protected String getModelViewPath() {
        
         return VIEWPATH;
     }
    
}
