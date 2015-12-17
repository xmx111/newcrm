package com.ufo.core.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.util.UrlPathHelper;

import com.ufo.core.service.impl.ResourceContextHolder;
import com.ufo.core.utils.FileUtils;

public class DownloadController extends AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ResourceContextHolder resourceContextHolder;
    private static UrlPathHelper urlPathHelper = new UrlPathHelper();

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        download(request, response);
        return null;
    }

    private void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // process url path
        String urlPath = urlPathHelper.getLookupPathForRequest(request);
        if (logger.isDebugEnabled()) {
            logger.debug("download urlPath: " + urlPath);
        }
        if (urlPath.startsWith("/")) {
            urlPath = urlPath.substring(1);
        }
        if (StringUtils.isEmpty(urlPath)) {
            return;
        }
        urlPath = FileUtils.normalizePath(urlPath);
        urlPath = urlPath.substring(0, urlPath.length() - 1);
        String downloadUrl = resourceContextHolder.getDownloadUrl();
        if (StringUtils.isNotBlank(downloadUrl)) {
            if (!urlPath.startsWith(downloadUrl)) {
                logger.error("the path is invalid:" + urlPath);
                return;
            }
            urlPath = urlPath.substring(downloadUrl.length());
        }
        ServletContext context = request.getSession().getServletContext();
        // real file name on disk
        String realFileName = FileUtils.getAbsolutePath(context, resourceContextHolder.getUploadPath()) + urlPath;
        if (!FileUtils.isFileExists(realFileName)) {
            logger.error(realFileName + " does not exist.");
            return;
        }

        // if file exists, then open it and read the content,send back to browse
        String shortFileName = FilenameUtils.getName(realFileName);
        String contentType = (FileUtils.isImagefile(realFileName)) ? "image/*" : "application/octet-stream";
        response.setContentType(contentType);

        // get ie version
        boolean isIe6 = (request.getHeader("User-Agent").indexOf("MSIE 6.0") > 0);
        if (isIe6) {
            response.setHeader("Content-Disposition", "filename=" + shortFileName);
        } else {
            response.setHeader("Content-Disposition", "attachment;filename=" + shortFileName);
        }

        // open file and read its content
        ServletOutputStream os = response.getOutputStream();
        File file = new File(realFileName);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[40960];
        int size = 0;
        while ((size = fis.read(buffer)) != -1) {
            os.write(buffer, 0, size);
        }
        fis.close();
        os.flush();
        os.close();
        response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
    }
}
