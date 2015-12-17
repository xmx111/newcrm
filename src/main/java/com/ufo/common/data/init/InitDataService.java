package com.ufo.common.data.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Lazy(false)
@Transactional
@Scope("singleton")
public class InitDataService {
    static {
        //设置字符编码,异常乱码问题处理.
        System.setProperty("file.encoding", "UTF-8");
    }
    @Autowired
    private InitDataServiceHelper initDataServiceHelper;

    private static boolean init = false;

    @PostConstruct
    public void initData() {
        if (!init) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                    }
                    initDataServiceHelper.init();

                }
            }).start();
            init = true;
        }
    }
}
