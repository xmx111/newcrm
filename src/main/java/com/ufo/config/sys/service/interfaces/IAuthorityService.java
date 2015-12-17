package com.ufo.config.sys.service.interfaces;

import java.util.List;
import java.util.Map;

import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Authority.SysTypeEnum;
import com.ufo.config.sys.entity.Role;
import com.ufo.core.service.IBaseSpringDataService;

public interface IAuthorityService extends IBaseSpringDataService<Authority, Integer> {

    public List<Authority> listByTree();

    public List<Authority> listByTree(SysTypeEnum sysType);

    public List<Authority> listByRole(Role role);

    public void removeAuthority();
    
}