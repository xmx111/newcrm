package com.ufo.config.sys.service.interfaces;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.service.IBaseSpringDataService;

public interface IManagerService extends IBaseSpringDataService<Manager, Integer> {

    /**
     * 登录
     * @param loginName
     * @param password
     * @return
     */
    public String login(String loginName, String password);
    
    /**
     * 检查token
     * @param loginName
     * @param token
     * @return
     */
    public String checkToken(String loginName, String token);
    
    /**
     * 清除token
     * @param loginName
     * @return
     */
    public void cleanToken(String loginName);
    
    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param cfmPwd
     * @return
     * @throws Exception
     */
    public Manager editPwd(String oldPwd, String newPwd, String cfmPwd) throws Exception;

    /**
     * 锁定用户
     * @param ids
     */
    public void lock(String[] ids);

    /**
     * 注销用户
     * @param ids
     */
    public void forbid(String[] ids);

    /**
     * 启用用户
     * @param ids
     */
    public void active(String[] ids);

    /**
     * 检查密码
     * @return
     */
    public Boolean checkPassword(String password);

    /**
     * 查找用户
     * @param loginName
     * @return
     */
    public Manager findByLoginName(String loginName);
}