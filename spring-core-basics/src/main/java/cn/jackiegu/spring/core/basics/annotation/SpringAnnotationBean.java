package cn.jackiegu.spring.core.basics.annotation;

public class SpringAnnotationBean {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
