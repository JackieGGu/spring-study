package cn.jackiegu.spring.core.advanced.xml.aop;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SpringXmlAopServiceImpl implements SpringXmlAopService {

    @Override
    public void insert(SpringXmlAopEntity entity) {
        System.out.println("insert: " + entity);
    }

    @Override
    public void update(SpringXmlAopEntity entity) {
        System.out.println("update: " + entity);
    }

    @Override
    public SpringXmlAopEntity findById(Integer id) {
        if (id != null && id.equals(1)) {
            return SpringXmlAopEntity.builder()
                .id(1)
                .name("admin")
                .build();
        } else {
            throw new NoSuchElementException("No value present");
        }
    }
}
