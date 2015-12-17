package com.ufo.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class to file relative functionalities.
 * 
 */
public class FileUtils {
    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Return absolute path of specified path, which relatives to the root path
     * of web application.
     * 
     * @param request
     * @param relativePath
     * @return
     */
    public static String getAbsolutePath(HttpServletRequest request, String relativePath) {
        return getAbsolutePath(request.getSession().getServletContext(), relativePath);
    }

    /**
     * Return absolute path of specified path, which relatives to the root path
     * of web application.
     * 
     * @param request
     * @param relativePath
     * @return
     */
    public static String getAbsolutePath(ServletContext context, String relativePath) {
        String prefix = getAbsolutePathPrefix(context, relativePath);
        return normalizePath(prefix + (StringUtils.isBlank(relativePath) ? "" : relativePath));
    }

    public static String getAbsolutePathPrefix(HttpServletRequest request, String relativePath) {
        return getAbsolutePathPrefix(request.getSession().getServletContext(), relativePath);
    }

    public static String getAbsolutePathPrefix(ServletContext context, String relativePath) {
        // it's an abstract path already, so return it directly
        if ((relativePath.indexOf(":") >= 0) || (relativePath.startsWith("/"))) {
            return "";
        }

        return normalizePath(context.getRealPath("/"));
    }

    /**
     * Convert \ as / and remove the repeated /, add / at the end of path
     * 
     * @param path
     * @return
     */
    public static String normalizePath(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        path = normalizeFilePath(path);
        return (path.endsWith("/") ? path : path + "/");
    }

    public static String normalizeFilePath(String path) {
        if (StringUtils.isBlank(path)) {
            return path;
        }
        path = path.replace("\\", "/").replace("\\", "/").replace("//", "/").replace("//", "/");
        return path;
    }

    /**
     * Check if the specified dir exists, if it doesn't, then create it.
     * 
     * @param dir
     * @throws IOException
     */
    public static void createDirectoryIfNecessary(String dir) throws IOException {
        File f = new File(dir);
        if (!f.exists()) {
            logger.debug("creating dir:" + dir);
            f.mkdirs();
        } else {
            logger.debug(dir + " already exists");
        }
    }

    public static void removeFile(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            logger.debug(dir + " does not exist");

        } else {
            f.delete();
            logger.debug("remove " + dir + " successfully");
        }
    }

    /**
     * Returns a uuid random file name with specified extension.
     * @param extension
     * @return
     */
    public static String getRandomFileName(String extension) {
        extension = StringUtils.isBlank(extension) ? "" : (extension.startsWith(".") ? extension : "." + extension);
        return UUID.randomUUID().toString() + extension;
    }

    /**
     * Return a file is exists
     * @param file
     * @return
     */
    public static boolean isFileExists(String file) {
        File f = new File(file);
        return f.exists();
    }

    /**
     * Return if the file is an image
     * @param file
     * @return
     */
    public static boolean isImagefile(String file) {
        String extension = FilenameUtils.getExtension(file).toLowerCase();
        return (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("bmp")
                || extension.equals("gif") || extension.equals("png"));
    }
}
