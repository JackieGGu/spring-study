package cn.jackiegu.spring.security.boot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 用户实体类
 *
 * @author JackieGu
 * @date 2021/7/6
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {

    private static final long serialVersionUID = -4088051533849759179L;

    private static final String SUPER_ADMIN = "admin";

    private Long id;

    private String username;

    private String password;

    private String realName;

    private String nickName;

    private Boolean expired;

    private Boolean locked;

    private Boolean disabled;

    private Set<SimpleGrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        // if (SUPER_ADMIN.equals(username)) {
        //     authorities.add(new SimpleGrantedAuthority("SUPER_USER"));
        // } else {
        //     authorities.add(new SimpleGrantedAuthority("GUEST"));
        // }
        // return authorities;

        return authorities;
    }

    /**
     * 账号是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    /**
     * 账号是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    /**
     * 凭证(密码)是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     */
    @Override
    public boolean isEnabled() {
        return !disabled;
    }
}
