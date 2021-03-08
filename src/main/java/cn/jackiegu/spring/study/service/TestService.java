package cn.jackiegu.spring.study.service;

/**
 * 测试Service
 *
 * @author JackieGu
 * @date 2021/2/3
 */
public interface TestService {

    String hello();

    String sayHi(String name, Integer sex, Integer age);

    String getTime();
}
