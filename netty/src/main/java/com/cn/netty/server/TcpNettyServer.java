package com.cn.netty.server;


import com.cn.common.core.KeyValue;
import com.cn.common.exception.ErrorCode;
import com.cn.common.exception.enums.GlobalErrorCodeConstants;
import com.cn.controller.vo.TcpCommonReq;
import com.cn.netty.TcpAbstract;
import com.cn.netty.TcpInterface;
import com.cn.service.IDeviceService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cn.common.config.ThreadPoolConfig.COMMON_POOL;
import static com.cn.common.config.ThreadPoolConfig.NETTY_SERVER_CONNECT_POOL;
import static com.cn.common.constant.TcpConstant.TCP_SERVER;


/**
 * TcpNetty服务端代码支持多线程数据处理和多端口监听
 *
 * @author xgy
 */
@Slf4j
@Component
public class TcpNettyServer extends TcpAbstract implements TcpInterface {
    @Resource
    private IDeviceService deviceService;
    @Resource(name = NETTY_SERVER_CONNECT_POOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource(name = COMMON_POOL)
    private ThreadPoolTaskExecutor commonThreadPoolTaskExecutor;

    //创建服务器端的启动对象，配置参数
    ServerBootstrap bootstrap = new ServerBootstrap();
    //bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //默认实际 cpu核数 * 2
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     * 根据设备信息获取启动时需要监听的端口信息
     */
    @Override
    @PostConstruct
    public void startNetty() {
        ArrayList<KeyValue<String, Integer>> keyValues = deviceService.selectActiveIpAndPort(TCP_SERVER);
        commonThreadPoolTaskExecutor.execute(() -> {
            try {
                this.init(keyValues);
            } catch (Exception e) {
                log.error("TcpNettyServer startNetty error", e);
            }
        });
    }

    /**
     * 新增ip和端口监听
     *
     * @param req ip:port
     */
    @Override
    public ErrorCode connect(TcpCommonReq req) {
        threadPoolTaskExecutor.execute(() -> {
            try {
                ChannelFuture cf = bootstrap.bind(req.getIp(), req.getPort()).sync();
                log.info("新增监听ip和端口号:" + req.getIp() + ":" + req.getPort());
                //对关闭通道进行监听
                cf.channel().closeFuture().sync();
            } catch (Exception e) {
                log.error("Netty connect error", e);
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });
        return GlobalErrorCodeConstants.SUCCESS;
    }

    /**
     * 发送消息
     *
     * @param req ip:port + message
     */
    @Override
    public ErrorCode sendMessage(TcpCommonReq req) {
        Map<ChannelId, ChannelHandlerContext> clientContextMap = ServerHandler.getSERVER_CONTEXT_MAP();
        return send(req, clientContextMap);
    }

    @Override
    public ErrorCode closeChannel(List<TcpCommonReq> tcpCommonReqList) {
        return close(tcpCommonReqList);
    }


    private void init(ArrayList<KeyValue<String, Integer>> keyValues) throws InterruptedException {
        deviceChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        try {
            //使用链式编程来进行设置,设置两个线程组
            bootstrap.group(bossGroup, workerGroup)
                    //使用NioSocketChannel 作为服务器的通道实现
                    .channel(NioServerSocketChannel.class)
                    // 设置线程队列得到连接个数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //设置保持活动连接状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //创建一个通道初始化对象(匿名对象)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline 设置处理器
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            //可以使用一个集合管理 SocketChannel， 再推送消息时，可以将业务加入到各个channel 对应的 NIOEventLoop 的 taskQueue 或者 scheduleTaskQueue
                            //设置log监听器，并且日志级别为debug，方便观察运行流程
                            ch.pipeline().addLast("logging", new LoggingHandler("DEBUG"));
                            //编码器。发送消息时候用
                            ch.pipeline().addLast("encode", new StringEncoder());
                            //解码器，接收消息时候用
                            ch.pipeline().addLast("decode", new StringDecoder());
                            //业务处理类，最终的消息会在这个handler中进行业务处理
                            ch.pipeline().addLast("handler", new ServerHandler());

                        }
                    });
            //启动服务器(并绑定端口),此处必须使用2个循环若改变顺序就无法监听多个端口
            List<ChannelFuture> channelFutureList = new ArrayList<>();
            for (KeyValue<String, Integer> keyValue : keyValues) {
                ChannelFuture cf = bootstrap.bind(keyValue.getKey(), keyValue.getValue()).sync();
                log.info("已监听ip和端口号:" + keyValue.getKey() + ":" + keyValue.getValue());
                channelFutureList.add(cf);
            }
            //对关闭通道进行监听
            for (ChannelFuture channelFuture : channelFutureList) {
                channelFuture.channel().closeFuture().sync();
            }
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}