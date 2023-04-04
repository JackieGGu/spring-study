package cn.jackiegu.spring.transaction.xml;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SpringTransactionXmlServiceImpl implements SpringTransactionXmlService {

    @Resource
    private SpringTransactionXmlDao springTransactionXmlDao;

    @Override
    public Integer saveAndUpdate(SpringTransactionXmlEntity entity, String name) {
        springTransactionXmlDao.save(entity);
        System.out.println("save entity: " + entity);
        if (entity.getId() > 1500) {
            throw new RuntimeException();
        }
        entity.setName(name);
        springTransactionXmlDao.update(entity);
        System.out.println("update entity: " + entity);
        return entity.getId();
    }

    @Override
    public SpringTransactionXmlEntity findById(Integer id) {
        return springTransactionXmlDao.findById(id);
    }
}
