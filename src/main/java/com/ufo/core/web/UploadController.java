package com.ufo.core.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ufo.core.dto.FileInfoDTO;
import com.ufo.core.service.impl.ResourceContextHolder;
import com.ufo.core.utils.FileUtils;
import com.ufo.core.utils.ImageUtils;
import com.ufo.core.utils.JsonUtils;

@Controller("_uploadController")
public class UploadController {

    public final static String TMP_UPLOAD = "upload";

    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ResourceContextHolder resourceContextHolder;

//    @RequestMapping(value = "/swfupload.json", method = RequestMethod.POST)
//    public void swfupLoad(HttpServletRequest request, String control, String dir, HttpServletResponse resp)
//            throws IOException {
//        FileInfoDTO info = saveFile(request, control, dir);
//        String str = JsonUtils.toJson(info);
//        OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream(), Charset.forName("utf-8"));
//        writer.write(str);
//        writer.flush();
//    }
//
//    @RequestMapping(value = "/uploadify.json", method = RequestMethod.POST)
//    @ResponseBody
//    public FileInfoDTO uploadify(HttpServletRequest request, String control, String dir) throws IOException {
//        FileInfoDTO info = saveFile(request, control, dir);
//        return info;
//    }

    /** 
     * 支持html5和Multipart/方式的文件上传
    * @param request
    * @param control
    * @param dir
    * @return
    */
    @RequestMapping(value = "/upload.json", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(HttpServletRequest request, String control, String dir) {
        FileInfoDTO info = new FileInfoDTO();
        try {
            info = saveFile(request, control, dir);
        } catch (Exception e) {
            info.setError(1);
            info.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return info;
    }

    @RequestMapping("/remove.json")
    @ResponseBody
    public Object remove(HttpServletRequest request, String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(path)) {
            String root = resourceContextHolder.getUploadPath();
            File file = new File(root, path);
            if (file.exists()) {
                boolean delete = file.delete();
                String thumbPath = ImageUtils.toThumbPath(path);
                FileUtils.removeFile(FileUtils.normalizeFilePath(root + thumbPath));
                map.put("result", delete);
            } else {
                map.put("err", path + "file not found");
            }
        } else {
            map.put("err", "file is not empty");
        }
        return JsonUtils.toJson(map);
    }

    //保存文件
    private FileInfoDTO saveFile(HttpServletRequest request, String control, String dir) throws IOException {
        FileInfoDTO info = null;
        InputStream input = null;
        String name = null;
        String imgType = null;
        Integer width = null;
        Integer height = null;
        if (request instanceof MultipartHttpServletRequest) {
            if (StringUtils.isBlank(control)) {
                MultiValueMap<String, MultipartFile> map = ((MultipartHttpServletRequest)request).getMultiFileMap();
                for (String key : map.keySet()){
                    name = map.getFirst(key).getOriginalFilename();
                    String prefix = checkFile(name);
                    imgType = key;
                    if (key.equals("headFile")){
                        if ("jpg,gif,bmp,png".indexOf(prefix.toLowerCase()) == -1){
                            throw new RuntimeException("文件格式[jpg,gif,bmp,png]不正确");
                        }
                        if (map.getFirst(key).getSize() > resourceContextHolder.getHeadSize() * 1024 * 1024){
                            throw new RuntimeException("图片大小不能超过" + resourceContextHolder.getHeadSize() + "M");
                        }
                        width = resourceContextHolder.getHeadImageWidth();
                        height = resourceContextHolder.getHeadImageHeight();
                    } else if (key.equals("imgFile")){
                        if ("jpg,gif,bmp,png".indexOf(prefix.toLowerCase()) == -1){
                            throw new RuntimeException("文件格式[jpg,gif,bmp,png]不正确");
                        }
                        if (map.getFirst(key).getSize() > resourceContextHolder.getImageSize() * 1024 * 1024){
                            throw new RuntimeException("图片大小不能超过" + resourceContextHolder.getImageSize() + "M");
                        }
                    } else if (key.equals("drawingFile")){
                        if ("jpg,png".indexOf(prefix.toLowerCase()) == -1){
                            throw new RuntimeException("文件格式不正确");
                        }
                        if (map.getFirst(key).getSize() > resourceContextHolder.getImageSize() * 1024 * 1024){
                            throw new RuntimeException("图片大小不能超过" + resourceContextHolder.getImageSize() + "M");
                        }
                    } else if (key.equals("videoFile")){
                        if (("3gpp,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb").indexOf(prefix.toLowerCase()) == -1)
                            throw new RuntimeException("文件格式不正确");
                        if (map.getFirst(key).getSize() > resourceContextHolder.getVideoSize() * 1024 * 1024){
                            throw new RuntimeException("视频大小不能超过" + resourceContextHolder.getVideoSize() + "M");
                        }
                    }
                    input = map.getFirst(key).getInputStream();
                    break;
                }
            } else {
                MultipartFile src = ((MultipartHttpServletRequest) request).getFile(control);
                if (!src.isEmpty()) {
                    name = src.getOriginalFilename();
                    input = src.getInputStream();
                }
            }
        } else {
            String contentType = request.getContentType();
            //HTML5 文件上传
            if ("application/octet-stream".equals(contentType)) {
                String dispoString = request.getHeader("Content-Disposition");
                String[] arr = dispoString.split(";");
                arr = arr[arr.length - 1].split("=");
                name = arr[arr.length - 1].replaceAll("\"", "");
                input = request.getInputStream();
            }
        }

        if (StringUtils.isNotBlank(name) && input != null) {
            String prefix = checkFile(name);
            //生成目标文件
            ServletContext context = request.getSession().getServletContext();
            final String absolutePath = FileUtils.getAbsolutePath(context, TMP_UPLOAD);
            final String upath = FileUtils.normalizePath(absolutePath);
            String[] path = makeFile(upath, dir, prefix);
            if (logger.isDebugEnabled()) {
                logger.debug("上传文件为" + upath + path[0]);
            }
            //end
            File dest = new File(upath + path[0]);
            if (width == null || height == null){
                FileCopyUtils.copy(input, new FileOutputStream(dest));
            } else {
                ImageUtils.zoom(input, width, height, dest);
            }

            // 除头像外生成小图
            if (imgType.equals("imgFile") || imgType.equals("drawingFile")) {
                width = resourceContextHolder.getSmallImageWidth();
                height = resourceContextHolder.getSmallImageHeight();
                ImageUtils.zoom(upath + path[0], width, height, upath + path[1]);
            }

            info = new FileInfoDTO(name, "/" + TMP_UPLOAD + "/" + (path[0].startsWith("/") ? path[0].substring(1) : path[0]), dest.length(), prefix);
        }
        return info;
    }

    private String checkFile(String name) {
        final int idx = name.lastIndexOf(".");
        String prefix = "";
        if (idx != -1) {
            prefix = name.substring(idx + 1);
        }
        return prefix;
    }

    private String[] makeFile(String root, String dir, String prefix) throws IOException {
        StringBuffer path = new StringBuffer();
        //生成日期目录
        DateTime now = new DateTime();
        StringBuilder sb = new StringBuilder();
        int year = now.getYear();
        sb.append("/" + year);
        String month = "";
        int monthOfYear = now.getMonthOfYear();
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = "" + monthOfYear;
        }
        String day = "";
        int dayOfMonth = now.getDayOfMonth();
        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = "" + dayOfMonth;
        }
        sb.append("/" + month);
        sb.append("/" + day);
        final String separator = "/";

        path.append(separator);
        dir = StringUtils.trimToEmpty(dir);
        if (StringUtils.isNotBlank(dir)) {
            path.append(dir);
        }
        path.append(sb);
        path.append(separator);

        FileUtils.createDirectoryIfNecessary(root + path.toString());
        //end
        //生成文件名
        path.append(separator);
        String fileName = FileUtils.getRandomFileName(prefix);
//        path.append(fileName);

        // 0为原图，1为小图
        String[] ret = new String[2];
        ret[0] = (path + fileName).replaceAll("//", separator);
        // 小图路径
        path.append("small");
        path.append(separator);
        FileUtils.createDirectoryIfNecessary(root + path.toString());
        ret[1] = (path + fileName).replaceAll("//", separator);
        return ret;
    }
}
