package chc.dts.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xgy
 * @date 2024/5/13 15:47
 */
@Data
@AllArgsConstructor
public class RedisMessage {
    /**
     * 本地地址
     */
    private String localAddress;
    /**
     * 远程地址
     */
    private String remoteAddress;
    /**
     * 消息
     */
    private String message;


}
