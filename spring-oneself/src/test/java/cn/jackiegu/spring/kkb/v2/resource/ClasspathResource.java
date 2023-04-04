package cn.jackiegu.spring.kkb.v2.resource;

import java.io.InputStream;

/**
 * classpath资源
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class ClasspathResource implements Resource {

    private String location;

    public ClasspathResource(String location) {
        this.location = location;
    }

    @Override
    public InputStream getInputStream() {
        if (location.startsWith("classpath:")) {
            location = location.substring(10);
        }
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }
}
