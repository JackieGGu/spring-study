package cn.jackiegu.spring.oneself.aspect;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * 执行时间日志打印切面
 *
 * @author JackieGu
 * @date 2021/3/12
 */
public class LogAspect {

    private static final Log LOGGER = LogFactory.get();

    private final TimeInterval interval = new TimeInterval();

    public void before() {
        this.interval.start();
    }

    public void after() {
        LOGGER.info("execute time: {}", this.interval.intervalMs());
    }
}
