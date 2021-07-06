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
        // 禁用CSRF攻击检测
        http.csrf().disable();

        http.authorizeRequests()
            // 对根和自定义登录页资源取消权限验证
            .antMatchers("/", "/security/boot/login").permitAll()
            // 对该资源设置需要登录权限验证
            .antMatchers("/security/boot/hello").authenticated()
            // 对其他所有资源设置都需要'SUPER_USER'权限验证
            .anyRequest().hasAuthority("SUPER_USER");

        http.formLogin()
            // 设置登录页地址
            .loginPage("/security/boot/login")
            // 设置该请求地址为登录请求, 与自定义登录页的登录请求地址对应
            .loginProcessingUrl("/authentication")
            // 登录成功后发送的请求, 第二个参数与是否恢复到登录前发送的请求有关(当为false时表示恢复)
            .defaultSuccessUrl("/security/boot/hello", true)
            // 登录失败后发送的请求
            .failureUrl("/security/boot/login?error=true");

        http.logout()
            // 注销成功后发送的请求
            .logoutSuccessUrl("/security/boot/login");
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
