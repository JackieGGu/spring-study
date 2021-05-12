package cn.jackiegu.spring.transaction.mixture;

public interface SpringTransactionMixtureDao {

    void save(SpringTransactionMixtureEntity entity);

    void update(SpringTransactionMixtureEntity entity);

    SpringTransactionMixtureEntity findById(Integer id);
}
