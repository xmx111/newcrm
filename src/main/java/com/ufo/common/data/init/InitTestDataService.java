package com.ufo.common.data.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 类名称：InitEnvironmentService 类描述：
 * 初始化菜单数据 
 * 创建人：Duzj 
 * 创建时间：2012-12-6 上午9:46:31
 * @version
 */
@Service
public class InitTestDataService {
    //private static final int SIZE = 5;
    private static final String REMARK = "此数据为测试数据!";
    private final Logger logger = LoggerFactory.getLogger(InitTestDataService.class);
    
    @PersistenceContext
    protected EntityManager entityManager;

    public void init() {
        try {

        } catch (Exception e) {
            logger.error("创建测试数据出错!", e);
        }
    }
}
