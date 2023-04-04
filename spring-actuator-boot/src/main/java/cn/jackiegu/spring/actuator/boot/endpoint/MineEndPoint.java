package cn.jackiegu.spring.actuator.boot.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * spring actuator info
 *
 * @author JackieGu
 * @date 2021/10/14
 */
@Component
@Endpoint(id = "mine")
public class MineEndPoint {

    private static final String METHOD_KEY = "method";

    @ReadOperation
    public Map<String, Object> read() {
        Map<String, Object> result = new HashMap<>();
        result.put(METHOD_KEY, "read");
        return result;
    }

    /**
     * Seleter注解可以增加路径参数:
     * 生成路径一
     *   url: /actuator/mine/{args0}
     *   原因: 某些1.8低版本JDK编译出来的class无法获取参数名
     * 生成路径二
     *   url: /actuator/mine/{path}
     *   原因: 使用1.8高版本JDK、或者在Additional command line parameters添加 -parameters 参数
     */
    @ReadOperation
    public Map<String, Object> selector(@Selector String path) {
        Map<String, Object> result = new HashMap<>();
        result.put(METHOD_KEY, "selector");
        result.put("path", path);
        return result;
    }

    @DeleteOperation
    public Map<String, Object> delete() {
        Map<String, Object> result = new HashMap<>();
        result.put(METHOD_KEY, "delete");
        return result;
    }
}
