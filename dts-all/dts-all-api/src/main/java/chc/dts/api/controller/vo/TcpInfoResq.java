package chc.dts.api.controller.vo;

import lombok.Data;

import java.util.List;

/**
 * 通道信息返回类
 *
 * @author xgy
 * @date 2024/6/17 10:03
 */
@Data
public class TcpInfoResq {
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private String port;
    /**
     * 通道信息集合
     */
    private List<RemoteInfo> remoteInfoList;


    /**
     * 通道信息
     */
    @Data
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
