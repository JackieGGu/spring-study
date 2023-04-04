package cn.jackiegu.spring.security.boot.support;

import cn.jackiegu.spring.security.boot.model.UserEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 已认证用户
 *
 * @author JackieGu
 * @date 2021/7/7
 */
@Slf4j
@Getter
public class AuthenticatedUser {

    private static final String AUTHENTICATED_USER = "authenticated.user";

    private static final int SCOPE = RequestAttributes.SCOPE_REQUEST;

    private final Long id;

    private final String username;

    private final String realName;

    private final String nickName;

    public AuthenticatedUser(UserEntity user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.realName = user.getRealName();
        this.nickName = user.getNickName();
    }

    public static AuthenticatedUser getUser() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Object attribute = requestAttributes.getAttribute(AUTHENTICATED_USER, SCOPE);
        if (attribute != null) {
            return (AuthenticatedUser) attribute;
        }
        log.info("Creating AuthenticatedUser instance");
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();
        Assert.notNull(principal, "Principal must not be null");
        Assert.isInstanceOf(UserEntity.class, principal, "Principal instance type is incorrect");
        AuthenticatedUser user = new AuthenticatedUser((UserEntity) principal);
        requestAttributes.setAttribute(AUTHENTICATED_USER, user, SCOPE);
        return user;
    }
}
