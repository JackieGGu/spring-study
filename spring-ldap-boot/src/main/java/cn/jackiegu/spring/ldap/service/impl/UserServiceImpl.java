package cn.jackiegu.spring.ldap.service.impl;

import cn.jackiegu.spring.ldap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.LdapProperties;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.ldap.LdapName;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User Service Impl
 *
 * @author JackieGu
 * @date 2021/11/1
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private LdapProperties ldapProperties;

    @Autowired
    private LdapTemplate ldapTemplate;

    public Boolean login(String username, String password) {
        // 根据username查询条目
        LdapQuery query = LdapQueryBuilder.query()
            .base("ou=People")
            .filter(new EqualsFilter("uid", username));
        DirContextAdapter userDirContext;
        try {
            userDirContext = ldapTemplate.searchForObject(query, ctx -> (DirContextAdapter) ctx);
        } catch (EmptyResultDataAccessException e) {
            // username不存在
            return false;
        }
        // 查询arthas组所有member
        Filter filter = new EqualsFilter("objectClass", "groupOfUniqueNames");
        Set<String> members = ldapTemplate.search("ou=arthas,ou=Group", filter.encode(), (ContextMapper<List<String>>) ctx -> {
                DirContextAdapter dirContext = (DirContextAdapter) ctx;
                String[] uniqueMembers = dirContext.getStringAttributes("uniqueMember");
                return Arrays.asList(uniqueMembers);
            }).stream()
            .flatMap(Collection::stream)
            .map(item -> {
                LdapName ldapName = LdapUtils.newLdapName(item);
                ldapName = LdapUtils.removeFirst(ldapName, LdapUtils.newLdapName(ldapProperties.getBase()));
                return ldapName.toString();
            })
            .collect(Collectors.toSet());
        if (members.contains(userDirContext.getDn().toString())) {
            return ldapTemplate.authenticate(userDirContext.getDn(), new EqualsFilter("objectClass", "inetOrgPerson").encode(), password);
        }
        return false;
    }
}
