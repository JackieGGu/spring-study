package cn.jackiegu.spring.auto.configuration.boot.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文支持
 *
 * @author JackieGu
 * @date 2021/7/15
 */
@Component
public class SpringContextSupport implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setValue(applicationContext);
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

    private static void setValue(ApplicationContext applicationContext) {
        SpringContextSupport.applicationContext = applicationContext;
    }
}
