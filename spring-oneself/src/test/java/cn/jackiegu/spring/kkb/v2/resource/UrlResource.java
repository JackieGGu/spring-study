package cn.jackiegu.spring.kkb.v2.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络资源
 *
 * @author JackieGu
 * @date 2021/5/19
 */
public class UrlResource implements Resource {

    private final String url;

    public UrlResource(String url) {
        this.url = url;
    }

    @Override
    public InputStream getInputStream() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return connection.getInputStream();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
