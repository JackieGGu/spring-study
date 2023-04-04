package cn.jackiegu.spring.security.boot;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * BCrypt密码算法测试
 *
 * @author JackieGu
 * @date 2021/7/2
 */
public class BCryptTest {

    @Test
    public void encodeTest() {
        String password = BCrypt.hashpw("123456", BCrypt.gensalt());
        System.out.println(password);
    }

    @Test
    public void decodeTest() {
        boolean result = BCrypt.checkpw("123456", "$2a$10$HmEfzHMjjaxnxd4zBzw1DONM8Up9RvwY59gMt1ztXtZz1LzCBJPde");
        System.out.println(result);
    }
}
