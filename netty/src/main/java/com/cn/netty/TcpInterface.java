package com.cn.netty;


import com.cn.common.exception.ErrorCode;
import com.cn.controller.vo.TcpCommonReq;

import java.util.List;

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
    ErrorCode connect(TcpCommonReq keyValue);

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


}
