package com.ufo.erp.company.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.erp.company.dao.interfaces.CompanyDao;
import com.ufo.erp.company.entity.Company;
import com.ufo.erp.company.service.interfaces.ICompanyService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.service.IBaseSpringDataService;

@Service
@Transactional
public class CompanyService extends BaseSpringDataService<Company, Integer> implements ICompanyService {
    
    @Autowired
    private CompanyDao companyDao;

    @Override
    public BaseDao<Company, Integer> getEntityDao() {
        return companyDao;
    }
}
