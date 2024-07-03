package chc.dts.receive.controller;

import chc.dts.api.pojo.vo.TcpCommonReq;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.pojo.CommonResult;
import chc.dts.receive.netty.client.TcpNettyClient;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * TcpClient前端控制器
 *
 * @author xgy
 * @since 2024-04-29
 */
@RestController
@RequestMapping("/client")
public class TcpClientController {
    @Resource
    private TcpNettyClient tcpNettyClient;

    @PostMapping("/connect")
    @Operation(summary = "连接tcp服务器")
    public CommonResult<String> connect(@Valid @RequestBody TcpCommonReq req) {
        return tcpNettyClient.connect(req);
    }

    @PostMapping("/send")
    @Operation(summary = "发送消息至tcp服务器")
    public CommonResult<String> send(@Valid @RequestBody TcpCommonReq req) {
        if (ObjectUtils.isEmpty(req.getRemotePort())) {
            throw new ServiceException(new ErrorCode(400, "remotePort can not be null"));
        }
        ErrorCode errorCode = tcpNettyClient.sendMessage(req);
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
        ErrorCode errorCode = tcpNettyClient.closeChannel(tcpCommonReqList);
        return new CommonResult<>(errorCode);
    }
}
