package chc.dts.receive.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 报文日志单独记录 由logback-spring.xml中配置实现
 * 日志文件输出的文件名: <FileNamePattern>${LOG_PATH}/${LOG_DIR}/message.log.%d{yyyy-MM-dd}.log</FileNamePattern>
 *
 * @author xgy
 * @date 2024/5/14 10:48
 */
@Component
@Slf4j
public class MessageLog {
    public void messageInfo(String localAddress, String remoteAddress, String msg) {
        log.info("[ServerHandler接收数据]-localAddress:{},remoteAddress:{},msg:{}", localAddress, remoteAddress, msg);
    }
}
