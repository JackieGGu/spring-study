package cn.jackiegu.spring.ioc.xml;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpringDiXmlBean {

    private Integer id;

    private String name;

    private SpringDiXmlDao dao;

    private List<String> list;

    private Set<String> set;

    private Map<String, String> map;

    public SpringDiXmlBean() {
        // this.list = new ArrayList<>();
        // this.set = new LinkedHashSet<>();
        // this.map = new HashMap<>();
    }

    public SpringDiXmlBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDao(SpringDiXmlDao dao) {
        this.dao = dao;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "id=" + id +
            ", name=" + name +
            ", dao=" + dao +
            ", list=" + list +
            ", set=" + set +
            ", map=" + map;
    }
}
