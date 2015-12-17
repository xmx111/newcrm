package com.ufo.core.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ResourceContextHolder {

    private final Logger logger = LoggerFactory.getLogger(ResourceContextHolder.class);

//    @Value("${resource.path}")
    private String uploadPath = "files";

//    @Value("${resource.downloadPath}")
    private String downloadPath = "";

    @Value("${web.domain}")
    private String webDomain;

    @Value("${image.size}")
    private Integer imageSize;

    @Value("${video.size}")
    private Integer videoSize;

    @Value("${headimage.size}")
    private Integer headSize;

    @Value("${headimage.width}")
    private Integer headImageWidth;

    @Value("${headimage.height}")
    private Integer headImageHeight;

    @Value("${smallimage.width}")
    private Integer smallImageWidth;

    @Value("${smallimage.height}")
    private Integer smallImageHeight;
    
    
    //版本配置信息android
    @Value("${code_kh_android}")
    private String code_kh_android;
    @Value("${content_kh_android}")
    private String content_kh_android;
    @Value("${url_kh_android}")
    private String url_kh_android;

    @Value("${code_cj_android}")
    private String code_cj_android;
    @Value("${content_cj_android}")
    private String content_cj_android;
    @Value("${url_cj_android}")
    private String url_cj_android;
    
    @Value("${code_wl_android}")
    private String code_wl_android;
    @Value("${content_wl_android}")
    private String content_wl_android;
    @Value("${url_wl_android}")
    private String url_wl_android;
    
    //版本配置信息ios
    @Value("${code_kh_ios}")
    private String code_kh_ios;
    @Value("${content_kh_ios}")
    private String content_kh_ios;
    @Value("${url_kh_ios}")
    private String url_kh_ios;

    @Value("${code_cj_ios}")
    private String code_cj_ios;
    @Value("${content_cj_ios}")
    private String content_cj_ios;
    @Value("${url_cj_ios}")
    private String url_cj_ios;
    
    @Value("${code_wl_ios}")
    private String code_wl_ios;
    @Value("${content_wl_ios}")
    private String content_wl_ios;
    @Value("${url_wl_ios}")
    private String url_wl_ios;


    /** 
     * 上传文件根目录 
    * @return
    */
    public String getUploadPath() {
        return StringUtils.isBlank(uploadPath) ? "files" : uploadPath;
    }

    /** 
     * 下载文件路径 
    * @return
    */
    public String getDownloadUrl() {
        return StringUtils.trimToEmpty(downloadPath);
    }

    private static String staticFileUploadDir;

    /**
     * 获取文件上传根目录：优先取cfg.file.upload.dir参数值，如果没有定义则取当前用户主目录${user.home}/attachments
     * @return
     */
    public String getFileUploadRootDir() {
        if (staticFileUploadDir == null) {
            staticFileUploadDir = getUploadPath();
            if (org.apache.commons.lang3.StringUtils.isBlank(staticFileUploadDir)) {
                staticFileUploadDir = System.getProperty("user.home") + File.separator + "attachments";
            }
            if (staticFileUploadDir.endsWith(File.separator)) {
                staticFileUploadDir = staticFileUploadDir.substring(0, staticFileUploadDir.length() - 2);
            }
            logger.info("Setup file upload root dir:  {}", staticFileUploadDir);
        }
        return staticFileUploadDir;
    }
    
    /**
     * 替换富文本框上传图片路径
     * @param content
     * @return
     */
    public String replaceContent(String content){
        if(StringUtils.isNotBlank(content)){
            content=content.replaceAll("/upload/", getWebDomain()+"/upload/");
        }
        return content;
    }

    public String getWebDomain() {
        return StringUtils.isBlank(uploadPath) ? "http://localhost" : webDomain;
    }

    public Integer getImageSize() {
        return imageSize == null || imageSize != Integer.MIN_VALUE ? 20 : imageSize;
    }

    public Integer getVideoSize() {
        return videoSize == null || videoSize != Integer.MIN_VALUE ? 20 : videoSize;
    }

    public Integer getHeadSize() {
        return headSize == null || headSize != Integer.MIN_VALUE ? 20 : headSize;
    }

    public Integer getHeadImageHeight() {
        return headImageHeight;
    }

    public Integer getHeadImageWidth() {
        return headImageWidth;
    }

    public Integer getSmallImageWidth() {
        return smallImageWidth;
    }

    public Integer getSmallImageHeight() {
        return smallImageHeight;
    }

    public String getCode_kh_android() {
        return code_kh_android;
    }

    public String getContent_kh_android() {
        return content_kh_android;
    }

    public String getUrl_kh_android() {
        return url_kh_android;
    }

    public String getCode_cj_android() {
        return code_cj_android;
    }

    public String getContent_cj_android() {
        return content_cj_android;
    }

    public String getUrl_cj_android() {
        return url_cj_android;
    }

    public String getCode_wl_android() {
        return code_wl_android;
    }

    public String getContent_wl_android() {
        return content_wl_android;
    }

    public String getUrl_wl_android() {
        return url_wl_android;
    }

    public String getCode_kh_ios() {
        return code_kh_ios;
    }

    public String getContent_kh_ios() {
        return content_kh_ios;
    }

    public String getUrl_kh_ios() {
        return url_kh_ios;
    }

    public String getCode_cj_ios() {
        return code_cj_ios;
    }

    public String getContent_cj_ios() {
        return content_cj_ios;
    }

    public String getUrl_cj_ios() {
        return url_cj_ios;
    }

    public String getCode_wl_ios() {
        return code_wl_ios;
    }

    public String getContent_wl_ios() {
        return content_wl_ios;
    }

    public String getUrl_wl_ios() {
        return url_wl_ios;
    }
    
    
}
