package cn.jackiegu.spring.transaction.mixture;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SpringTransactionMixtureServiceImpl implements SpringTransactionMixtureService {

    @Resource
    private SpringTransactionMixtureDao springTransactionMixtureDao;

    @Override
    public Integer saveAndUpdate(SpringTransactionMixtureEntity entity, String name) {
        springTransactionMixtureDao.save(entity);
        System.out.println("save entity: " + entity);
        if (entity.getId() > 1500) {
            throw new RuntimeException();
        }
        entity.setName(name);
        springTransactionMixtureDao.update(entity);
        System.out.println("update entity: " + entity);
        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public SpringTransactionMixtureEntity findById(Integer id) {
        return springTransactionMixtureDao.findById(id);
    }
}
