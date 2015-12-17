package com.ufo.common.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ufo.common.dao.interfaces.BusinessLogDao;
import com.ufo.common.entity.BusinessLog;
import com.ufo.config.sys.dao.interfaces.ManagerDao;
import com.ufo.core.service.BaseService;
import com.ufo.core.utils.LogUtils;

/** 
* 类名称：BusinessLogHelperService 
* 类描述： 业务日志辅助类
* 创建人：Duzj
* 创建时间：2013-5-17 上午11:21:07 
* @version 
* 
*/
@Service("businessLogHelperService")
@Scope("singleton")
public class BusinessLogHelperService extends BaseService {
    @Autowired
    private BusinessLogDao businessLogDao;
    @Autowired
    private ManagerDao managerDao;
    /** 
    *batSize 批量处理日志大小
    */
    private static final int batSize = 50;

    public void doSave(){
        final String msg = "执行保存操作日志任务";
        if (logger.isDebugEnabled()) {
            logger.debug(msg + "开始!");
        }
        try {
            List<BusinessLog> list = LogUtils.pop(batSize);
            if (CollectionUtils.isNotEmpty(list)) {
                for (BusinessLog log : list) {
                    businessLogDao.save(log);
                }
            }
        } catch (Exception e) {
            LogUtils.addJob(msg + e.getLocalizedMessage(), false);
        }
    }

    /** 
    * 停止时,保存日志. 此日志,可能无法保存到数据库,建议写入日志文件
    */
    public void destroy() {
        if (logger.isInfoEnabled()) {
            logger.info("停止系统,开始保存缓存中的日志");
        }
        LogUtils.addJob("停止系统,保存缓存中的日志", null);
        while (true) {
            List<BusinessLog> list = LogUtils.pop(batSize);
            if (CollectionUtils.isEmpty(list)) {
                break;
            } else {
                for (BusinessLog log : list) {
                    businessLogDao.save(log);
                }
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("停止系统,保存缓存中的日志完成");
        }
    }
}
