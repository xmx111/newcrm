package com.ufo.common.service.interfaces;

import com.ufo.common.entity.BusinessLog;
import com.ufo.core.service.IBaseSpringDataService;

public interface IBusinessLogService extends IBaseSpringDataService<BusinessLog, Integer> {
    
    /** 
     * 缓存日志保存
     */
    public void saveLog();
    
}