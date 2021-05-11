package cn.jackiegu.spring.core.advanced.xml.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring基于XML的AOP测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringXmlAopTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-xml-aop.xml");
        SpringXmlAopService springXmlAopService = applicationContext.getBean(SpringXmlAopService.class);
        SpringXmlAopEntity entity = SpringXmlAopEntity.builder()
            .id(1)
            .name("admin")
            .build();
        springXmlAopService.insert(entity);

        System.out.println();
        entity.setName("superAdmin");
        springXmlAopService.update(entity);

        System.out.println();
        SpringXmlAopEntity entity1 = springXmlAopService.findById(1);
        System.out.println("entity1: " + entity1);

        System.out.println();
        SpringXmlAopEntity entity2 = springXmlAopService.findById(2);
        System.out.println("entity2: " + entity2);
    }

}
