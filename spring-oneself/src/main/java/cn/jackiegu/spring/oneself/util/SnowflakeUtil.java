package cn.jackiegu.spring.oneself.util;

import cn.hutool.core.lang.Snowflake;

/**
 * 雪花算法ID生成类工具
 *
 * @author JackieGu
 * @date 2021/2/3
 */
public class SnowflakeUtil {

    private static final Snowflake snowflake;

    static {
        snowflake = new Snowflake(0L, 0L);
    }

    public static String nextId() {
        return String.valueOf(SnowflakeUtil.snowflake.nextId());
    }
}
