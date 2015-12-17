package com.ufo.config.sys.web;

import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.entity.Role;
import com.ufo.config.sys.service.interfaces.IManagerService;
import com.ufo.config.sys.service.interfaces.IRoleService;
import com.ufo.core.annotation.Description;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller("configSysManagerController")
@RequestMapping("/config/sys/manager/")
@Description(code = "system.manager", value = "系统操作员设置")
@Secured("system.manager")
public class ManagerController extends PersistableController<Manager, Integer> {
    
    /***
     * jsp file path
     */
    private static final String VIEWPATH = "/config/sys/manager/";

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IManagerService managerService;

    @Override
    public IBaseSpringDataService<Manager, Integer> getEntityService() {
        return managerService;
    }
    
    @RequestMapping("index.htm")
    @Description("列表查询")
    @Secured("system.manager.index")
    public String index(HttpServletRequest request) {
        return this.toView("manager-index");
    }

    @RequestMapping("inputTabs.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.manager.add", "system.manager.edit" })
    public String inputTabs(Integer id, Boolean clone, ModelMap map) {
        if (id != null && id != Integer.MIN_VALUE)
            map.put("id", id);
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("manager-inputTabs");
    }

    @RequestMapping("edit.htm")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.manager.add", "system.manager.edit" })
    public String edit(Integer id, Boolean clone, ModelMap map) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(0, 9999, sort);
        List<Role> roles = roleService.findByPage(null, pageable).getContent();
        map.put("roles", roles);

        if (id != null && id != Integer.MIN_VALUE){
            Manager manager = detail(id);
            map.put("dto", manager);
            map.put("roleMap", roleArrayToMap(manager.getRoles().iterator()));
            map.put("id", id);
        }
        if (clone == Boolean.TRUE)
            map.put("clone", clone);
        return this.toView("manager-inputBasic");
    }
    
    @RequestMapping("findByPage.json")
    @Description("列表查询")
    @Secured("system.manager.index")
    @ResponseBody
    public Object findByPage(HttpServletRequest request) {
        return super.findByPage(request);
    }
    
    @RequestMapping("update.json")
    @Description("编辑数据")
    @Secured("system.manager.edit")
    @ResponseBody
    public Object update(Manager dto) {
        return super.save(dto);
    }
    
    @RequestMapping("save.json")
    @Description({ "新增数据", "编辑数据" })
    @Secured({ "system.manager.add", "system.manager.edit" })
    @ResponseBody
    public Object save(Manager dto) {
        Manager entity = managerService.findByLoginName(dto.getLoginName());
        if (entity != null && dto.getId() != entity.getId()){
            return restFailed("账号编号重复");
        }

        return super.save(dto);
    }

    @RequestMapping("lock.json")
    @Secured({ "system.manager.add", "system.manager.edit" })
    @ResponseBody
    public Object lock(String[] ids) {
        this.managerService.lock(ids);
        return this.restSuccess(null);
    }

    @RequestMapping("forbid.json")
    @Secured({ "system.manager.add", "system.manager.edit" })
    @ResponseBody
    public Object forbid(String[] ids) {
        this.managerService.forbid(ids);
        return this.restSuccess(null);
    }

    @RequestMapping("active.json")
    @Secured({ "system.manager.add", "system.manager.edit" })
    @ResponseBody
    public Object active(String[] ids) {
        this.managerService.active(ids);
        return this.restSuccess(null);
    }

    @RequestMapping("delete.json")
    @Description("删除数据")
    @Secured("system.manager.delete")
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

    private Map<Integer, Role> roleArrayToMap(Iterator<Role> roles){
        Map<Integer, Role> map = new HashMap<Integer, Role>();
        while (roles.hasNext()) {
            Role role = roles.next();
            map.put(role.getId(), role);
        }
        return map;
    }

    @ModelAttribute("manager")
    public Manager initManager() {
        Manager manager = new Manager();
        Set<Role> roles = new HashSet<Role>();
        for (int i = 0; i < 20; i ++){
            Role role = new Role();
            roles.add(role);
        }
        manager.setRoles(roles);
        return manager;
    }

}
