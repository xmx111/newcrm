package com.ufo.web;

import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.service.interfaces.IAuthorityService;
import com.ufo.config.sys.service.interfaces.IManagerService;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.service.impl.SystemConfigHolder;
import com.ufo.core.web.PersistableController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@SuppressWarnings("rawtypes")
@Controller("mainController")
@RequestMapping("/main/")
public class MainController extends PersistableController {
    
    @Autowired
    private IManagerService managerService;
    
    @Autowired
    private IAuthorityService authorityService;

    @Override
    protected IBaseSpringDataService getEntityService() {
        return managerService;
    }
    
    @RequestMapping("index.htm")
    public String index(@RequestParam(defaultValue = "")String mobile,@RequestParam(defaultValue = "")String path, ModelMap map) {
        final Manager operation = operation();
        if (operation == null){
            map.put("redirect", "/main/index.htm");
            return toView("login");
        }
        map.put("operation", operation);

        List<Authority> authorities = authorityService.listByTree();
        map.put("menus", authorities);
        map.put("systemTitle", SystemConfigHolder.systemTitle);
        return toView("layout-start");
    }

    @RequestMapping("dashboard.htm")
    public String dashboard(ModelMap map) {
        final Manager operation = operation();
        if (operation == null)
            return toView("login");
        return toView("layout-dashboard");
    }
    
    @RequestMapping("profile-pwd.htm")
    public String profilePwd() {
        return this.toView("profile-pwd");
    }
    
    @RequestMapping("editPwd.json")
    @ResponseBody
    public Object editPwd(String oldPwd, String newPwd, String cfmPwd) {
        Manager manager = null;
        try {
            manager = managerService.editPwd(oldPwd, newPwd, cfmPwd);
        } catch(Exception e){
            return restFailed(e.getMessage());
        }
        manager.setPassword(null);
        return this.restSuccess(manager, "修改密码成功");
    }

    @RequestMapping("lock.htm")
    @ResponseBody
    public Object lock(String password) {
        if (managerService.checkPassword(password)){
            return this.restSuccess("密码正确");
        } else {
            return this.restFailed("密码出错");
        }
    }
}
