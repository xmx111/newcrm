package com.ufo.erp.business.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.erp.business.entity.Business;
import com.ufo.erp.business.service.interfaces.IBusinessService;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@Controller("erpBusinessBusinessController")
@RequestMapping("/erp/business/")
@Description(code = "erp.business", value = "小贷公司设置")
@Secured("erp.business")
public class BusinessController extends PersistableController<Business, Integer> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/erp/business/";

    @Autowired
    private IBusinessService businessService;

    @Override
    public IBaseSpringDataService<Business, Integer> getEntityService() {
        return businessService;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("erp.business.index")
    public String index(HttpServletRequest request) {
        return this.toView("business-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.business.add", "erp.business.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("business-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.business.add", "erp.business.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("business-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("erp.business.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("erp.business.edit")
    @ResponseBody
    public Object update(Business dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.business.add", "erp.business.edit" })
    @ResponseBody
    public Object save(Business dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("erp.business.delete")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        return super.delete(request);
    }
    
    @RequestMapping("buildValidateRules.json")
    @ResponseBody
    public Object buildValidateRules() {
        return super.buildValidateRules();
    }

    @RequestMapping("checkUnique.json")
    @ResponseBody
    public Boolean checkUnique(HttpServletRequest request) {
        return super.checkUnique(request);
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
