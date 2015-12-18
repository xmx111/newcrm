package com.ufo.erp.salesman.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.erp.salesman.entity.Salesman;
import com.ufo.erp.salesman.service.interfaces.ISalesmanService;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@Controller("erpSalesmanSalesmanController")
@RequestMapping("/erp/salesman/")
@Description(code = "erp.salesman", value = "业务员设置")
@Secured("erp.salesman")
public class SalesmanController extends PersistableController<Salesman, Integer> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/erp/salesman/";

    @Autowired
    private ISalesmanService salesmanService;

    @Override
    public IBaseSpringDataService<Salesman, Integer> getEntityService() {
        return salesmanService;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("erp.salesman.index")
    public String index(HttpServletRequest request) {
        return this.toView("salesman-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.salesman.add", "erp.salesman.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("salesman-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.salesman.add", "erp.salesman.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("salesman-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("erp.salesman.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("erp.salesman.edit")
    @ResponseBody
    public Object update(Salesman dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.salesman.add", "erp.salesman.edit" })
    @ResponseBody
    public Object save(Salesman dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("erp.salesman.delete")
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
