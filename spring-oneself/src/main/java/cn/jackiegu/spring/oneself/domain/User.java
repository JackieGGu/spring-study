package cn.jackiegu.spring.oneself.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
public class User implements Serializable {

    private static final long serialVersionUID = -3084703631198632996L;

    private String id;

    private String name;

    private Integer sex;

    private Integer age;

}
