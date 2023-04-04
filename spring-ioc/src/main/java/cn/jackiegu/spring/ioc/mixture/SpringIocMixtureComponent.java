package cn.jackiegu.spring.ioc.mixture;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SpringIocMixtureComponent {

    @PostConstruct
    public void init() {
        System.out.println("spring ioc mixture component init");
    }

    public void doSomething(String work) {
        System.out.println("spring ioc mixture component do " + work);
    }

    @PreDestroy
    public void destroy() {
        System.out.println("spring ioc mixture component destroy");
    }
}
