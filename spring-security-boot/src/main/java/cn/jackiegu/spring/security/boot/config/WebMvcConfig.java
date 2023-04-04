package cn.jackiegu.spring.security.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web mvc 配置(相当于spring-servlet.xml)
 *
 * @author JackieGu
 * @date 2021/7/2
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 设置根路径重定向到Spring Security默认登录页
        registry.addViewController("/").setViewName("redirect:/login");

        // 设置根路径重定向到自定义登录页
        // registry.addViewController("/").setViewName("redirect:/security/boot/login");
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages_zh_CN");
        return messageSource;
    }
}
