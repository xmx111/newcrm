package com.ufo.config.sys.dao.interfaces;

import com.ufo.config.sys.entity.AttachmentFile;
import com.ufo.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentFileDao extends BaseDao<AttachmentFile, String> {
    List<AttachmentFile> findByEntityClassNameAndEntityIdAndEntityFileCategory(String entityClassName, String entityId,
                                                                               String entityFileCategory);

    List<AttachmentFile> findByEntityClassNameAndEntityId(String entityClassName, String entityId);
   
}
