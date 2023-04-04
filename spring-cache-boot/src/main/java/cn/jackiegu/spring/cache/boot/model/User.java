package cn.jackiegu.spring.cache.boot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户实体
 *
 * @author JackieGu
 * @date 2021/9/7
 */
@Data
@AllArgsConstructor
public class User {

    private String name;

    private Integer age;

    private String sex;

}
