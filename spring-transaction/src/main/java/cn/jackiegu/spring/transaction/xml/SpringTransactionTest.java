package cn.jackiegu.spring.transaction.xml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Spring Transaction测试
 *
 * @author JackieGu
 * @date 2021/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-transaction-xml.xml")
public class SpringTransactionTest {

    @Resource
    private SpringTransactionService springTransactionService;

    @Test
    public void doAction() {
        SpringTransactionEntity entity = SpringTransactionEntity.builder()
            .name("张三")
            .age(28)
            .sex("男")
            .build();
        Integer id = springTransactionService.saveAndUpdate(entity, "zs");
        System.out.println("id: " + id);
    }

    @Test
    public void doSelect() {
        SpringTransactionEntity entity = springTransactionService.findById(1);
        System.out.println("entity: " + entity);
    }
}
