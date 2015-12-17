package com.ufo.common.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.common.entity.BusinessLog;
import com.ufo.core.dao.BaseDao;

@Repository
public interface BusinessLogDao extends BaseDao<BusinessLog, Integer> {

}
