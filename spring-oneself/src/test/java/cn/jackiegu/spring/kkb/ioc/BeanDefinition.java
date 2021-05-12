package cn.jackiegu.spring.kkb.ioc;

import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

    private static final String SCOPE_SINGLETON = "singleton";

    private static final String SCOPE_PROTOTYPE = "prototype";

    private String beanName;

    private String clazzName;

    private Class<?> clazzType;

    private String initMethod;

    private String scope;

    private List<PropertyValue> propertyValues = new ArrayList<>();

}
