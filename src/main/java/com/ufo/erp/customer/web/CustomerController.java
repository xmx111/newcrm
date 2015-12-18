package com.ufo.erp.customer.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.erp.customer.entity.Customer;
import com.ufo.erp.customer.service.interfaces.ICustomerService;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@Controller("erpCustomerCustomerController")
@RequestMapping("/erp/customer/")
@Description(code = "erp.customer", value = "客户设置")
@Secured("erp.customer")
public class CustomerController extends PersistableController<Customer, Integer> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/erp/customer/";

    @Autowired
    private ICustomerService customerService;

    @Override
    public IBaseSpringDataService<Customer, Integer> getEntityService() {
        return customerService;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("erp.customer.index")
    public String index(HttpServletRequest request) {
        return this.toView("customer-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.customer.add", "erp.customer.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("customer-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.customer.add", "erp.customer.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("customer-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("erp.customer.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("erp.customer.edit")
    @ResponseBody
    public Object update(Customer dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "erp.customer.add", "erp.customer.edit" })
    @ResponseBody
    public Object save(Customer dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("erp.customer.delete")
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
