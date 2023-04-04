package cn.jackiegu.spring.ioc.annotation;

public class SpringIocAnnotationBean {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "name=" + name;
    }
}
