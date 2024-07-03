package chc.dts.receive.netty;

import chc.dts.api.dao.ChannelInfoMapper;
import chc.dts.api.entity.ChannelInfo;
import chc.dts.api.entity.ConnectInfo;
import chc.dts.api.pojo.vo.TcpCommonReq;
import chc.dts.api.service.IConnectInfoService;
import chc.dts.common.core.KeyValue;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.exception.enums.GlobalErrorCodeConstants;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * tcp连接公共抽象类
 *
 * @author xgy
 * @date 2024/5/9 11:02
 */
public abstract class TcpAbstract {
    public IConnectInfoService iConnectInfoService;
    public ChannelInfoMapper channelInfoMapper;

    /**
     * 通道集合
     */
    protected static ChannelGroup deviceChannelGroup;
    /**
     * 设备对应的通道信息 {localAddress:[remoteAddress:ChannelId]}
     */
    protected static final ConcurrentHashMap<String, ArrayList<KeyValue<String, ChannelId>>> DEVICE_MAP = new ConcurrentHashMap<>();

    protected TcpAbstract(IConnectInfoService iConnectInfoService, ChannelInfoMapper channelInfoMapper) {
        this.iConnectInfoService = iConnectInfoService;
        this.channelInfoMapper = channelInfoMapper;
    }

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
            Optional<KeyValue<String, ChannelId>> first = keyValues.stream().filter(keyValue -> keyValue.getKey().equals(remoteAddress)).findFirst();
            return first.map(stringChannelIdKeyValue -> deviceChannelGroup.find(stringChannelIdKeyValue.getValue())).orElse(null);
        }
        return null;
    }

    /**
     * 设备接入
     */
    public void deviceAdd(String localAddress, String remoteAddress, Channel channel) {
        deviceChannelGroup.add(channel);
        KeyValue<String, ChannelId> keyValue = new KeyValue<>(remoteAddress, channel.id());
        ArrayList<KeyValue<String, ChannelId>> keyValues = DEVICE_MAP.get(localAddress);
        if (CollectionUtils.isEmpty(keyValues)) {
            DEVICE_MAP.put(localAddress, Lists.newArrayList(keyValue));
        } else {
            keyValues.add(keyValue);
            DEVICE_MAP.put(localAddress, keyValues);
        }

        String[] split = localAddress.split(":");
        String ip = split[0];
        String localPort = split[1];
        String[] split1 = remoteAddress.split(":");
        String remotePort = split1[1];

        ChannelInfo channelInfo = channelInfoMapper.selectOne(new LambdaQueryWrapper<ChannelInfo>()
                .eq(ChannelInfo::getIp, "0.0.0.0")
                .eq(ChannelInfo::getPort, localPort));
        if (ObjectUtils.isEmpty(channelInfo)) {
            throw new ServiceException(500, "找不到对应的监听信息:" + ip + ":" + localPort);
        }
        ConnectInfo connectInfo = new ConnectInfo()
                .setIp(ip)
                .setRemotePort(Integer.valueOf(remotePort))
                .setChannelId(channelInfo.getId())
                .setAutoConnect(false)
                .setStatus(0);
        connectInfo.setCreator(1);
        connectInfo.setUpdater(1);
        iConnectInfoService.saveOrUpdate(connectInfo);


    }

    /**
     * 设备移除
     */
    public void deviceRemove(String localAddress, String remoteAddress, Channel channel) {
        deviceChannelGroup.remove(channel);
        ArrayList<KeyValue<String, ChannelId>> keyValues = DEVICE_MAP.get(localAddress);
        if (!CollectionUtils.isEmpty(keyValues)) {
            keyValues.removeIf(keyValue -> keyValue.getKey().equals(remoteAddress));
            DEVICE_MAP.put(localAddress, keyValues);
        }

        String[] split = localAddress.split(":");
        String ip = split[0];
        String localPort = split[1];
        String[] split1 = remoteAddress.split(":");
        String remotePort = split1[1];

        ChannelInfo channelInfo = channelInfoMapper.selectOne(new LambdaQueryWrapper<ChannelInfo>()
                .eq(ChannelInfo::getIp, "0.0.0.0")
                .eq(ChannelInfo::getPort, localPort));
        if (ObjectUtils.isEmpty(channelInfo)) {
            throw new ServiceException(500, "找不到对应的监听信息:" + ip + ":" + localPort);
        }

        iConnectInfoService.update(new LambdaUpdateWrapper<ConnectInfo>()
                .eq(ConnectInfo::getIp, ip)
                .eq(ConnectInfo::getRemotePort, remotePort)
                .eq(ConnectInfo::getChannelId, channelInfo.getId())
                .set(ConnectInfo::getStatus, 1));

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
                ArrayList<KeyValue<String, ChannelId>> keyValues = DEVICE_MAP.get(tcpCommonReq.getIp() + ":" + tcpCommonReq.getPort());
                List<KeyValue<String, ChannelId>> collect = keyValues.stream()
                        .filter(kayValue -> !kayValue.getKey().equals(tcpCommonReq.getIp() + ":" + tcpCommonReq.getRemotePort()))
                        .collect(Collectors.toList());
                DEVICE_MAP.put(tcpCommonReq.getIp() + ":" + tcpCommonReq.getPort(), Lists.newArrayList(collect));
                channel.close();
            } else {
                return new ErrorCode(500, tcpCommonReq.getIp() + ":" + tcpCommonReq.getRemotePort() + "找不到对应的通道信息");
            }
        }
        return GlobalErrorCodeConstants.SUCCESS;
    }
}
