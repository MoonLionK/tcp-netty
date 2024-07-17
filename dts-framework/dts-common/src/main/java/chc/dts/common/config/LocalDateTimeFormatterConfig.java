package chc.dts.common.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * LocalDateTime 格式化
 *
 * @author xgy
 */
@Configuration
public class LocalDateTimeFormatterConfig extends SimpleModule {
    public LocalDateTimeFormatterConfig() {
        super();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 添加自定义的序列化器
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        // 如果需要，也可以添加自定义的反序列化器
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
    }
}
