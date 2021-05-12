package cn.jackiegu.spring.transaction.mixture;

public class SpringTransactionMixtureException extends RuntimeException {

    private static final long serialVersionUID = -7620525596300732761L;

    public SpringTransactionMixtureException(String message) {
        super(message);
    }

    public SpringTransactionMixtureException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpringTransactionMixtureException(Throwable cause) {
        super(cause);
    }
}
