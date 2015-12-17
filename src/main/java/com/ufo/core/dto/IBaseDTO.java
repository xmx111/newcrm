package com.ufo.core.dto;

public interface IBaseDTO {

    /** 
    * @return
    */
    public IUserDTO getOperation();

    /** 
    * @param operation
    */
    public void setOperation(IUserDTO operation);

    /** 
    * @return
    */
    public String getOperTime();

    /** 
    * @param operTime
    */
    public void setOperTime(String operTime);
}
