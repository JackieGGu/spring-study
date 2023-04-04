package cn.jackiegu.spring.transaction.mixture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Spring Transaction 混合方式测试
 *
 * @author JackieGu
 * @date 2021/5/11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-transaction-mixture.xml")
public class SpringTransactionMixtureTest {

    @Resource
    private SpringTransactionMixtureService springTransactionMixtureService;

    @Test
    public void doAction() {
        SpringTransactionMixtureEntity entity = SpringTransactionMixtureEntity.builder()
            .name("张三")
            .age(28)
            .sex("男")
            .build();
        Integer id = springTransactionMixtureService.saveAndUpdate(entity, "zs");
        System.out.println("id: " + id);
    }

    @Test
    public void doSelect() {
        SpringTransactionMixtureEntity entity = springTransactionMixtureService.findById(1);
        System.out.println("entity: " + entity);
    }
}
