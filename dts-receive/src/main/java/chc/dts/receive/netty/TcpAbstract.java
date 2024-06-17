package chc.dts.receive.netty;

import chc.dts.api.controller.vo.TcpCommonReq;
import chc.dts.common.core.KeyValue;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServerException;
import chc.dts.common.exception.enums.GlobalErrorCodeConstants;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * tcp连接公共抽象类
 *
 * @author xgy
 * @date 2024/5/9 11:02
 */
public abstract class TcpAbstract {

    /**
     * 通道集合
     */
    protected static ChannelGroup deviceChannelGroup;
    /**
     * 设备对应的通道信息 {localAddress:[remoteAddress:ChannelId]}
     */
    protected static final ConcurrentHashMap<String, ArrayList<KeyValue<String, ChannelId>>> DEVICE_MAP = new ConcurrentHashMap<>();

    /**
     * 项目启动时就创建监听/连接
     */
    protected abstract void startNetty();

    /**
     * 根据ip和端口信息获取对应的通道信息
     *
     * @param req TcpCommonReq
     * @return Channel
     */
    public Channel getChannel(TcpCommonReq req) {
        String localAddress = req.getIp() + ":" + req.getPort();
        String remoteAddress = req.getIp() + ":" + req.getRemotePort();
        ArrayList<KeyValue<String, ChannelId>> keyValues = DEVICE_MAP.get(localAddress);
        if (!CollectionUtils.isEmpty(keyValues)) {
            KeyValue<String, ChannelId> keyValue1 = keyValues.stream().filter(keyValue -> keyValue.getKey().equals(remoteAddress))
                    .findFirst().orElseThrow(() -> new ServerException(new ErrorCode(500, "发送消息找不到对应的通道")));
            return deviceChannelGroup.find(keyValue1.getValue());
        }
        return null;
    }

    /**
     * 设备接入
     */
    public static void deviceAdd(String localAddress, String remoteAddress, Channel channel) {
        deviceChannelGroup.add(channel);
        KeyValue<String, ChannelId> keyValue = new KeyValue<>(remoteAddress, channel.id());
        ArrayList<KeyValue<String, ChannelId>> keyValues = DEVICE_MAP.get(localAddress);
        if (CollectionUtils.isEmpty(keyValues)) {
            DEVICE_MAP.put(localAddress, Lists.newArrayList(keyValue));
        } else {
            keyValues.add(keyValue);
            DEVICE_MAP.put(localAddress, keyValues);
        }
    }

    /**
     * 设备移除
     */
    public static void deviceRemove(String localAddress, String remoteAddress, Channel channel) {
        deviceChannelGroup.remove(channel);
        ArrayList<KeyValue<String, ChannelId>> keyValues = DEVICE_MAP.get(localAddress);
        if (!CollectionUtils.isEmpty(keyValues)) {
            keyValues.removeIf(keyValue -> keyValue.getKey().equals(remoteAddress));
            DEVICE_MAP.put(localAddress, keyValues);
        }
    }


    /**
     * 发送消息
     *
     * @param req ip:port + message
     */
    public ErrorCode send(TcpCommonReq req, Map<ChannelId, ChannelHandlerContext> clientContextMap) {
        Channel channel = getChannel(req);
        ChannelHandlerContext channelHandlerContext = null;
        if (channel != null) {
            channelHandlerContext = clientContextMap.get(channel.id());
        }
        if (channelHandlerContext != null) {
            channelHandlerContext.writeAndFlush(req.getMessage());
            return GlobalErrorCodeConstants.SUCCESS;
        }
        return GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;
    }

    /**
     * 关闭设备连接
     *
     * @param tcpCommonReqList [ip:remotePort + message]
     * @return ErrorCode
     */
    public ErrorCode close(List<TcpCommonReq> tcpCommonReqList) {
        for (TcpCommonReq tcpCommonReq : tcpCommonReqList) {
            Channel channel = getChannel(tcpCommonReq);
            if (channel != null) {
                channel.close();
            }
        }
        return GlobalErrorCodeConstants.SUCCESS;
    }
}
