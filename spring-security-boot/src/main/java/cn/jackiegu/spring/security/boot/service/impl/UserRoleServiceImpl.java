package cn.jackiegu.spring.security.boot.service.impl;

import cn.jackiegu.spring.security.boot.dao.UserRoleDao;
import cn.jackiegu.spring.security.boot.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色Service实现
 *
 * @author JackieGu
 * @date 2021/7/8
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<String> listUserRoleCodes(Long userId) {
        return userRoleDao.findRoleCodeByUserId(userId);
    }
}
