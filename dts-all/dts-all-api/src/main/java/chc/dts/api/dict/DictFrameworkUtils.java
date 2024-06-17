package chc.dts.api.dict;

import chc.dts.api.service.DictDataApi;
import chc.dts.api.pojo.dto.DictDataRespDTO;
import chc.dts.common.core.KeyValue;
import chc.dts.common.util.cache.CacheUtils;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

/**
 * 字典工具类,基于Guava Cache本地缓存实现,第一次查询会查mysql数据库,而后放入本地缓存一分钟
 *
 * @author xgy
 */
@Slf4j
public class DictFrameworkUtils {

    private static DictDataApi dictDataApi;

    private static final DictDataRespDTO DICT_DATA_NULL = new DictDataRespDTO();

    /**
     * 针对 {@link #getDictDataLabel(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, DictDataRespDTO> GET_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, DictDataRespDTO>() {

                @Override
                public DictDataRespDTO load(KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(dictDataApi.getDictData(key.getKey(), key.getValue()), DICT_DATA_NULL);
                }

            });

    /**
     * 针对 {@link #parseDictDataValue(String, String)} 的缓存
     */
    private static final LoadingCache<KeyValue<String, String>, DictDataRespDTO> PARSE_DICT_DATA_CACHE = CacheUtils.buildAsyncReloadingCache(
            Duration.ofMinutes(1L), // 过期时间 1 分钟
            new CacheLoader<KeyValue<String, String>, DictDataRespDTO>() {

                @Override
                public DictDataRespDTO load(KeyValue<String, String> key) {
                    return ObjectUtil.defaultIfNull(dictDataApi.parseDictData(key.getKey(), key.getValue()), DICT_DATA_NULL);
                }

            });

    public static void init(DictDataApi dictDataApi) {
        DictFrameworkUtils.dictDataApi = dictDataApi;
        log.info("[init][初始化 DictFrameworkUtils 成功]");
    }

    @SneakyThrows
    public static String getDictDataLabel(String dictType, Integer value) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, String.valueOf(value))).getLabel();
    }

    @SneakyThrows
    public static String getDictDataLabel(String dictType, String value) {
        return GET_DICT_DATA_CACHE.get(new KeyValue<>(dictType, value)).getLabel();
    }

    @SneakyThrows
    public static String parseDictDataValue(String dictType, String label) {
        return PARSE_DICT_DATA_CACHE.get(new KeyValue<>(dictType, label)).getValue();
    }

}
