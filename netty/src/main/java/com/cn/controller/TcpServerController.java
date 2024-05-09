package com.cn.controller;


import com.cn.common.exception.ErrorCode;
import com.cn.common.exception.ServiceException;
import com.cn.common.pojo.CommonResult;
import com.cn.controller.vo.TcpCommonReq;
import com.cn.netty.server.TcpNettyServer;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * TcpServer前端控制器
 *
 * @author xgy
 * @since 2024-04-29
 */
@Validated
@RestController
@RequestMapping("/server")
public class TcpServerController {
    @Resource
    private TcpNettyServer tcpNettyServer;

    @PostMapping("/connect")
    @Operation(summary = "连接tcp客户端")
    public CommonResult<String> connect(@Validated @RequestBody TcpCommonReq req) {
        ErrorCode errorCode = tcpNettyServer.connect(req);
        return new CommonResult<>(errorCode);
    }

    @PostMapping("/send")
    @Operation(summary = "发送消息至tcp客户端")
    public CommonResult<String> sendMessage(@Validated @RequestBody TcpCommonReq req) {
        if (ObjectUtils.isEmpty(req.getRemotePort())) {
            throw new ServiceException(new ErrorCode(400, "remotePort can not be null"));
        }
        ErrorCode errorCode = tcpNettyServer.sendMessage(req);
        return new CommonResult<>(errorCode);
    }

    @PostMapping("/closeChannel")
    @Operation(summary = "关闭通道")
    public CommonResult<String> closeChannel(@RequestBody @Valid List<TcpCommonReq> tcpCommonReqList) {
        for (TcpCommonReq req : tcpCommonReqList) {
            if (ObjectUtils.isEmpty(req.getRemotePort())) {
                throw new ServiceException(new ErrorCode(400, "remotePort can not be null"));
            }
        }
        ErrorCode errorCode = tcpNettyServer.closeChannel(tcpCommonReqList);
        return new CommonResult<>(errorCode);
    }
}
