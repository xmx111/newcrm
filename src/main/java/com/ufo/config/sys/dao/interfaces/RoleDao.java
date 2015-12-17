package com.ufo.config.sys.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.config.sys.entity.Role;
import com.ufo.core.dao.BaseDao;

@Repository
public interface RoleDao extends BaseDao<Role, Integer> {

}
