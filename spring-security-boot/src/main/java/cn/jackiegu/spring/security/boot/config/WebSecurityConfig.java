package cn.jackiegu.spring.security.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author JackieGu
 * @date 2021/7/2
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/security/boot/hello").authenticated()
            .antMatchers("/**").hasAuthority("SUPER_USER")
            .and()
            .formLogin().defaultSuccessUrl("/security/boot/hello", true)
            .and()
            .logout().logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 注意: authorities与roles同时配置时, 前面的会被后面的覆盖
        auth.inMemoryAuthentication()
            .withUser("admin").password("123456").authorities("SUPER_USER")
            .and()
            .withUser("guest").password("123456").authorities("GUEST");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
