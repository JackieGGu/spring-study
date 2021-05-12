package cn.jackiegu.spring.transaction.annotation;

public class SpringTransactionAnnotationException extends RuntimeException {

    private static final long serialVersionUID = -7430054929562918163L;

    public SpringTransactionAnnotationException(String message) {
        super(message);
    }

    public SpringTransactionAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringTransactionAnnotationException(Throwable cause) {
        super(cause);
    }
}
