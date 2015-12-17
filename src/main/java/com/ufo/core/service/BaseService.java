package com.ufo.core.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufo.config.sys.dao.interfaces.ManagerDao;
import com.ufo.config.sys.entity.Manager;
import com.ufo.core.service.impl.MessageSourceHelper;
import com.ufo.core.utils.NumberUtils;
import com.ufo.core.utils.RequestUtils;
import com.ufo.core.utils.SecurityUtils;

public abstract class BaseService {
    private static final String sess_oper = "session_operation";
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    protected ManagerDao managerDao;
    @Autowired
    protected MessageSourceHelper messageSourceHelper;

    public BaseService() {
    }

    protected Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    protected Manager getOperation() {
        Object oper = RequestUtils.getAttribute(sess_oper);
        if (null == oper) {
            Manager managerDTO = (Manager) SecurityUtils.currentUser();
            Integer uid = null == managerDTO ? null : managerDTO.getId();
            if (NumberUtils.isNotEmpty(uid)) {
                oper = managerDao.findOne(uid);
                RequestUtils.setAttribute(sess_oper, oper);
            }
        }
        return (oper instanceof Manager) ? (Manager) oper : null;

    }
}
