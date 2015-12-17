package com.ufo.core.socket;

import org.xsocket.connection.INonBlockingConnection;

public interface IDataService {

    /** 
    * @param nbc 连接
    * @param str 已接收的数据
    * @param length 长度
    */
    public void parse(INonBlockingConnection nbc, String str, int length);

    /**  
     * 完成
    * @return
    */
    public boolean finsh();

}
