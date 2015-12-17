package com.ufo.core.context;

/**
 * 类名称:
 * 类描述:
 * <p/>
 * 创建人: hekang
 * 创建时间: 下午3:32
 *
 * @verion 1.0
 */
public class ApplicationContext {

    private String realPath;

    private static ApplicationContext context;

    private ApplicationContext(){

    }

    public static ApplicationContext getInstance(){
        if (context == null)
            context = new ApplicationContext();
        return context;
    }

    public String getRealPath(){
        return this.realPath;
    }

    public void setRealPath(String realPath){
        this.realPath = realPath;
    }

}
