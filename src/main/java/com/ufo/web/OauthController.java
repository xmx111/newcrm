package com.ufo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.service.interfaces.IManagerService;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.web.PersistableController;

@SuppressWarnings("rawtypes")
@Controller("oauthController")
@RequestMapping("/oauth")
public class OauthController extends PersistableController {
    @Autowired
    private IManagerService managerService;

    @Override
    public IBaseSpringDataService<Manager, Integer> getEntityService() {
        return managerService;
    }

    @RequestMapping("login.json")
    @ResponseBody
    public Object login(String loginName, String password) {
        return managerService.login(loginName, password);
    }

    @RequestMapping("checkToken.json")
    @ResponseBody
    public Object checkToken(String loginName, String token) {
        return managerService.checkToken(loginName, token);
    }

    @RequestMapping("cleanToken.json")
    public void cleanToken(String loginName) {
        managerService.cleanToken(loginName);
    }

}