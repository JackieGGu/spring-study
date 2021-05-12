package cn.jackiegu.spring.transaction.xml;

public interface SpringTransactionXmlService {

    Integer saveAndUpdate(SpringTransactionXmlEntity entity, String name);

    SpringTransactionXmlEntity findById(Integer id);
}
