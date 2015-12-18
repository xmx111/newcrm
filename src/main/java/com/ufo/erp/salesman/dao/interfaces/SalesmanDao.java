package com.ufo.erp.salesman.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.erp.salesman.entity.Salesman;
import com.ufo.core.dao.BaseDao;

@Repository
public interface SalesmanDao extends BaseDao<Salesman, Integer> {

}
