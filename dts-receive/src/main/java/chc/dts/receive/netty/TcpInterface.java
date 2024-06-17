package chc.dts.receive.netty;

import chc.dts.api.controller.vo.TcpCommonReq;
import chc.dts.api.controller.vo.TcpInfoResq;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.pojo.CommonResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * TCP 接口,供前端调用
 *
 * @author xgy
 * @date 2024/5/7 9:41
 */
public interface TcpInterface {

    /**
     * 新增ip和端口监听/连接
     *
     * @param keyValue ip:port
     * @return ErrorCode
     */
    CommonResult<String> connect(TcpCommonReq keyValue) throws ExecutionException, InterruptedException;

    /**
     * 发送消息
     *
     * @param req ip:port + message
     * @return ErrorCode
     */
    ErrorCode sendMessage(TcpCommonReq req);

    /**
     * 关闭设备连接
     *
     * @param tcpCommonReqList [ip:remotePort + message]
     * @return ErrorCode
     */
    ErrorCode closeChannel(List<TcpCommonReq> tcpCommonReqList);


    /**
     * 查询连接信息
     *
     * @param port 本地端口,不传查所有
     * @return TcpInfoResq
     */
    List<TcpInfoResq> getChannelInfo(String port);
}
