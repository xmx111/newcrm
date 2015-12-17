package com.ufo.config.sys.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.config.sys.dao.interfaces.AuthorityDao;
import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Authority.AuthorityTypeEnum;
import com.ufo.config.sys.entity.Authority.SysTypeEnum;
import com.ufo.config.sys.entity.Role;
import com.ufo.config.sys.service.interfaces.IAuthorityService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.utils.CollectionUtils;
import com.ufo.core.utils.NumberUtils;
import com.ufo.core.utils.SecurityUtils;

@Service
@Transactional
public class AuthorityService extends BaseSpringDataService<Authority, Integer> implements IAuthorityService {
    
    @Autowired
    private AuthorityDao authorityDao;

    @Override
    public BaseDao<Authority, Integer> getEntityDao() {
        return authorityDao;
    }

    @Override
    public List<Authority> listByTree() {
        return listByTree(null);
    }

    @Override
    public List<Authority> listByTree(SysTypeEnum sysType) {
        List<Authority> result = new ArrayList<Authority>(5);
        List<Authority> list = new ArrayList<Authority>();
        if (sysType != null)
            list = authorityDao.findBySysType(sysType, new Sort("parent.id", "sequence"));
        else
            CollectionUtils.iterableToList(authorityDao.findAll(new Sort("parent.id", "sequence")), list);
        List<Authority> root = new ArrayList<Authority>(5);
        for (Authority authority : list) {
            if (authority.getParent() == null || NumberUtils.isEmpty(authority.getParent().getId())) {
                if (SecurityUtils.hasAllAuthorize(authority.getCode()))
                    root.add(authority);
            }
        }
        for (final Authority authority : root) {
            if (!SecurityUtils.hasAuthorize(authority.getUrl())) {
                continue;
            }
            checkChildren(authority);
            checkAuthorize(authority);
            if (authority.getHasChildren()) {
                result.add(authority);
            } else if (null == authority.getParent()) {
                result.add(authority);
            }
        }
        return result;
    }

    protected void checkChildren(Authority authority) {
        List<Authority> child = new ArrayList<Authority>();
        if (authority.getHasChildren()){
            for (Authority auth : authority.getChildren()){
                if (auth.getCode() != null){
                    Authority dto = new Authority();
                    dto.setId(auth.getId());
                    dto.setChildren(auth.getChildren());
                    dto.setType(auth.getType());
                    dto.setCode(auth.getCode());
                    dto.setUrl(auth.getUrl());
                    dto.setName(auth.getName());
                    dto.setParent(auth.getParent());
                    dto.setSysType(auth.getSysType());
                    child.add(dto);
                }
            }
        }
        authority.setChildren(child);
    }
    
    protected void checkAuthorize(Authority authority) {
        if (authority.getHasChildren()) {
            List<Authority> temp = new ArrayList<Authority>(authority.getChildren());
            for (Authority item : temp) {
                checkAuthorize(item);
            }
        }
        Authority parent = authority.getParent();
        if (parent != null) {
            if (authority.getType() == AuthorityTypeEnum.AUTH)
                parent.removeChildren(authority);
            if (!SecurityUtils.hasAllAuthorize(authority.getCode()))
                parent.removeChildren(authority);
        }
    }

    @Override
    public List<Authority> listByRole(Role role) {
        return authorityDao.findByRoles(role);
    }

    @Override
    public void removeAuthority() {
        List<Authority> list = authorityDao.findAllSort();
        entityManager.createNativeQuery("delete from " + Role.TB_ROLE_AUTH).executeUpdate();

        if (list == null)
            return;
        int i = 1;
        for (Authority t : list){
            entityManager.remove(t);
            if (i % 30 == 0 ){
                entityManager.flush();
            }
            i ++;
        }
        if (i / 31 != 0)
            entityManager.flush();
    }
}
