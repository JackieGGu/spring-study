package cn.jackiegu.spring.transaction.mixture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpringTransactionMixtureEntity {

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
