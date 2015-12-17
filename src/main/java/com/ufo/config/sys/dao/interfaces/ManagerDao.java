package com.ufo.config.sys.dao.interfaces;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.entity.UndeleteEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManagerDao extends BaseDao<Manager, Integer> {

    //@QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    List<Manager> findByDeletedAndLoginName(UndeleteEntity.DeleteTypeEnum deleted, String loginName);

}
