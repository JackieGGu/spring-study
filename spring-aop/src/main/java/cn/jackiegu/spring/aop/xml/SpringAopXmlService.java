package cn.jackiegu.spring.aop.xml;

public interface SpringAopXmlService {

    void insert(SpringAopXmlEntity entity);

    void update(SpringAopXmlEntity entity);

    SpringAopXmlEntity findById(Integer id);
}
