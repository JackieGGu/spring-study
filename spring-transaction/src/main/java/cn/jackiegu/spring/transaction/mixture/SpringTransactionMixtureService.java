package cn.jackiegu.spring.transaction.mixture;

public interface SpringTransactionMixtureService {

    Integer saveAndUpdate(SpringTransactionMixtureEntity entity, String name);

    SpringTransactionMixtureEntity findById(Integer id);
}
