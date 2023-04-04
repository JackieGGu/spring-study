package cn.jackiegu.spring.security.boot.config;

import cn.jackiegu.spring.security.boot.model.RolePermissionDO;
import cn.jackiegu.spring.security.boot.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * spring security 配置(相当于spring-security.xml)
 *
 * @author JackieGu
 * @date 2021/7/2
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private List<RolePermissionDO> permissions;

    @Autowired
    private RolePermissionService rolePermissionService;

    @PostConstruct
    public void init() {
        this.permissions = rolePermissionService.listPermissionRoles();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用CSRF攻击检测
        http.csrf().disable();

        /*
         * 设置Session管理策略
         * ALWAYS: 总是创建Session, 不管是否需要
         * IF_REQUIRED: 如果需要才创建Session, 默认值
         * NEVER: 不会主动创建Session, 但是如果在其他地方已创建Session则将会使用它
         * STATELESS: 不会主动创建Session, 也不使用任何Session
         */
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        // 静态权限控制配置
        // http.authorizeRequests()
        //     // 对根和自定义登录页资源取消权限验证
        //     .antMatchers("/", "/login").permitAll()
        //     // 对该资源设置需要登录权限验证
        //     .antMatchers("/hello").authenticated()
        //     // 对其他所有资源设置都需要'SUPER_USER'权限验证
        //     .anyRequest().hasAuthority("SUPER_USER");

        // 动态权限控制配置
        http.authorizeRequests().antMatchers("/", "/login", "/other").permitAll();
        this.permissions.forEach(item -> {
            try {
                String url = item.getPermissionUrl();
                if (url == null || "".equals(url.trim())) {
                    return;
                }
                String roleCode = item.getRoleCode();
                if (roleCode == null || "".equals(roleCode.trim())) {
                    return;
                }
                String[] authorities = roleCode.split(",");
                http.authorizeRequests().antMatchers(url).hasAnyAuthority(authorities);
            } catch (Exception e) {
                log.error("动态权限控制配置失败", e);
            }
        });
        http.authorizeRequests().anyRequest().denyAll();

        // 表单登录配置
        http.formLogin()
            // 设置登录页地址
            .loginPage("/login")
            // 设置该请求地址为登录请求, 与自定义登录页的登录请求地址对应
            .loginProcessingUrl("/authentication")
            // 登录成功后发送的请求, 第二个参数与是否恢复到登录前发送的请求有关(当为false时表示恢复)
            .defaultSuccessUrl("/hello", true)
            // 登录失败后发送的请求
            .failureUrl("/login?error=true");

        // 注销配置
        http.logout()
            // 设置注销地址
            .logoutUrl("/logout")
            // 注销成功后发送的请求
            .logoutSuccessUrl("/");
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     // 注意: authorities与roles同时配置时, 前面的会被后面的覆盖
    //     auth.inMemoryAuthentication()
    //         .withUser("admin").password("123456").authorities("SUPER_USER")
    //         .and()
    //         .withUser("guest").password("123456").authorities("GUEST");
    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 无加密算法
        // return NoOpPasswordEncoder.getInstance();
        // BCrypt算法
        return new BCryptPasswordEncoder();
    }
}
