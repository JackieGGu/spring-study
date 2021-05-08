package cn.jackiegu.spring.oneself.framework.aop.config;

import java.io.Serializable;

/**
 * AOP配置类
 *
 * @author JackieGu
 * @date 2021/3/11
 */
public class AopConfig implements Serializable {

    private static final long serialVersionUID = -2425995650906639648L;

    private String pointcut;

    private String aspectClass;

    private String aspectBefore;

    private String aspectAfter;

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }
}
