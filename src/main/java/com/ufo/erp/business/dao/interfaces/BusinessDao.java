package com.ufo.erp.business.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.erp.business.entity.Business;
import com.ufo.core.dao.BaseDao;

@Repository
public interface BusinessDao extends BaseDao<Business, Integer> {

}
