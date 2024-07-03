package chc.dts.api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 本地连接信息返回类
 *
 * @author xgy
 * @date 2024/6/18 10:06
 */
@Data
@AllArgsConstructor
public class LocalInfoResq {
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 0:启动 1关闭
     */
    private Integer status;
}
