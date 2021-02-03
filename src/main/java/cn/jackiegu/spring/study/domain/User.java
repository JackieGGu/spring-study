package cn.jackiegu.spring.study.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户实体类
 *
 * @author JackieGu
 * @date 2021/2/3
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String name;

    private Integer sex;

    private Integer age;

}
