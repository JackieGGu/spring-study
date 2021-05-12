package cn.jackiegu.spring.transaction.xml;

public interface SpringTransactionXmlDao {

    void save(SpringTransactionXmlEntity entity);

    void update(SpringTransactionXmlEntity entity);

    SpringTransactionXmlEntity findById(Integer id);
}
