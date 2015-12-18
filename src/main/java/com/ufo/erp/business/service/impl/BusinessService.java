package com.ufo.erp.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.erp.business.dao.interfaces.BusinessDao;
import com.ufo.erp.business.entity.Business;
import com.ufo.erp.business.service.interfaces.IBusinessService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.service.IBaseSpringDataService;

@Service
@Transactional
public class BusinessService extends BaseSpringDataService<Business, Integer> implements IBusinessService {
    
    @Autowired
    private BusinessDao businessDao;

    @Override
    public BaseDao<Business, Integer> getEntityDao() {
        return businessDao;
    }
}
