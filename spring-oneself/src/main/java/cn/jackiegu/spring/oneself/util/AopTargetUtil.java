package cn.jackiegu.spring.oneself.util;

import cn.hutool.core.convert.Convert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

/**
 * Aop代理对象解析工具
 *
 * @author JackieGu
 * @date 2021/3/12
 */
public class AopTargetUtil {

    public static <T extends InvocationHandler, E> E getTargetFieldValue(Object proxy,
                                                                         Class<T> proxyClass,
                                                                         String fieldName,
                                                                         Class<E> fieldClass) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        T handler = Convert.convert(proxyClass, h.get(proxy));
        Field field = handler.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return Convert.convert(fieldClass, field.get(handler));
    }
}
