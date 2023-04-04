package cn.jackiegu.spring.ioc.xml;

import java.security.SecureRandom;
import java.util.Random;

public class SpringIocXmlBean {

    private static final Random RANDOM = new SecureRandom();

    private final Integer number;

    public SpringIocXmlBean() {
        this.number = RANDOM.nextInt(100);
    }

    public Integer getNumber() {
        return number;
    }

    public static SpringIocXmlBean instance() {
        return new SpringIocXmlBean();
    }
}
