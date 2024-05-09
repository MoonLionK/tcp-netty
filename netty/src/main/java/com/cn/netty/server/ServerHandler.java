package com.cn.netty.server;



import com.cn.common.config.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可用网络调试助手、Modbus Poll进行调试
 *
 * @author xgy
 */
@Slf4j
@Component
public class ServerHandler extends SimpleChannelInboundHandler<Object> {
    @Getter
    private static final Map<ChannelId, ChannelHandlerContext> SERVER_CONTEXT_MAP = new ConcurrentHashMap<>();

    /**
     * 读取客户端传过来的数据
     * 发送到目标，比如redis，websocket等
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        String string = (String) msg;
        String remoteAddress = ctx.channel().remoteAddress().toString().replace("/", "");

        log.info("[ServerHandler接收数据]-[" + remoteAddress + "]" + string);
        // 构造要发送的消息
        String message = "Hello, client!";

        // 向客户端发送消息
        ctx.channel().writeAndFlush(message);

        //将数据存放到redis队列中
        RedissonClient client = SpringUtils.getBean(RedissonClient.class);
        RQueue<String> queue = client.getQueue(remoteAddress);
        queue.add(string);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof Exception) {
            log.error("ServerHandler异常捕获", cause);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 添加 StringDecoder 和 StringEncoder
        ctx.pipeline().addLast(new StringDecoder(), new StringEncoder());
        // 将客户端的 ChannelHandlerContext 保存到 Map 中
        SERVER_CONTEXT_MAP.put(ctx.channel().id(), ctx);
        Channel channel = ctx.channel();
        String localAddress = channel.localAddress().toString().replace("/", "");
        String remoteAddress = channel.remoteAddress().toString().replace("/", "");
        TcpNettyServer.deviceAdd(localAddress, remoteAddress, ctx.channel());
        log.info("netty-->TCP客户端服务上线：" + channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 当客户端断开连接时,从 Map 中移除对应的 ChannelHandlerContext
        SERVER_CONTEXT_MAP.remove(ctx.channel().id());

        Channel channel = ctx.channel();
        String localAddress = channel.localAddress().toString().replace("/", "");
        String remoteAddress = channel.remoteAddress().toString().replace("/", "");
        TcpNettyServer.deviceRemove(localAddress, remoteAddress, channel);
        // 当通道不活跃时关闭通道,关闭的频道会自动从ChannelGroup集合中删除
        ctx.channel().close();
        log.info("netty-->TCP客户端服务下线：" + channel.remoteAddress());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("接收到客户端信息完成");
        ctx.flush();
        //数据处理完毕后拆包,而后解析数据
        String remoteAddress = ctx.channel().remoteAddress().toString();

    }
}
