package cn.jackiegu.spring.transaction.annotation;

public interface SpringTransactionAnnotationDao {

    void save(SpringTransactionAnnotationEntity entity);

    void update(SpringTransactionAnnotationEntity entity);

    SpringTransactionAnnotationEntity findById(Integer id);
}
