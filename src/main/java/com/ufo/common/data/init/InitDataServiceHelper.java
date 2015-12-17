package com.ufo.common.data.init;

import java.util.HashSet;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.config.sys.dao.interfaces.ManagerDao;
import com.ufo.config.sys.dao.interfaces.RoleDao;
import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.entity.Role;
import com.ufo.config.sys.service.impl.AuthorityServiceHelper;
import com.ufo.core.common.Paginator;

/** 
* 类名称：InitDataService 
* 类描述： 初始化数据 
* 
* 创建人：Duzj
* 创建时间：2012-12-5 下午3:02:19 
* @version 
* 
*/
@Service("initDataServiceHelper")
public class InitDataServiceHelper {
    private Logger logger = LoggerFactory.getLogger(InitDataServiceHelper.class);
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private AuthorityServiceHelper authorityServiceHelper;
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private InitTestDataService initTestDataService;

    @Transactional(readOnly = false)
    public void init() {
        Paginator paginator = new Paginator();
        paginator.setPageSize(2);
        Iterator<Manager> it = managerDao.findAll().iterator();
        if (it.hasNext()) {
            return;
        }
        //主版本.初始化所有数据.包括测试数据,如果有特殊处理的,
        //请在分支版本上修改.
        Role role = initRole();
        initAuthority(role);        
        initManager(role);
        //添加测试数据.请放在最后.
//        initAuthorityTelAndOrder();
//        initTestDataService.init();
    }

    /** 
    * 初始化角色数据
     * @return 
    */
    private Role initRole() {
        try {
            Role role = new Role();
            role.setUsers(new HashSet<Manager>(1));
            final String admin = "管理员";
            role.setCode(admin);
            role.setName(admin);
            roleDao.save(role);
            return role;
        } catch (Exception e) {
            logger.error("初始化角色数据出错!", e);
        }
        return null;
    }

    /** 
    * 初始化权限数据
    */
    public void initAuthority(Role role) {
        try {
            authorityServiceHelper.initAuthoritiesByResources(role);
        } catch (Exception e) {
            logger.error("初始化权限数据出错!", e);
        }
    }

    /** 
    * 初始化操作员数据
    */
    private void initManager(Role role) {
        try {
            Manager manager = new Manager();
            manager.setRoles(new HashSet<Role>(1));
            final String name = "admin";
            manager.setLoginName(name);
            manager.setPassword(passwordEncoder.encodePassword("123456", null));
            manager.setName("管理员");
            manager.getRoles().add(role);
            managerDao.save(manager);
        } catch (Exception e) {
            logger.error("初始化操作员数据出错!", e);
        }
    }

    /** 
    * 初始化权限数据
    */
    public void initAuthorityTelAndOrder() {
        try {
            authorityServiceHelper.initTelAndOrder();
        } catch (Exception e) {
            logger.error("初始化权限数据出错!", e);
        }
    }

}
