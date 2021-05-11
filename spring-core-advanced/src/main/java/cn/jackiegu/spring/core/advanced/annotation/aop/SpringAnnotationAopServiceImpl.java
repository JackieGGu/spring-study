package cn.jackiegu.spring.core.advanced.annotation.aop;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SpringAnnotationAopServiceImpl implements SpringAnnotationAopService {

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
