package com.ufo.common.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.common.entity.BusinessLog;
import com.ufo.common.service.interfaces.IBusinessLogService;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@Controller("commonBusinessLogController")
@RequestMapping("/common/log/")
@Description(code = "common", value = "业务日志设置")
@Secured("common")
public class BusinessLogController extends PersistableController<BusinessLog, Integer> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/common/log/";

    @Autowired
    private IBusinessLogService businessLogService;

    @Override
    public IBaseSpringDataService<BusinessLog, Integer> getEntityService() {
        return businessLogService;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("common.index")
    public String index(HttpServletRequest request) {
        return this.toView("businessLog-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "common.add", "common.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("businessLog-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "common.add", "common.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("businessLog-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("common.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("common.edit")
    @ResponseBody
    public Object update(BusinessLog dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "common.add", "common.edit" })
    @ResponseBody
    public Object save(BusinessLog dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("common.delete")
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
