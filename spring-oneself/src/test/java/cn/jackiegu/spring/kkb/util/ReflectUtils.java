package cn.jackiegu.spring.kkb.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class ReflectUtils {

    /**
     * 根据Class类型创建实例
     *
     * @param clazz CLass类型
     * @return 实例
     */
    public static Object newInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据方法名称调用实例方法
     *
     * @param bean       bean实例
     * @param methodName 方法名称
     * @param args       参数
     */
    public static void invokeMethod(Object bean, String methodName, Object... args) {
        try {
            Method method = bean.getClass().getMethod(methodName);
            method.invoke(bean, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对实例属性赋值
     *
     * @param bean  bean实例
     * @param name  属性名称
     * @param value 属性值
     */
    public static void setProperty(Object bean, String name, Object value) {
        try {
            Field field = bean.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
