package cn.jackiegu.spring.core.advanced.mixture.aop;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class SpringMixtureAopServiceImpl implements SpringMixtureAopService {

    @Override
    public String findName(Integer id) {
        System.out.println("id: " + id);
        switch (id) {
            case 1:
                return "张三";
            case 2:
                return "李四";
            case 3:
                return "王五";
            default:
                throw new NoSuchElementException("No value present");
        }
    }
}
