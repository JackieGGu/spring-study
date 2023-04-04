package cn.jackiegu.spring.kkb.v2;

import cn.jackiegu.spring.kkb.business.entity.UserEntity;
import cn.jackiegu.spring.kkb.business.service.UserService;
import cn.jackiegu.spring.kkb.v2.factory.BeanFactory;
import cn.jackiegu.spring.kkb.v2.factory.XmlBeanFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * UserService测试
 *
 * @author JackieGu
 * @date 2021/5/12
 */
public class UserServiceTest {

    private static final String configLocation = "beans.xml";

    private BeanFactory beanFactory;

    @Before
    public void before() {
       beanFactory = new XmlBeanFactory(configLocation);
    }

    @Test
    public void run() {
        UserService userService = (UserService) beanFactory.getBean("userService");
        Assert.assertNotNull(userService);
        UserEntity entity = userService.findById(1);
        System.out.println(entity);
    }
}
