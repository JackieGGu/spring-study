package cn.jackiegu.spring.transaction.xml;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SpringTransactionServiceImpl implements SpringTransactionService {

    @Resource
    private SpringTransactionDao springTransactionDao;

    @Override
    public Integer saveAndUpdate(SpringTransactionEntity entity, String name) {
        springTransactionDao.save(entity);
        System.out.println("save entity: " + entity);
        if (entity.getId() > 1500) {
            throw new RuntimeException();
        }
        entity.setName(name);
        springTransactionDao.update(entity);
        System.out.println("update entity: " + entity);
        return entity.getId();
    }

    @Override
    public SpringTransactionEntity findById(Integer id) {
        SpringTransactionEntity entity = SpringTransactionEntity.builder()
            .name("张三")
            .age(28)
            .sex("男")
            .build();
        springTransactionDao.save(entity);
        return entity;
    }


}
