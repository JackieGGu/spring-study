package cn.jackiegu.spring.aop.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring基于XML的AOP测试
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringAopXmlTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-aop-xml.xml");
        SpringAopXmlService springAopXmlService = applicationContext.getBean(SpringAopXmlService.class);
        SpringAopXmlEntity entity = SpringAopXmlEntity.builder()
            .id(1)
            .name("admin")
            .build();
        springAopXmlService.insert(entity);

        System.out.println();
        entity.setName("superAdmin");
        springAopXmlService.update(entity);

        System.out.println();
        SpringAopXmlEntity entity1 = springAopXmlService.findById(1);
        System.out.println("entity1: " + entity1);

        System.out.println();
        SpringAopXmlEntity entity2 = springAopXmlService.findById(2);
        System.out.println("entity2: " + entity2);
    }

}
