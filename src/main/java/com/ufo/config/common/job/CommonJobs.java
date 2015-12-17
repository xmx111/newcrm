package com.ufo.config.common.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ufo.common.service.interfaces.IBusinessLogService;

/** 
* 类名称：CommonJobs 
* 类描述：公共自动任务类,含(业务日志保存,节假日生成等)
* 创建人：Duzj
* 创建时间：2012-12-6 下午5:14:33 
* @version 
* 
*/
@Service("commonJobs")
@Lazy(false)
@Scope("singleton")
public class CommonJobs {
    @Autowired
    private IBusinessLogService businessLogService;

    /** 
     * 每分钟执行一次保存日志操作
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void saveBusinessLog() {
        businessLogService.saveLog();
    }

}
