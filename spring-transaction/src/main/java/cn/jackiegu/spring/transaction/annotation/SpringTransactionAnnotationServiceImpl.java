package cn.jackiegu.spring.transaction.annotation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class SpringTransactionAnnotationServiceImpl implements SpringTransactionAnnotationService {

    @Resource
    private SpringTransactionAnnotationDao springTransactionAnnotationDao;

    @Override
    public Integer saveAndUpdate(SpringTransactionAnnotationEntity entity, String name) {
        springTransactionAnnotationDao.save(entity);
        System.out.println("save entity: " + entity);
        if (entity.getId() > 1500) {
            throw new RuntimeException();
        }
        entity.setName(name);
        springTransactionAnnotationDao.update(entity);
        System.out.println("update entity: " + entity);
        return entity.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public SpringTransactionAnnotationEntity findById(Integer id) {
        return springTransactionAnnotationDao.findById(id);
    }
}
