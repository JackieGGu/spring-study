package cn.jackiegu.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 自定义监听器
 *
 * @author JackieGu
 * @date 2022/1/10
 */
public class CustomSpringApplicationRunListener implements SpringApplicationRunListener {

    private final Logger logger = LoggerFactory.getLogger(CustomSpringApplicationRunListener.class);


    public CustomSpringApplicationRunListener(SpringApplication application, String[] args) {
        logger.info("constructor...");
    }

    @Override
    public void starting() {
        logger.info("starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        logger.info("environmentPrepared...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        logger.info("contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        logger.info("contextLoaded...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        logger.info("started...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        logger.info("running...");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        logger.info("failed...");
    }
}
