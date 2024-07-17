package chc.dts.receive.netty;

import chc.dts.api.pojo.vo.TcpCommonReq;
import chc.dts.api.pojo.vo.TcpInfoResq;
import chc.dts.common.exception.ErrorCode;

import java.util.List;

/**
 * TCP 接口,供前端调用
 *
 * @author xgy
 * @date 2024/5/7 9:41
 */
public interface TcpInterface {

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
     * @return TcpInfoResq
     */
    List<TcpInfoResq> getChannelInfo();

}
