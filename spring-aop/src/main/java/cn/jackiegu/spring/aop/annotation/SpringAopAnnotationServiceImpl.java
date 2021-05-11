package cn.jackiegu.spring.aop.annotation;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SpringAopAnnotationServiceImpl implements SpringAopAnnotationService {

    @Override
    public String findName(Integer id) {
        System.out.println("id: " + id);
        switch (id) {
            case 1:
                return "球球";
            default:
                throw new NoSuchElementException("No value present");
        }
    }
}
