package com.ufo.erp.salesman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.erp.salesman.dao.interfaces.SalesmanDao;
import com.ufo.erp.salesman.entity.Salesman;
import com.ufo.erp.salesman.service.interfaces.ISalesmanService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.service.IBaseSpringDataService;

@Service
@Transactional
public class SalesmanService extends BaseSpringDataService<Salesman, Integer> implements ISalesmanService {
    
    @Autowired
    private SalesmanDao salesmanDao;

    @Override
    public BaseDao<Salesman, Integer> getEntityDao() {
        return salesmanDao;
    }
}
