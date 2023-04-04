package cn.jackiegu.spring.oneself.framework.beans;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.jackiegu.spring.oneself.framework.aop.JdkDynamicAopProxy;
import cn.jackiegu.spring.oneself.framework.aop.support.AdviceSupport;
import cn.jackiegu.spring.oneself.util.AopTargetUtil;

import java.lang.reflect.Proxy;

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

    public BeanWrapper(Object wrapperInstance) throws Exception {
        this.wrapperInstance = wrapperInstance;
        this.wrapperClass = this.getTargetObjectClass(wrapperInstance);
    }

    public Object getWrapperInstance() {
        return this.wrapperInstance;
    }

    public Class<?> getWrapperClass() {
        return this.wrapperClass;
    }

    private Class<?> getTargetObjectClass(Object instance) throws Exception {
        if (!(instance instanceof Proxy)) {
            return instance.getClass();
        }
        AdviceSupport adviceSupport = AopTargetUtil
            .getTargetFieldValue(instance, JdkDynamicAopProxy.class, "adviceSupport", AdviceSupport.class);
        return adviceSupport.getTargetClass();
    }
}
