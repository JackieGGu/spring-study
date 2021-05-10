package cn.jackiegu.spring.core.basics.xml.ioc;

import java.security.SecureRandom;
import java.util.Random;

/**
 * IOC XML bean对象
 *
 * @author JackieGu
 * @date 2021/5/10
 */
public class SpringXmlIocBean {

    private static final Random RANDOM = new SecureRandom();

    private final Integer number;

    public SpringXmlIocBean() {
        this.number = RANDOM.nextInt(100);
    }

    public Integer getNumber() {
        return number;
    }

    public static SpringXmlIocBean instance() {
        return new SpringXmlIocBean();
    }
}
