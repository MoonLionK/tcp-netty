package chc.dts.api.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;


/**
 * @author fyh
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    @Bean("RedisTemplate")
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        // 设置读取超时
        factory.setReadTimeout(30000);
        // 设置连接超时，单位毫秒
        factory.setConnectTimeout(60000);
        RestTemplate restTemplate = new RestTemplate(factory);
        // 支持中文编码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        log.info("RestTemplate初始化完成");
        return restTemplate;
    }

}
