package cn.jackiegu.spring.kkb.v1.ioc;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

    private static final String SCOPE_SINGLETON = "singleton";

    private static final String SCOPE_PROTOTYPE = "prototype";

    /**
     * bean标签的id
     */
    private String beanName;

    /**
     * bean标签的class
     */
    private String clazzName;

    /**
     * bean标签的class对应的Class对象
     */
    private Class<?> clazzType;

    /**
     * bean标签的初始化方法
     */
    private String initMethod;

    /**
     * bean标签的作用域, 默认singleton
     */
    private String scope;

    /**
     * bean标签中的属性
     */
    private List<PropertyValue> propertyValues = new ArrayList<>();

    public BeanDefinition(String beanName, String clazzName) {
        try {
            this.beanName = beanName;
            this.clazzName = clazzName;
            this.clazzType = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(this.scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(this.scope);
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Class<?> getClazzType() {
        return clazzType;
    }

    public void setClazzType(Class<?> clazzType) {
        this.clazzType = clazzType;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(List<PropertyValue> propertyValues) {
        this.propertyValues = propertyValues;
    }
}
