package cn.jackiegu.spring.kkb.v2.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * 本地资源
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class FileResource implements Resource {

    private String path;

    public FileResource(String path) {
        this.path = path;
    }

    @Override
    public InputStream getInputStream() {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
