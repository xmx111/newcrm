package com.ufo.core.security;

import com.ufo.config.sys.dao.interfaces.ManagerDao;
import com.ufo.config.sys.entity.Authority;
import com.ufo.config.sys.entity.Manager;
import com.ufo.config.sys.entity.Role;
import com.ufo.core.entity.UndeleteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private ManagerDao managerDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        
        List<Manager> list = managerDao.findByDeletedAndLoginName(UndeleteEntity.DeleteTypeEnum.UNDELETE, username);
        if (list.size() != 1) {
            throw new UsernameNotFoundException(username + "用户不存在!");
        } else {
            final Manager model = list.get(0);
            if (!model.getStatus().equals(Manager.ManagerStatusEnum.NORMAL))
                throw new InvalidDataAccessResourceUsageException(username + "用户已" + model.getStatus().name() + "!");
            Set<Role> roles = model.getRoles();
            Set<String> set = new HashSet<String>();
            for (Role role : roles) {
                Set<Authority> authorities = role.getAuthorities();
                for (Authority authority : authorities) {
                    set.add(authority.getCode());
                }
            }
            Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
            for (String key : set) {
                authorities.add(new SimpleGrantedAuthority(key));
            }
            model.setAuthorities(authorities);
            return model;
        }
    }

}
