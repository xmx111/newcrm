package com.ufo.config.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ufo.config.sys.entity.AttachmentFile;
import com.ufo.config.sys.service.interfaces.IAttachmentFileService;
import com.ufo.core.annotation.Description;
import com.ufo.core.context.RuntimeContext;
import com.ufo.core.dto.FileInfoDTO;
import com.ufo.core.service.IBaseSpringDataService;
import com.ufo.core.service.impl.ResourceContextHolder;
import com.ufo.core.utils.ServletUtils;
import com.ufo.core.web.PersistableController;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller("configSysAttachmentFileController")
@RequestMapping("/config/sys/attachmentfile/")
@Description(code = "system.attachmentfile", value = "附件处理")
@Secured("system.attachmentfile")
public class AttachmentFileController extends PersistableController<AttachmentFile, String> {

    private final Logger logger = LoggerFactory.getLogger(AttachmentFileController.class);

    @Autowired
    private IAttachmentFileService attachmentFileService;

    @Autowired ResourceContextHolder resourceContextHolder;

    private MultipartFile[] files;

    private String[] filesFileName;

    @Override
    protected IBaseSpringDataService<AttachmentFile, String> getEntityService() {
        return attachmentFileService;
    }

    protected void checkEntityAclPermission(AttachmentFile entity) {
        //Do nothing check
    }

    @RequestMapping("delete.json")
    @ResponseBody
    public Object delete(HttpServletRequest request) {
        return super.delete(request);
    }

    private void initRequestFiles(MultipartHttpServletRequest request){
        MultiValueMap<String, MultipartFile> map = request.getMultiFileMap();
        files = new MultipartFile[map.size()];
        filesFileName = new String[map.size()];
        Integer idx = 0;
        for (String name : map.keySet()){
            filesFileName[idx] = map.getFirst(name).getOriginalFilename();
            files[idx] = map.getFirst(name);
        }
    }

    private AttachmentFile saveAttachmentFile(int idx) throws Exception {
        MultipartFile file = files[idx];
        AttachmentFile entity = AttachmentFile.buildInstance(file);
        entity.setFileRealName(filesFileName[idx]);
        entity.setFileType(file.getContentType());
        entity.setFileExtension(StringUtils.substringAfterLast(entity.getFileRealName(), "."));

        String rootPath = resourceContextHolder.getFileUploadRootDir();
        File diskFileDir = new File(rootPath + entity.getFileRelativePath());
        if (!diskFileDir.exists()) {
            diskFileDir.mkdirs();
        }

        File diskFile = new File(rootPath + entity.getFileRelativePath() + File.separator + entity.getDiskFileName());
        InputStream input = file.getInputStream();

        if (input != null) {
            if (logger.isDebugEnabled()) {
                logger.debug("上传文件为: {}", diskFile.getAbsolutePath());
            }
            FileCopyUtils.copy(input, new FileOutputStream(diskFile));
        }
        logger.debug("上传文件到数据库: {}", entity.getFileRealName());
        attachmentFileService.save(entity);
        return entity;
    }

    @RequestMapping("uploadMulti.json")
    @ResponseBody
    public Object uploadMulti(HttpServletRequest request) {
        String attachmentName = getRequiredParameter(request, "attachmentName");
        if (request instanceof MultipartHttpServletRequest) {
            initRequestFiles((MultipartHttpServletRequest)request);
        } else {
            throw new RuntimeException("上传出错，不是文件");
        }
        List<Map<String, Object>> filesResponse = Lists.newArrayList();
        for (int i = 0; i < files.length; i++) {
            Map<String, Object> dataMap = Maps.newHashMap();
            try {
                AttachmentFile entity = saveAttachmentFile(i);
                dataMap.put("id", entity.getId());
                dataMap.put("attachmentName", attachmentName);
                dataMap.put("name", filesFileName[i]);
                dataMap.put("size", FileUtils.byteCountToDisplaySize(entity.getFileLength()));
                dataMap.put("url", request.getContextPath() + "/config/sys/attachmentfile/download.json?id=" + entity.getId());
            } catch (Exception e) {
                logger.warn("entity delete failure", e);
                dataMap.put("name", filesFileName[i]);
                dataMap.put("error", e.getMessage());
            }
            filesResponse.add(dataMap);
        }
        Map<String, List<Map<String, Object>>> response = Maps.newHashMap();
        response.put("files", filesResponse);
        return response;

    }

    @RequestMapping("download.json")
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response) {
        try {
            AttachmentFile entity = attachmentFileService.findOne(this.getRequiredParameter(request, "id"));
            //附件访问控制：未关联附件才允许通用访问下载，已关联附件只能通过各业务对象入口访问
            Assert.isTrue(StringUtils.isBlank(entity.getEntityId()));
            ServletUtils.setFileDownloadHeader(response, entity.getFileRealName());
            response.setContentType(entity.getFileType());
            String rootPath = resourceContextHolder.getFileUploadRootDir();
            File diskFile = new File(rootPath + entity.getFileRelativePath() + File.separator
                    + entity.getDiskFileName());

            ServletUtils.renderFileDownload(response, FileUtils.readFileToByteArray(diskFile));
        } catch (Exception e) {
            logger.error("Download file error", e);
        }
    }

    @RequestMapping("deleteOne.json")
    @ResponseBody
    public Object deleteOne(HttpServletRequest request) {
        AttachmentFile entity = attachmentFileService.findOne(this.getRequiredParameter(request, "id"));
        List<Map<String, Object>> filesResponse = Lists.newArrayList();
        if (entity != null && entity.getEntityId() == null) {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put(entity.getFileRealName(), true);
            attachmentFileService.delete(entity);
            filesResponse.add(dataMap);
        }
        Map<String, List<Map<String, Object>>> response = Maps.newHashMap();
        response.put("files", filesResponse);
        return response;
    }

}
