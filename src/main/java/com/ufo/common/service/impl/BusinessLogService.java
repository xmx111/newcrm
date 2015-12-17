package com.ufo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.common.dao.interfaces.BusinessLogDao;
import com.ufo.common.entity.BusinessLog;
import com.ufo.common.service.interfaces.IBusinessLogService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;

@Service
@Transactional
public class BusinessLogService extends BaseSpringDataService<BusinessLog, Integer> implements IBusinessLogService {
    
    @Autowired
    private BusinessLogDao businessLogDao;
    
    @Autowired
    private BusinessLogHelperService businessLogHelperService;

    @Override
    public BaseDao<BusinessLog, Integer> getEntityDao() {
        return businessLogDao;
    }
    
    public void saveLog(){
        businessLogHelperService.doSave();
    }
}
