package com.ufo.core.dto;

/** 
* 类名称：FileInfoDTO 
* 类描述： 文件信息
* 
* 
* 创建人：Duzj
* 创建时间：2012-4-2 下午10:10:03 
* @version 
* 
*/
public class FileInfoDTO {
    private String name;
    private String url;
    private Long size;
    private String type;
    private Integer error = 0;
    private String message;

    public FileInfoDTO() {
    }
    

    public FileInfoDTO(String name, String url, Long size,String type) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}