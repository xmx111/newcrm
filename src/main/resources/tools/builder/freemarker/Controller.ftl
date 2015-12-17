package ${root_package}.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ${root_package}.entity.${entity_name};
import ${root_package}.service.interfaces.I${entity_name}Service;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@Controller("${controll_name}Controller")
@RequestMapping("${model_path}/")
@Description(code = "${model_name}", value = "${model_title}设置")
@Secured("${model_name}")
public class ${entity_name}Controller extends PersistableController<${entity_name}, ${id_type}> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "${model_path}/";

    @Autowired
    private I${entity_name}Service ${entity_name_uncapitalize}Service;

    @Override
    public IBaseSpringDataService<${entity_name}, ${id_type}> getEntityService() {
        return ${entity_name_uncapitalize}Service;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("${model_name}.index")
    public String index(HttpServletRequest request) {
        return this.toView("${entity_name_field}-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "${model_name}.add", "${model_name}.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("${entity_name_field}-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "${model_name}.add", "${model_name}.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE){
            map.put("dto", detail(id));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("${entity_name_field}-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("${model_name}.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("${model_name}.edit")
    @ResponseBody
    public Object update(${entity_name} dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "${model_name}.add", "${model_name}.edit" })
    @ResponseBody
    public Object save(${entity_name} dto) {
        return super.save(dto);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("${model_name}.delete")
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
