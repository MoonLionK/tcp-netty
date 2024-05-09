package com.cn.controller.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * tcp通用入参
 *
 * @author xgy
 */
@Data
public class TcpCommonReq {
    /**
     * ip
     */
    @NotNull(message = "ip can not be null")
    private String ip;
    /**
     * 监听端口
     */
    @NotNull(message = "port can not be null")
    private Integer port;
    /**
     * 远程端口
     */
    private Integer remotePort;
    /**
     * 信息
     */
    private String message;
}
