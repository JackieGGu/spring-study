package cn.jackiegu.spring.study.framework.exception;

/**
 * Spring Bean 创建异常
 *
 * @author JackieGu
 * @date 2021/2/2
 */
public class BeanCreationException extends RuntimeException {

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
