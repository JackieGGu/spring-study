package cn.jackiegu.spring.oneself.framework.exception;

/**
 * Spring Bean 创建异常
 *
 * @author JackieGu
 * @date 2021/2/2
 */
public class BeanCreationException extends RuntimeException {

    private static final long serialVersionUID = -2283863561327009318L;

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
