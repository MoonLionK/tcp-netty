package chc.dts.api.common.lang;


import chc.dts.api.dao.InternalResourceMapper;
import chc.dts.api.entity.InternalResource;
import chc.dts.api.pojo.constants.RedisKey;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author fyh 2024/06/14 14:51
 */
@Component
@AllArgsConstructor
public class ResourceCache {

    private RedisTemplate<String, Map<String, String>> redisTemplate;
    private final InternalResourceMapper internalResourceMapper;
    private final RedisTemplate<String, Map<String, String>> resourceRedisTemplate;
    private static final Pattern PATTERN = Pattern.compile("\\{\\}");

    /**
     * @param input  code
     * @param obj    无意义
     * @param locale 语言
     * @return 该语言结果
     */
    public String getMessage(String input, Object[] obj, Locale locale) {
        // 解析占位符
        input = getResourceText(input, locale.toString());
        Matcher matcher = PATTERN.matcher(input);

        // 替换占位符
        StringBuffer sb = new StringBuffer();
        int index = 0;
        while (matcher.find()) {
            if (obj != null && index < obj.length) {

                String replacement = String.valueOf(obj[index]);
                matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
                index++;
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private String getResourceText(String key, String lang) {
        String cacheKey = RedisKey.LANG_APP_KEY + lang;
        // 尝试从 Redis 缓存中获取数据
        Map<String, String> cachedText = redisTemplate.opsForValue().get(cacheKey);
        if (cachedText != null && cachedText.containsKey(key)) {
            return cachedText.get(key);
        } else {
            //同步所有AppCache
            addAppCacheAll();
        }

        // Redis 缓存中不存在，从 MySQL 数据库中获取数据
        String text = fetchResourceTextFromDatabase(key, lang);

        // 将数据存储到 Redis 缓存中
        if (Objects.isNull(text)) {
            //如果数据库为空，则返回key
            text = key;
        }

        return text;
    }

    private String fetchResourceTextFromDatabase(String key, String lang) {
        return internalResourceMapper.getResourceByKeyAndLang(key, lang);
    }

    private void addAppCacheAll() {
        List<InternalResource> webList = internalResourceMapper.getResourceApp();
        Map<String, List<InternalResource>> maps = webList.stream().collect(Collectors.groupingBy(InternalResource::getLang));
        for (Map.Entry<String, List<InternalResource>> stringListEntry : maps.entrySet()) {
            String key = stringListEntry.getKey();
            Map<String, String> map = stringListEntry.getValue().stream()
                    .collect(Collectors.toMap(InternalResource::getKey, InternalResource::getText));
            resourceRedisTemplate.opsForValue().set(RedisKey.LANG_APP_KEY + key, map);
        }
    }

}
