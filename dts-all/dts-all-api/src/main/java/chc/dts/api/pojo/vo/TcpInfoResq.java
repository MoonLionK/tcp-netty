package chc.dts.api.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通道信息返回类
 *
 * @author xgy
 * @date 2024/6/17 10:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TcpInfoResq {
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 状态 0:启动 1关闭
     */
    private Integer status;
    /**
     * 通道信息集合
     */
    private List<RemoteInfo> remoteInfoList;


    /**
     * 通道信息
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RemoteInfo {
        /**
         * ip
         */
        private String ip;
        /**
         * 远程端口
         */
        private String remotePort;
    }
}
