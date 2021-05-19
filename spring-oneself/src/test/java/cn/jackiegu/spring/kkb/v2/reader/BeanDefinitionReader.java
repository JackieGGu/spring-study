package cn.jackiegu.spring.kkb.v2.reader;

import cn.jackiegu.spring.kkb.v2.resource.Resource;

/**
 * BeanDefinition阅读器
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(Resource resource);
}
