package com.ufo.config.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.ufo.core.utils.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.config.sys.dao.interfaces.ManagerDao;
import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.entity.Role;
import com.ufo.config.sys.service.interfaces.IManagerService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.entity.UndeleteEntity;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.utils.CollectionUtils;
import com.ufo.core.utils.RandomUtils;
import com.ufo.core.utils.SecurityUtils;

@Service
@Transactional
public class ManagerService extends BaseSpringDataService<Manager, Integer> implements IManagerService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ManagerDao managerDao;
    
    @Autowired
    private PersistentTokenRepository dataTokenRepositoryService;

    @Override
    public BaseDao<Manager, Integer> getEntityDao() {
        return managerDao;
    }

    @Override
    public Manager save(Manager manager){
        Set<Role> roles = new HashSet<Role>();
        if (manager.getRoles() != null){
            Iterator<Role> it = manager.getRoles().iterator();
            while (it.hasNext()){
                Role role = it.next();
                if (role != null && role.getId() != null)
                    roles.add(role);
            }
        }
        manager.setRoles(roles);

        if (StringUtils.isBlank(manager.getPassword())){
            Manager entity = managerDao.findOne(manager.getId());
            if (entity != null)
                manager.setPassword(entity.getPassword());
        } else {
            manager.setPassword(passwordEncoder.encodePassword(manager.getPassword(), null));
        }
        return managerDao.save(manager);
    }

    @Override
    public String login(String loginName, String password) {
        Manager manager = this.findByProperty("loginName", loginName);
        
        if (null==manager || !manager.getPassword().equals(password)) {
            return "";
        }
        PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(manager.getLoginName(), RandomUtils.generateSeriesData(),
                RandomUtils.generateTokenData(), new Date());
        dataTokenRepositoryService.createNewToken(persistentToken);
        return persistentToken.getSeries();
    }

    @Override
    public String checkToken(String loginName, String token) {
        PersistentRememberMeToken persistentToken = dataTokenRepositoryService.getTokenForSeries(token);
        if (persistentToken == null || !persistentToken.getUsername().equals(loginName))
            return "";
        return token;
    }

    @Override
    public void cleanToken(String loginName) {
        dataTokenRepositoryService.removeUserTokens(loginName);
    }
    
    @Override
    public Manager editPwd(String oldPwd, String newPwd, String cfmPwd) throws Exception {
        Manager manager = managerDao.findOne(((Manager)SecurityUtils.currentUser()).getId());
        if (!newPwd.equals(cfmPwd))
            throw new Exception("确认密码和新密码不一致");
        if (oldPwd.equals(newPwd))
            throw new Exception("新密码不能和旧密码一样");
        if (!manager.getPassword().equals(passwordEncoder.encodePassword(oldPwd, null)))
            throw new Exception("旧密码不正确");
        manager.setPassword(passwordEncoder.encodePassword(newPwd, null));
        return managerDao.save(manager);
    }

    public void lock(String[] ids){
        if (ArrayUtils.isEmpty(ids))
            return;
        List list = new ArrayList<Manager>();
        for (String id : ids){
            Manager manager = this.findOne(Integer.valueOf(id));
            manager.setStatus(Manager.ManagerStatusEnum.LOCK);
            list.add(manager);
        }
        this.batchUpdate(list);
    }

    public void forbid(String[] ids){
        if (ArrayUtils.isEmpty(ids))
            return;
        List list = new ArrayList<Manager>();
        for (String id : ids){
            Manager manager = this.findOne(Integer.valueOf(id));
            manager.setStatus(Manager.ManagerStatusEnum.LOGOUT);
            list.add(manager);
        }
        this.batchUpdate(list);
    }

    public void active(String[] ids){
        if (ArrayUtils.isEmpty(ids))
            return;
        List list = new ArrayList<Manager>();
        for (String id : ids){
            Manager manager = this.findOne(Integer.valueOf(id));
            manager.setStatus(Manager.ManagerStatusEnum.NORMAL);
            list.add(manager);
        }
        this.batchUpdate(list);
    }

    public Boolean checkPassword(String password){
        if (this.getOperation().getPassword().equals(passwordEncoder.encodePassword(password, null))){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public Manager findByLoginName(String loginName){
        List<Manager> managers = managerDao.findByDeletedAndLoginName(UndeleteEntity.DeleteTypeEnum.UNDELETE, loginName);
        if (CollectionUtils.isNotEmpty(managers))
            return managers.get(0);
        else{
            return null;
        }      
    }
}
