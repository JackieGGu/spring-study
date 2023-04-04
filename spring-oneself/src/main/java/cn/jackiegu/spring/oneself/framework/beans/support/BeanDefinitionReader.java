package cn.jackiegu.spring.oneself.framework.beans.support;

import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.setting.dialect.Props;
import cn.jackiegu.spring.oneself.framework.annotation.Controller;
import cn.jackiegu.spring.oneself.framework.annotation.Service;
import cn.jackiegu.spring.oneself.framework.beans.config.BeanDefinition;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring Bean 定义阅读器类
 *
 * @author JackieGu
 * @date 2021/2/2
 */
public class BeanDefinitionReader {

    private static final Log LOGGER = LogFactory.get();

    /**
     * 配置文件
     */
    private final Props props = new Props();

    /**
     * 待注册的Bean Class
     */
    private final List<String> registryBeanClasses = new ArrayList<>();

    /**
     * 别名
     */
    private final Map<String, String> aliasMap = new HashMap<>();

    /**
     * SpringBean 阅读器构造方法
     *
     * @param configLocation 配置文件位置
     */
    @SuppressWarnings("all")
    public BeanDefinitionReader(String configLocation) {
        LOGGER.info("Instantiate BeanDefinitionReader");
        try {
            String contextConfigLocation = configLocation.replaceAll("classpath:", "");
            ClassPathResource resource = new ClassPathResource(contextConfigLocation);
            this.props.load(resource.getStream());
        } catch (Exception e) {
            LOGGER.error(e, "加载上下文配置文件异常");
        }
        this.scanner(props.getStr("scan.package"));
    }

    /**
     * 扫描所有class文件
     *
     * @param scanPackage 扫描的包
     */
    @SuppressWarnings("all")
    private void scanner(String scanPackage) {
        LOGGER.info("Scan package: {}", scanPackage);
        if (StrUtil.isBlank(scanPackage)) {
            return;
        }

        String path = "/" + scanPackage.replaceAll("\\.", "/");
        // 获取扫描路径的URL
        URL url = this.getClass().getClassLoader().getResource(path);
        if (url == null) {
            return;
        }

        // 获取扫描路径的File
        File classPath = new File(url.getFile());
        File[] files = classPath.listFiles();
        if (files == null) {
            return;
        }
        // 递归遍历
        for (File file : files) {
            if (file.isDirectory()) {
                this.scanner(scanPackage + "." + file.getName());
            } else {
                if (!file.getName().endsWith(".class")) {
                    continue;
                }
                this.registryBeanClasses.add(scanPackage + "." + file.getName().replace(".class", ""));
            }
        }
    }

    /**
     * 加载SpringBean定义配置
     */
    public List<BeanDefinition> loadBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = new ArrayList<>();
        try {
            for (String className : this.registryBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                if (beanClass.isInterface()) {
                    continue;
                }
                if (!beanClass.isAnnotationPresent(Controller.class) && !beanClass.isAnnotationPresent(Service.class)) {
                    continue;
                }
                String factoryBeanName = this.toLowerFirstCase(beanClass.getSimpleName());
                String beanClassName = beanClass.getName();
                beanDefinitions.add(this.createBeanDefinition(factoryBeanName, beanClassName));

                // 接口注入
                for (Class<?> i : beanClass.getInterfaces()) {
                    this.aliasMap.put(i.getName(), factoryBeanName);
                }
            }
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
        return beanDefinitions;
    }

    /**
     * 确定实际的BeanName
     * @param name Bean名称, 也许是别名
     * @return 实际的BeanName
     */
    public String canonicalName(String name) {
        String canonicalName = name;
        String resolvedName;
        do {
            resolvedName = this.aliasMap.get(canonicalName);
            if (resolvedName != null) {
                canonicalName = resolvedName;
            }
        } while (resolvedName != null);
        return canonicalName;
    }

    /**
     * 获取应用配置
     */
    public Props getConfig() {
        return this.props;
    }

    /**
     * 创建Bean定义对象
     *
     * @param factoryBeanName Bean工厂名称
     * @param beanClassName   Bean类名称
     */
    private BeanDefinition createBeanDefinition(String factoryBeanName, String beanClassName) {
        return new BeanDefinition(factoryBeanName, beanClassName);
    }

    /**
     * 将类名首字母小写
     *
     * @param simpleName 类名
     * @return 首字母小写的类名
     */
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return new String(chars);
    }
}
