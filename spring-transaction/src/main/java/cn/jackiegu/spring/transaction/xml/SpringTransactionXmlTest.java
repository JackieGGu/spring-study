package cn.jackiegu.spring.transaction.xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Spring Transaction XML方式测试
 *
 * @author JackieGu
 * @date 2021/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-transaction-xml.xml")
public class SpringTransactionXmlTest {

    @Resource
    private SpringTransactionXmlService springTransactionXmlService;

    @Test
    public void doAction() {
        SpringTransactionXmlEntity entity = SpringTransactionXmlEntity.builder()
            .name("张三")
            .age(28)
            .sex("男")
            .build();
        Integer id = springTransactionXmlService.saveAndUpdate(entity, "zs");
        System.out.println("id: " + id);
    }

    @Test
    public void doSelect() {
        SpringTransactionXmlEntity entity = springTransactionXmlService.findById(1);
        System.out.println("entity: " + entity);
    }
}
