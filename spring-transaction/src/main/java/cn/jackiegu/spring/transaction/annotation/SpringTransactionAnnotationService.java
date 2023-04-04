package cn.jackiegu.spring.transaction.annotation;

public interface SpringTransactionAnnotationService {

    Integer saveAndUpdate(SpringTransactionAnnotationEntity entity, String name);

    SpringTransactionAnnotationEntity findById(Integer id);
}
