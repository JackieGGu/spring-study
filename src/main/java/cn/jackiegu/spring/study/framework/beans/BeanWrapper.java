package cn.jackiegu.spring.study.framework.beans;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * Spring Bean 包装类
 *
 * @author JackieGu
 * @date 2021/2/2
 */
public class BeanWrapper {

    private static final Log LOGGER = LogFactory.get();

    private final Object wrapperInstance;

    private final Class<?> wrapperClass;

    public BeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
        this.wrapperClass = wrapperInstance.getClass();
    }

    public Object getWrapperInstance() {
        return this.wrapperInstance;
    }

    public Class<?> getWrapperClass() {
        return this.wrapperClass;
    }
}
