package cn.jackiegu.spring.oneself.framework.aop.aspect;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 切面通知类
 *
 * @author JackieGu
 * @date 2021/3/11
 */
public class AdviceAspect implements Serializable {

    private static final long serialVersionUID = 3988814250748192142L;

    @SuppressWarnings("all")
    private Object aspect;

    @SuppressWarnings("all")
    private Method method;

    public AdviceAspect() {
    }

    public AdviceAspect(Object aspect, Method method) {
        this.aspect = aspect;
        this.method = method;
    }

    public Object getAspect() {
        return aspect;
    }

    public void setAspect(Object aspect) {
        this.aspect = aspect;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
