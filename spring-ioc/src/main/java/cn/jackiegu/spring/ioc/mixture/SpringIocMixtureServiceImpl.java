package cn.jackiegu.spring.ioc.mixture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SpringIocMixtureServiceImpl implements SpringIocMixtureService {

    /**
     * Autowired:
     * 1. Autowired默认按类型装配
     * 2. Autowired是Spring自带的注解
     * 3. Autowired默认情况下要求依赖对象必须存在, 如果需要允许null值, 可设置其required属性为false
     * 4. Autowired若想以按名称装配, 可以结合Qualifier注解使用
     *
     * Qualifier
     * 1. Qualifier是在自动按照类型装配的基础上, 再按照Bean的id进行装配
     * 2. Qualifier在给类属性注入值时不能独立使用, 必须配合Autowired一起使用
     * 3. Qualifier在给方法参数注入值时, 可以独立使用
     *
     * Resource
     * 1. Resource默认按名称装配, 可以通过其name属性指定名称,
     *   若没有指定name属性, 当注解写在类属性上时, 默认取属性名进行按名称查找, 当找不到与名称匹配的bean时才按照类型进行装配
     * 2. Resource是JSR250提供的注解
     * 3. Resource在指定name属性后, 就只会按照名称进行装配
     *
     * Inject
     * 1. Inject是根据类型进行自动装配的, 如果需要按名称进行装配, 则需要配合Named注解使用
     * 2. Inject是JSR330中的规范, 需要导入java.inject.Inject
     * 3. Inject可以作用在变量、setting方法、构造函数上
     */
    @Resource
    private SpringIocMixtureComponent springIocMixtureComponent;

    @Value("${work}")
    private String work;

    @Override
    public void doSomething() {
        System.out.println("spring ioc mixture service do something");
        springIocMixtureComponent.doSomething(work);
    }
}
