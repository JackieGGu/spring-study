package cn.jackiegu.spring.core.advanced.xml.aop;

public interface SpringXmlAopService {

    void insert(SpringXmlAopEntity entity);

    void update(SpringXmlAopEntity entity);

    SpringXmlAopEntity findById(Integer id);
}
