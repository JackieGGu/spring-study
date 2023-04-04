package cn.jackiegu.spring.transaction.annotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Spring Transaction 纯注解方式测试
 *
 * @author JackieGu
 * @date 2021/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTransactionAnnotationConfiguration.class})
public class SpringTransactionAnnotationTest {

    @Resource
    private SpringTransactionAnnotationService springTransactionAnnotationService;

    @Test
    public void doAction() {
        SpringTransactionAnnotationEntity entity = SpringTransactionAnnotationEntity.builder()
            .name("张三")
            .age(28)
            .sex("男")
            .build();
        Integer id = springTransactionAnnotationService.saveAndUpdate(entity, "zs");
        System.out.println("id: " + id);
    }

    @Test
    public void doSelect() {
        SpringTransactionAnnotationEntity entity = springTransactionAnnotationService.findById(1);
        System.out.println("entity: " + entity);
    }
}
