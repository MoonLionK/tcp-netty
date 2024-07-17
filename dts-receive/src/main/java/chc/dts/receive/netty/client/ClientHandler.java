package chc.dts.receive.netty.client;

import chc.dts.common.util.object.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RQueue;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TcpNettyClient消息处理类
 *
 * @author xgy
 * @date 2024/5/9 9:59
 */
@Slf4j
@Component
public class ClientHandler extends SimpleChannelInboundHandler<Object> {
    @Getter
    private static final Map<ChannelId, ChannelHandlerContext> CLIENT_CONTEXT_MAP = new ConcurrentHashMap<>();
    private TcpNettyClient tcpNettyClient;

    public ClientHandler(TcpNettyClient tcpNettyClient) {
        this.tcpNettyClient = tcpNettyClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        String string = (String) msg;
        String remoteAddress = ctx.channel().remoteAddress().toString().replace("/", "");

        log.info("[ClientHandler接收数据]-[" + remoteAddress + "]" + string);
        // 构造要发送的消息
        String message = "Hello, sever!";

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
            log.error("ClientHandler异常捕获", cause);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // 将客户端的 ChannelHandlerContext 保存到 Map 中
        CLIENT_CONTEXT_MAP.put(ctx.channel().id(), ctx);
        Channel channel = ctx.channel();
        String localAddress = channel.localAddress().toString().replace("/", "");
        String remoteAddress = channel.remoteAddress().toString().replace("/", "");
        tcpNettyClient.deviceAdd(remoteAddress, localAddress, ctx.channel());
        log.info("netty-->TCP客户端服务已连接：" + channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 当客户端断开连接时,从 Map 中移除对应的 ChannelHandlerContext
        CLIENT_CONTEXT_MAP.remove(ctx.channel().id());
        Channel channel = ctx.channel();
        String localAddress = channel.localAddress().toString().replace("/", "");
        String remoteAddress = channel.remoteAddress().toString().replace("/", "");
        tcpNettyClient.deviceRemove(localAddress, remoteAddress, channel);
        // 当通道不活跃时关闭通道,关闭的频道会自动从ChannelGroup集合中删除
        ctx.channel().close();
        log.info("netty-->TCP客户端服务断开连接：" + channel.remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("接收到服务端信息完成");
        ctx.flush();
    }
}
