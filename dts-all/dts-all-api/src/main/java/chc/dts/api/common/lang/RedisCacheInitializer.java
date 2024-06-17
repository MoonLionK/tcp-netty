package chc.dts.api.common.lang;


import chc.dts.api.dao.InternalResourceMapper;
import chc.dts.api.entity.InternalResource;
import chc.dts.api.pojo.constants.RedisKey;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description
 *
 * @author hc 2024/03/19 15:30
 */
@Component
@AllArgsConstructor
public class RedisCacheInitializer implements CommandLineRunner {

    private final RedisTemplate<String, Map<String, String>> resourceRedisTemplate;
    private InternalResourceMapper internalResourceMapper;
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) {
        initializeWebCache();
        initializeAppCache();
    }

    /**
     * 初始化前端资源
     */
    private void initializeWebCache() {

        List<InternalResource> webList = internalResourceMapper.selectList(new LambdaQueryWrapper<InternalResource>()
                .eq(InternalResource::getType, 1)
                .or().eq(InternalResource::getType, 0));
        Map<String, List<InternalResource>> maps = webList.stream().collect(Collectors.groupingBy(InternalResource::getLang));
        for (Map.Entry<String, List<InternalResource>> stringListEntry : maps.entrySet()) {
            String key = stringListEntry.getKey();
            Map<String, String> map = stringListEntry.getValue().stream()
                    .collect(Collectors.toMap(InternalResource::getKey, InternalResource::getText));
            resourceRedisTemplate.opsForValue().set("web:" + key, map);
        }
    }

    /**
     * 初始化后端资源
     */
    private void initializeAppCache() {
        List<InternalResource> webList = internalResourceMapper.selectList(new LambdaQueryWrapper<InternalResource>()
                .eq(InternalResource::getType, 2)
                .or().eq(InternalResource::getType, 0));
        Map<String, List<InternalResource>> maps = webList.stream().collect(Collectors.groupingBy(InternalResource::getLang));
        for (Map.Entry<String, List<InternalResource>> stringListEntry : maps.entrySet()) {
            String key = stringListEntry.getKey();
            Map<String, String> map = stringListEntry.getValue().stream()
                    .collect(Collectors.toMap(InternalResource::getKey, InternalResource::getText));
            resourceRedisTemplate.opsForValue().set(RedisKey.LANG_APP_KEY + key, map);
        }
        redisTemplate.opsForValue().set("RADAR-BUILD-QUICK_TABLE:", "false");
    }
}
