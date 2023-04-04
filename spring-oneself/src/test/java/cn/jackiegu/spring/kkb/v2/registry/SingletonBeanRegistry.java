package cn.jackiegu.spring.kkb.v2.registry;

/**
 * 单例Bean注册容器接口
 * 定义单例Bean容器的行为
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String name);

    void addSingleton(String name, Object bean);
}
