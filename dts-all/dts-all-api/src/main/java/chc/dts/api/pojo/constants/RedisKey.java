package chc.dts.api.pojo.constants;

/**
 * @author xgy
 * @date 2024/06/14
 */
public class RedisKey {
    private static final String PREFIX = "dts:";

    /**
     * 动态参数redis缓存KEY
     */
    public static final String DYNAMIC_PARAMETER = PREFIX + "dynamic:parameter";

    /**
     * app redis缓存KEY 语言包
     */
    public static final String LANG_APP_KEY = PREFIX + "app:";
}
