package cn.jackiegu.spring.security.boot.service.impl;

import cn.jackiegu.spring.security.boot.dao.UserDao;
import cn.jackiegu.spring.security.boot.model.UserEntity;
import cn.jackiegu.spring.security.boot.service.UserRoleService;
import cn.jackiegu.spring.security.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户Service实现
 *
 * @author JackieGu
 * @date 2021/7/2
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final List<UserDetails> userCache = new ArrayList<>();

    static {
        UserDetails admin = User
            .withUsername("admin")
            .password("$2a$10$a0xjcsh/OadU6n2GUQ5cf.TMRs9QXUT7pbRRjs1DITcceuoDYNsaO")
            .authorities("SUPER_USER")
            .build();
        UserDetails guest = User
            .withUsername("guest")
            .password("$2a$10$HmEfzHMjjaxnxd4zBzw1DONM8Up9RvwY59gMt1ztXtZz1LzCBJPde")
            .authorities("GUEST")
            .build();
        userCache.add(admin);
        userCache.add(guest);
    }

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从内存缓存中加载用户
        // UserDetails userDetails = userCache
        //     .stream()
        //     .filter(item -> username.equals(item.getUsername()))
        //     .findFirst().orElse(null);

        // 从数据库中加载用户
        UserEntity user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        // 从数据库中加载权限
        List<String> roleCodes = userRoleService.listUserRoleCodes(user.getId());
        Set<SimpleGrantedAuthority> authorities = roleCodes
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
        user.setAuthorities(authorities);
        return user;
    }
}
