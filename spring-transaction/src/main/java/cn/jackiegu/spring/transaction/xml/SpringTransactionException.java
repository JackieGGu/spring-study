package cn.jackiegu.spring.transaction.xml;

public class SpringTransactionException extends RuntimeException {

    private static final long serialVersionUID = -7815452823909629814L;

    public SpringTransactionException(String message) {
        super(message);
    }

    public SpringTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringTransactionException(Throwable cause) {
        super(cause);
    }
}
