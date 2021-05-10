package cn.jackiegu.spring.core.basics.ioc.xml;

import java.security.SecureRandom;
import java.util.Random;

/**
 * IOC XML bean对象
 *
 * @author JackieGu
 * @date 2021/5/10
 */
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
