package com.ufo.config.sys.service.interfaces;

import com.ufo.config.sys.entity.AttachmentFile;
import com.ufo.config.sys.entity.Authority;
import com.ufo.core.service.IBaseSpringDataService;
import org.springframework.data.domain.Persistable;

import java.util.List;

public interface IAttachmentFileService extends IBaseSpringDataService<AttachmentFile, String> {

    public List<AttachmentFile> findBy(String entityClassName, String entityId, String entityFileCategory);

    public void attachmentBind(String[] attachmentIds, Persistable<?> bindingEntity, String entityFileCategory);

    /**
     * 有时用户上传了一些附件但是可能没有保存主业务对象，导致产生一些“孤儿”附件记录和文件
     * 配置定时任务清理超过一定时限没有被关联使用的附件数据记录和磁盘文件
     */
    public void timelyClearUnusedFiles();
}
