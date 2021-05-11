package cn.jackiegu.spring.core.advanced.xml.aop;

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
public class SpringXmlAopEntity implements Serializable {

    private static final long serialVersionUID = 5209337105965037129L;

    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }
}
