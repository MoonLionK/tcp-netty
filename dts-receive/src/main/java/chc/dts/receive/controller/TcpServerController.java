package chc.dts.receive.controller;

import chc.dts.api.controller.vo.TcpCommonReq;
import chc.dts.api.controller.vo.TcpInfoResq;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.pojo.CommonResult;
import chc.dts.receive.netty.server.TcpNettyServer;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/info")
    @Operation(summary = "查询连接信息")
    public CommonResult<List<TcpInfoResq>> getChannelInfo(@RequestParam(value = "port", required = false) String port) {
        return CommonResult.success(tcpNettyServer.getChannelInfo(port));
    }

    @PostMapping("/connect")
    @Operation(summary = "连接tcp客户端")
    public CommonResult<String> connect(@Validated @RequestBody TcpCommonReq req) throws ExecutionException, InterruptedException {
        return tcpNettyServer.connect(req);
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
