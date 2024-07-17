package chc.dts.receive.controller;

import chc.dts.api.entity.ConnectInfo;
import chc.dts.api.pojo.vo.TcpCommonReq;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.pojo.CommonResult;
import chc.dts.common.pojo.PageParam;
import chc.dts.common.pojo.PageResult;
import chc.dts.receive.controller.vo.ClientUpdateReq;
import chc.dts.receive.netty.client.TcpNettyClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @PostMapping("/save")
    @Operation(summary = "新增客户端连接")
    public CommonResult<Boolean> save(@Valid @RequestParam @NotNull @Schema(description = "ip", requiredMode = Schema.RequiredMode.REQUIRED) String ip,
                                      @Valid @RequestParam @NotNull @Schema(description = "端口", requiredMode = Schema.RequiredMode.REQUIRED) Integer port) {
        return tcpNettyClient.save(ip, port);
    }

    @PostMapping("/update")
    @Operation(summary = "修改客户端连接")
    public CommonResult<Boolean> update(@Valid @RequestBody ClientUpdateReq req) {
        return tcpNettyClient.update(req);
    }

    @PostMapping("/info")
    @Operation(summary = "查询客户端连接信息")
    public CommonResult<PageResult<ConnectInfo>> info(@Valid @RequestBody PageParam pageParam) {
        return tcpNettyClient.info(pageParam);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除客户端连接信息")
    public CommonResult<Boolean> delete(@Valid @RequestParam @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED) Integer id) {
        return tcpNettyClient.delete(id);
    }

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
