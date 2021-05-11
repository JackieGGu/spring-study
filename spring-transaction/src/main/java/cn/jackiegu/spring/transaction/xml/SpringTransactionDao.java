package cn.jackiegu.spring.transaction.xml;

public interface SpringTransactionDao {

    void save(SpringTransactionEntity entity);

    void update(SpringTransactionEntity entity);

    SpringTransactionEntity findById(Integer id);
}
