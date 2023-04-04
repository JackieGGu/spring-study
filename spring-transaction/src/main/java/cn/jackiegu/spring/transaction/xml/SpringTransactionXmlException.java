package cn.jackiegu.spring.transaction.xml;

public class SpringTransactionXmlException extends RuntimeException {

    private static final long serialVersionUID = -7815452823909629814L;

    public SpringTransactionXmlException(String message) {
        super(message);
    }

    public SpringTransactionXmlException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringTransactionXmlException(Throwable cause) {
        super(cause);
    }
}
