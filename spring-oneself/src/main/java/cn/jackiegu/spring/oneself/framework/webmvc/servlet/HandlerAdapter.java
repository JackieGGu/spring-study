package cn.jackiegu.spring.oneself.framework.webmvc.servlet;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.jackiegu.spring.oneself.framework.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringMVC HandlerAdapter组件
 *
 * @author JackieGu
 * @date 2021/3/5
 */
public class HandlerAdapter {

    @SuppressWarnings("unchecked")
    public Object handle(HttpServletRequest request, HttpServletResponse response, HandlerMapping handlerMapping) throws Exception {
        Map<String, Integer> parameterSort = new HashMap<>();
        // 形参
        Annotation[][] parameterAnnotations = handlerMapping.getMethod().getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof RequestParam) {
                    String parameterName = ((RequestParam) annotation).value();
                    if (StrUtil.isNotBlank(parameterName)) {
                        parameterSort.put(parameterName, i);
                    }
                }
            }
        }
        Class<?>[] parameterTypes = handlerMapping.getMethod().getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (HttpServletRequest.class == type || HttpServletResponse.class == type) {
                parameterSort.put(type.getName(), i);
            }
        }
        // 实参
        Object[] args = new Object[parameterTypes.length];
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> item : parameterMap.entrySet()) {
            String key = item.getKey();
            if (!parameterSort.containsKey(key)) {
                continue;
            }
            int index = parameterSort.get(key);
            args[index] = this.convert(parameterTypes[index], item.getValue());
        }
        if (parameterSort.containsKey(HttpServletRequest.class.getName())) {
            int index = parameterSort.get(HttpServletRequest.class.getName());
            args[index] = request;
        }
        if (parameterSort.containsKey(HttpServletResponse.class.getName())) {
            int index = parameterSort.get(HttpServletResponse.class.getName());
            args[index] = response;
        }
        // 反射执行方法
        return handlerMapping.getMethod().invoke(handlerMapping.getController(), args);
    }

    /**
     * 类型转换
     *
     * @param type  转换类型
     * @param value 被转换值
     * @return 转换后值
     */
    private Object convert(Class<?> type, String[] value) {
        if (ClassUtil.isBasicType(type) || type == String.class) {
            return Convert.convert(type, value[0]);
        } else if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            Object result = Array.newInstance(componentType, value.length);
            for (int i = 0; i < value.length; i++) {
                Array.set(result, i, Convert.convert(componentType, value[i]));
            }
            return result;
        } else {
            return null;
        }
    }
}
