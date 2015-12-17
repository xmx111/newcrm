package com.ufo.config.sys.dao.interfaces;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Authority.SysTypeEnum;
import com.ufo.config.sys.entity.Role;
import com.ufo.core.dao.BaseDao;

@Repository
public interface AuthorityDao extends BaseDao<Authority, Integer> {

	public List<Authority> findBySysType(SysTypeEnum sysType, Sort sort);

    public Authority findByCode(String code);

    public List<Authority> findByRoles(Role role);

    @Query("select t from Authority t order by t.parent desc ")
    public List<Authority> findAllSort();
	
}
