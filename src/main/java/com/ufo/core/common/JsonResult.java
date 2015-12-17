package com.ufo.core.common;

/** 
* 类名称：JsonResult 
* 类描述： 
* 
* 
* 创建人：HuZhiGang
* 创建时间：2012-3-26 下午04:05:49 
* @version 
* 
*/
public class JsonResult {
    //返回结果码
    private Integer ResultCode =0;
    //  返回值
    private Object ResultValue;
    //  异常
    private String ResultException ="";

   
    public Integer getResultCode() {
        return ResultCode;
    }

    public void setResultCode(Integer resultCode) {
        ResultCode = resultCode;
    }

    public Object getResultValue() {
        return ResultValue;
    }

    public void setResultValue(Object resultValue) {
        ResultValue = resultValue;
    }

    public String getResultException() {
        return ResultException;
    }

    public void setResultException(String resultException) {
        ResultException = resultException;
    }
}
