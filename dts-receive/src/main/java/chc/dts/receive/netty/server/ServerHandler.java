package chc.dts.receive.netty.server;


import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * tcp事件监听处理器
 *
 * @author xgy
 */
@Slf4j
@Component
public class ServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Getter
    private static final Map<ChannelId, ChannelHandlerContext> SERVER_CONTEXT_MAP = new ConcurrentHashMap<>();
    private final TcpNettyServer tcpNettyServer;

    public ServerHandler(TcpNettyServer tcpNettyServer) {
        this.tcpNettyServer = tcpNettyServer;
    }

    /**
     * @param ctx 上下文
     * @param evt 事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            //将  evt 向下转型 IdleStateEvent
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            log.info(ctx.channel().remoteAddress() + "--超时时间--" + eventType);

            //如果发生空闲，我们关闭通道
            // ctx.channel().close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) {
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
        tcpNettyServer.deviceAdd(localAddress, remoteAddress, ctx.channel());
        log.info("netty-->TCP客户端服务上线：" + channel.remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 当客户端断开连接时,从 Map 中移除对应的 ChannelHandlerContext
        SERVER_CONTEXT_MAP.remove(ctx.channel().id());

        Channel channel = ctx.channel();
        String localAddress = channel.localAddress().toString().replace("/", "");
        String remoteAddress = channel.remoteAddress().toString().replace("/", "");
        tcpNettyServer.deviceRemove(localAddress, remoteAddress, channel);
        // 当通道不活跃时关闭通道,关闭的频道会自动从ChannelGroup集合中删除
        ctx.channel().close();
        log.info("netty-->TCP客户端服务下线：" + channel.remoteAddress());

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("接收到客户端信息完成");
        ctx.flush();
    }
}
