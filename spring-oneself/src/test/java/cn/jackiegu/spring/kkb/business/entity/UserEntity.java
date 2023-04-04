package cn.jackiegu.spring.kkb.business.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 6466586534987116818L;

    private Integer id;

    private String name;

    private Integer age;

    private String sex;

    @Override
    public String toString() {
        return "id=" + id +
            ", name=" + name +
            ", age=" + age +
            ", sex=" + sex;
    }
}
