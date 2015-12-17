package com.ufo.core.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* 类名称：ImageUtils 
* 类描述： 图片工具类
* 
* 
* 创建人：Duzj
* 创建时间：2012-3-22 上午10:12:15 
* @version 
* 
*/
public class ImageUtils {
    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);
    private final static String THUMB_PREFIX = "thumb_";
    private final static int THUMB_WIDTH = 150;
    private final static int THUMB_HEIGHT = 210;
    static {
        //Linux 下图形处理
        try {
            System.setProperty("java.awt.headless", "true");
        } catch (Exception e) {
        }
    }

    /**
     * @param inputStream
     * @param width
     * @param destPath
     */
    public static void zoom(InputStream inputStream, int width, int height, File destPath) {
        try {
            BufferedImage src = javax.imageio.ImageIO.read(inputStream);
            double w = src.getWidth();
            double h = src.getHeight();
            int dw = 0, dh = 0;
            if (width != 0 && height != 0) {
                //宽度缩小
                if (w > width) {
                    dw = width;
                    dh = (int) (width / (w / h));
                }
                //高度缩小
                if (h > height || dh > height) {
                    dh = height;
                    dw = (int) (height / (h / w));
                }
            } else {
                dw = THUMB_WIDTH;
                dh = THUMB_HEIGHT;
            }
            dw = dw == 0 ? (int) w : dw;
            dh = dh == 0 ? (int) h : dh;

            BufferedImage tag = new BufferedImage(dw, dh, BufferedImage.TYPE_INT_RGB);
            tag.createGraphics();
            tag.getGraphics().drawImage(src.getScaledInstance(dw, dh, Image.SCALE_SMOOTH), 0, 0, null);
            FileOutputStream out = new FileOutputStream(destPath);
            int pos = destPath.getName().lastIndexOf(".");
            String format = destPath.getName().substring(pos + 1);
            javax.imageio.ImageIO.write(tag, format, out);
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /** 
    * @param srcPath
    * @param width
    * @param height
    * @param destPath
    */
    public static void zoom(String srcPath, int width, int height, String destPath) {
        try {
            File file = new File(srcPath);
            if (!file.exists()) {
                throw new FileNotFoundException(srcPath + " file not found!");
            }
            BufferedImage src = javax.imageio.ImageIO.read(file);
            double w = src.getWidth();
            double h = src.getHeight();
            int dw = 0, dh = 0;
            if (width != 0 && height != 0) {
                //宽度缩小
                if (w > width) {
                    dw = width;
                    dh = (int) (width / (w / h));
                }
                //高度缩小
                if (h > height || dh > height) {
                    dh = height;
                    dw = (int) (height / (h / w));
                }
            } else {
                dw = THUMB_WIDTH;
                dh = THUMB_HEIGHT;
            }
            dw = dw == 0 ? (int) w : dw;
            dh = dh == 0 ? (int) h : dh;

            BufferedImage tag = new BufferedImage(dw, dh, BufferedImage.TYPE_INT_RGB);
            tag.createGraphics();
            tag.getGraphics().drawImage(src.getScaledInstance(dw, dh, Image.SCALE_SMOOTH), 0, 0, null);
            FileOutputStream out = new FileOutputStream(destPath);
            int pos = srcPath.lastIndexOf(".");
            String format = srcPath.substring(pos + 1);
            javax.imageio.ImageIO.write(tag, format, out);
            out.flush();
            out.close();
            out = null;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /** 
     * 原图片路径转为缩略图路径
    * @param path
    * @return
    */
    public static String toThumbPath(final String path) {
        if (StringUtils.isBlank(path)) {
            return StringUtils.EMPTY;
        }
        if (path.indexOf("/" + THUMB_PREFIX) != -1) {
            return path;
        }
        String[] arr = path.split("/");
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                buf.append(arr[i]);
                buf.append("/");
            } else {
                buf.append(THUMB_PREFIX).append(arr[i]);
            }
        }
        return buf.toString();
    }

    /** 
     * 是否为缩略图文件
    * @param filePath
    * @return
    */
    public static boolean isThumbPath(String filePath) {
        int begin = filePath.lastIndexOf("/");
        String fname = filePath.substring(begin == -1 ? 0 : begin + 1);
        return fname.contains(THUMB_PREFIX);
    }

    /** 
     * 缩略图地址  file/2012/thumb_aa.jpg  返回原文件地址 file/2012/aa.jpg
    * @param filePath
    * @return
    */
    public static String toOriginPath(String filePath) {
        int begin = filePath.lastIndexOf("/");
        String fname = filePath.substring(begin == -1 ? 0 : begin + 1);
        return filePath.substring(0, begin == -1 ? 0 : begin + 1) + fname.replaceFirst(THUMB_PREFIX, "");
    }
}
