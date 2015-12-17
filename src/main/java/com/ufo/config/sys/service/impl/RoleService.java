package com.ufo.config.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.config.sys.dao.interfaces.RoleDao;
import com.ufo.config.sys.entity.Role;
import com.ufo.config.sys.service.interfaces.IRoleService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;

@Service
@Transactional
public class RoleService extends BaseSpringDataService<Role, Integer> implements IRoleService {
    
    @Autowired
    private RoleDao roleDao;

    @Override
    public BaseDao<Role, Integer> getEntityDao() {
        return roleDao;
    }
}
