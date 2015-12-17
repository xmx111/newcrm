package com.ufo.core.security.repository.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.core.dao.BaseDao;
import com.ufo.core.security.repository.entity.PersistentLogin;

@Repository
public interface DataTokenRepositoryDao extends BaseDao<PersistentLogin, String> {

}
