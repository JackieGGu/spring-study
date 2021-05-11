package cn.jackiegu.spring.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Spring整合Junit单元测试
 *
 * @author JackieGu
 * @date 2021/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
// @ContextConfiguration(classes = {SpringJunitConfiguration.class})
public class SpringJunitTest {

    @Resource
    private SpringJunitComponent springJunitComponent;

    @Test
    public void testSayHi() {
        springJunitComponent.sayHi();
    }

}
