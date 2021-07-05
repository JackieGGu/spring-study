package cn.jackiegu.spring.security.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置(相当于spring-security.xml)
 *
 * @author JackieGu
 * @date 2021/7/2
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // 对根和自定义登录页资源取消权限验证
            .antMatchers("/", "/security/boot/login").permitAll()
            // 对该资源设置需要登录权限验证
            .antMatchers("/security/boot/hello").authenticated()
            // 对该资源设置需要'SUPER_USER'权限验证
            .antMatchers("/**").hasAuthority("SUPER_USER")
            .and()
            .formLogin()
                .defaultSuccessUrl("/security/boot/hello", true)
            .and()
            .logout().logoutSuccessUrl("/login");
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
