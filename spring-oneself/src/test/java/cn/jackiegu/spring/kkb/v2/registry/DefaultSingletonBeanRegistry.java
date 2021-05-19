package cn.jackiegu.spring.kkb.v2.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例Bean注册容器
 * 注意: 对单例bean容器的操作，会存在线程安全的问题
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String name) {
        return singletonObjects.get(name);
    }

    @Override
    public void addSingleton(String name, Object bean) {
        singletonObjects.put(name, bean);
    }
}
