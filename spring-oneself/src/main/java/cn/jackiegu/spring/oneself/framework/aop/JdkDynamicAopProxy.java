package cn.jackiegu.spring.oneself.framework.aop;

import cn.jackiegu.spring.oneself.framework.aop.aspect.AdviceAspect;
import cn.jackiegu.spring.oneself.framework.aop.support.AdviceSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * JDK 动态AOP代理类
 *
 * @author JackieGu
 * @date 2021/3/11
 */
public class JdkDynamicAopProxy implements InvocationHandler {

    private final AdviceSupport adviceSupport;

    public JdkDynamicAopProxy(AdviceSupport adviceSupport) {
        this.adviceSupport = adviceSupport;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
            this.adviceSupport.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<String, AdviceAspect> advices = this.adviceSupport.getAdvices(method);
        if (advices == null) {
            return method.invoke(this.adviceSupport.getTarget(), args);
        }
        if (advices.containsKey("before")) {
            AdviceAspect before = advices.get("before");
            before.getMethod().invoke(before.getAspect());
        }
        Object result = method.invoke(this.adviceSupport.getTarget(), args);
        if (advices.containsKey("after")) {
            AdviceAspect after = advices.get("after");
            after.getMethod().invoke(after.getAspect());
        }
        return result;
    }
}
