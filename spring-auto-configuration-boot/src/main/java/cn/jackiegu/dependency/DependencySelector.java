package cn.jackiegu.dependency;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 自定义导入选择器
 *
 * @author JackieGu
 * @date 2021/7/15
 */
public class DependencySelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{DependencyMarker.class.getName()};
    }
}
