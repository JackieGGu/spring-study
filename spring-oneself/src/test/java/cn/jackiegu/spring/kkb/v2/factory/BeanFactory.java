package cn.jackiegu.spring.kkb.v2.factory;

/**
 * Bean工厂接口
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public interface BeanFactory {

    Object getBean(String name);
}
