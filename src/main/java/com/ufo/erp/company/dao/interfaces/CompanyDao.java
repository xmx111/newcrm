package com.ufo.erp.company.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.erp.company.entity.Company;
import com.ufo.core.dao.BaseDao;

@Repository
public interface CompanyDao extends BaseDao<Company, Integer> {

}
