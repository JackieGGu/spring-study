package cn.jackiegu.spring.transaction.xml;

public interface SpringTransactionService {

    Integer saveAndUpdate(SpringTransactionEntity entity, String name);

    SpringTransactionEntity findById(Integer id);
}
