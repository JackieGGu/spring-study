package cn.jackiegu.spring.kkb.v2.resource;

import cn.hutool.core.util.StrUtil;

import java.io.InputStream;

/**
 * 资源
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public interface Resource {

    InputStream getInputStream();

    static Resource getInstance(String location) {
        if (StrUtil.isBlank(location)) {
            throw new IllegalArgumentException("the location parameter invalid");
        }
        if (location.startsWith("file://")) {
            return new FileResource(location);
        } else if (location.startsWith("http://") || location.startsWith("https://")) {
            return new UrlResource(location);
        } else {
            return new ClasspathResource(location);
        }
    }
}
