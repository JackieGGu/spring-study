package cn.jackiegu.spring.mvc.service.impl;

import cn.jackiegu.spring.mvc.service.SpringMvcService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Spring Mvc ServiceImpl
 *
 * @author JackieGu
 * @date 2021/6/2
 */
@Service
public class SpringMvcServiceImpl implements SpringMvcService, ApplicationContextAware {

    @Override
    public Integer generateNumber() {
        Random random = new SecureRandom();
        return random.nextInt(1000);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(applicationContext);
    }
}
