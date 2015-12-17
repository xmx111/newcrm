package com.ufo.core.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.ufo.config.sys.entity.AttachmentFile;
import com.ufo.config.sys.service.interfaces.IAttachmentFileService;
import com.ufo.core.entity.AttachmentableEntity;
import com.ufo.core.entity.BaseEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class FileBaseController<T extends BaseEntity, ID extends Serializable> extends
        PersistableController<T, Integer> {

    private final Logger logger = LoggerFactory.getLogger(FileBaseController.class);

    protected long imageUploadMaxSize;

    abstract protected IAttachmentFileService getAttachmentFileService();

    public void setImageUploadMaxSize(String imageUploadMaxSize) {
        this.imageUploadMaxSize = Long.parseLong(imageUploadMaxSize);
    }

    public Long getImageUploadMaxSize() {
        return imageUploadMaxSize;
    }

    protected Object save(HttpServletRequest request, T dto) {
        dto = getEntityService().save(dto);

        //附件关联处理，自动处理以attachment为前缀的参数
        if (dto instanceof AttachmentableEntity) {
            Enumeration<?> names = request.getParameterNames();
            Set<String> attachmentNames = Sets.newHashSet();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                if (name.startsWith("_attachment_")) {
                    attachmentNames.add(name);
                }
            }
            if (attachmentNames.size() > 0) {
                for (String attachmentName : attachmentNames) {
                    String[] attachments = getParameterIds(request, attachmentName);
                    getAttachmentFileService().attachmentBind(attachments, dto,
                            StringUtils.substringAfter(attachmentName, "_attachment_"));
                }
            }
        }
        return restSuccess(dto);
    }

    protected Object attachmentList(HttpServletRequest request, T entity) {
        return attachmentList(request, entity, "default");
    }

    protected Object attachmentList(HttpServletRequest request, T entity, String category) {
        if (!(entity instanceof  AttachmentableEntity))
            return null;
        String url = request.getServletPath();
        List<AttachmentFile> attachmentFiles = getAttachmentFileService().findBy(entity.getClass().getName(),
                String.valueOf(((AttachmentableEntity)entity).getAttachmentGuid()), category);
        List<Map<String, Object>> filesResponse = Lists.newArrayList();
        for (AttachmentFile attachmentFile : attachmentFiles) {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("id", attachmentFile.getId());
            dataMap.put("attachmentName", "_attachment_" + attachmentFile.getEntityFileCategory());
            dataMap.put("name", attachmentFile.getFileRealName());
            dataMap.put("size", FileUtils.byteCountToDisplaySize(attachmentFile.getFileLength()));

            dataMap.put("url", request.getContextPath() + StringUtils.substringBefore(url, "/attachmentList.htm")
                    + "?id=" + entity.getId() + "&attachmentId=" + attachmentFile.getId());
            filesResponse.add(dataMap);
        }
        Map<String, List<Map<String, Object>>> response = Maps.newHashMap();
        response.put("files", filesResponse);
        return response;
    }
}
