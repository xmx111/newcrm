package com.ufo.config.sys.service.impl;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.ufo.config.sys.service.interfaces.IAuthorityService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ufo.common.data.init.AuthorityResources;
import com.ufo.config.sys.dao.interfaces.AuthorityDao;
import com.ufo.config.sys.dao.interfaces.ManagerDao;
import com.ufo.config.sys.dao.interfaces.RoleDao;
import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.entity.Role;
import com.ufo.core.security.SecurityContext;
import com.ufo.core.service.BaseService;
import com.ufo.core.utils.NumberUtils;

/** 
* 类名称：AuthorityServiceHelper 
* 类描述：权限辅助类 
* 
* 创建人：Duzj
* 创建时间：2012-12-5 下午5:23:03 
* @version 
* 
*/
@Service
public class AuthorityServiceHelper extends BaseService {
    @Autowired
    private ManagerDao managerDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private AuthorityDao authorityDao;
    @Autowired
    private RoleDao roleDao;

    /** 
    * 初如化所有权限
    */
    public void initAuthorities(Role role) {
        final SecurityContext context = SecurityContext.instance();
        List<String> authorities = context.getAuthoritiesCode();
        Sort sort = new Sort("parent.id", "sequence");
        List<Authority> list = authorityDao.findAll(null, sort);
        Map<String, Authority> map = new HashMap<String, Authority>(authorities.size());
        for (Authority authority : list) {
            map.put(authority.getCode(), authority);
        }
        Locale local = Locale.CHINA;
        String fName = "functions_" + local.getLanguage() + "_" + local.getCountry() + ".properties";
        URL url = getClass().getClassLoader().getResource(fName);
        try {
            if (null != url) {
                URI uri = url.toURI();
                List<?> functions = FileUtils.readLines(new File(uri), "UTF-8");
                for (int i = 0; i < functions.size(); i++) {
                    String line = (String) functions.get(i);
                    if (StringUtils.isNotBlank(line) && !line.startsWith("#")) {
                        String function = line.split("=")[0].trim();
                        String code = function.substring(function.indexOf(".") + 1);
                        authorities.add(code);
                    }
                }
            }
        } catch (Exception e) {
            //
        }
        Collections.sort(authorities);
        int seq = 1;
        for (String code : authorities) {
            if (StringUtils.isBlank(code)) {
                continue;
            }
            Authority model = map.containsKey(code) ? map.get(code) : new Authority();
            model.setCode(code);
            if (model.getUpdateTime() == null) {
                String name = context.getDesc(code);
                if (StringUtils.isBlank(name)) {
                    final String _code = "function." + code;
                    name = messageSourceHelper.getMessage(_code);
                    name = _code.equals(name) ? null : name;
                }
                model.setName(StringUtils.isNotBlank(name) ? name : code);
            }
            if (code.indexOf(".") != -1) {
                String key = code.substring(0, code.lastIndexOf("."));
                Authority parent = map.get(key);
                model.setParent(parent);
                if (parent != null) {
                    parent.getChildren().add(model);
                    int idx = parent.getChildren().size();
                    model.setSequence(idx);
                }
            } else {
                model.setSequence(NumberUtils.isEmpty(model.getSequence()) ? seq : model.getSequence());
                seq++;
            }
            authorityDao.save(model);
            map.put(code, model);
            list.add(model);
        }
        if (null != role) {
            role.setAuthorities(new HashSet<Authority>(list));
            roleDao.save(role);
        }
    }
    

    /** 
    * 初始化所有权限
    */
    public void initAuthoritiesByResources(Role role) {
        
        List<Authority> authorities = AuthorityResources.getAll();
        List<Authority> all = new ArrayList<Authority>();

        authorityService.removeAuthority();

        Sort sort = new Sort("parent.id", "sequence");
        List<Authority> list = authorityDao.findAll(null, sort);
        Map<String, Authority> map = new HashMap<String, Authority>(authorities.size());
        for (Authority authority : list) {
            map.put(authority.getCode(), authority);
        }

        for (Authority authority : authorities){
            List<Authority> childs = authority.getChildren();
            if (map.get(authority.getCode()) == null) {
                authority = this.save(authority, map);
                all.add(authority);
            }
            for (Authority child : childs){
                List<Authority> childsThds = child.getChildren();
                if (map.get(child.getCode()) == null) {
                    child = this.save(child, map);
                    all.add(child);
                }
                for (Authority childThd : childsThds){
                    List<Authority> childsFours = childThd.getChildren();
                    if (map.get(childThd.getCode()) == null) {
                        childThd = this.save(childThd, map);
                        all.add(childThd);
                    }
                    for (Authority childsFour : childsFours){
                        if (map.get(childsFour.getCode()) == null) {
                            childsFour = this.save(childsFour, map);
                            all.add(childsFour);
                        }
                    }
                }
            }
        }
        
        if (null != role) {
            Set<Authority> set = role.getAuthorities();
            if (set != null)
                set.addAll(new HashSet<Authority>(all));
            else
                set = new HashSet<Authority>(all);
            role.setAuthorities(set);
            roleDao.save(role);
        }
    }
    
    private Authority save(Authority authority, Map<String, Authority> map){
        if (authority.getParent() != null)
            authority.setParent(authorityDao.findByCode(authority.getParent().getCode()));
        return authorityDao.save(authority);
    }
    
    public void initTelAndOrder(){
        Role tel = new Role();
        tel.setName("话务员");
        tel.setCode("话务员");
        tel = roleDao.save(tel);
        tel.setAuthorities(changeListToAuthoritySet(AuthorityResources.getTelList()));
        roleDao.save(tel);
        
        Role order = new Role();
        order.setName("预订员");
        order.setCode("预订员");
        order = roleDao.save(order);
        order.setAuthorities(changeListToAuthoritySet(AuthorityResources.getOrderList()));
        roleDao.save(order);
        
        Manager managerTel = new Manager();
        managerTel.setRoles(new HashSet<Role>(1));
        managerTel.setLoginName("hwy");
        managerTel.setPassword(passwordEncoder.encodePassword("123456", null));
        managerTel.setName("话务员");
        managerTel = managerDao.save(managerTel);
        managerTel.getRoles().add(tel);
        managerDao.save(managerTel);
        
        Manager managerOder = new Manager();
        managerOder.setRoles(new HashSet<Role>(1));
        managerOder.setLoginName("ydy");
        managerOder.setPassword(passwordEncoder.encodePassword("123456", null));
        managerOder.setName("预订员");
        managerOder = managerDao.save(managerOder);
        managerOder.getRoles().add(order);
        managerDao.save(managerOder);
    }
    
    private Set<Authority> changeListToAuthoritySet(List<String> list){
        Set<Authority> set = new HashSet<Authority>();
        for (String code : list){
            Authority authority = authorityDao.findByCode(code);
            if (authority == null)
                System.out.println(code);
            else
                set.add(authorityDao.findByCode(code));
        }
        return set;
    }

}
