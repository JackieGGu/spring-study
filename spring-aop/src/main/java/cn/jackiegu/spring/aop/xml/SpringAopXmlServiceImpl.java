package cn.jackiegu.spring.aop.xml;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SpringAopXmlServiceImpl implements SpringAopXmlService {

    @Override
    public void insert(SpringAopXmlEntity entity) {
        System.out.println("insert: " + entity);
    }

    @Override
    public void update(SpringAopXmlEntity entity) {
        System.out.println("update: " + entity);
    }

    @Override
    public SpringAopXmlEntity findById(Integer id) {
        if (id != null && id.equals(1)) {
            return SpringAopXmlEntity.builder()
                .id(1)
                .name("admin")
                .build();
        } else {
            throw new NoSuchElementException("No value present");
        }
    }
}
