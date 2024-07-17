package chc.dts.receive.netty.server;

import chc.dts.api.common.CodeGenUtil;
import chc.dts.api.dao.ChannelInfoMapper;
import chc.dts.api.dao.ConnectInfoMapper;
import chc.dts.api.entity.ChannelInfo;
import chc.dts.api.pojo.constants.CodeGenEnum;
import chc.dts.api.pojo.vo.TcpCommonReq;
import chc.dts.api.pojo.vo.TcpInfoResq;
import chc.dts.api.service.IDeviceService;
import chc.dts.common.core.KeyValue;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.enums.GlobalErrorCodeConstants;
import chc.dts.common.pojo.CommonResult;
import chc.dts.receive.netty.TcpAbstract;
import chc.dts.receive.netty.TcpInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static chc.dts.common.config.ThreadPoolConfig.COMMON_POOL;
import static chc.dts.common.config.ThreadPoolConfig.NETTY_SERVER_CONNECT_POOL;
import static chc.dts.common.constant.TcpConstant.TCP_SERVER;

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
    @Resource
    private CodeGenUtil codeGenUtil;
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
    List<ChannelFuture> channelFutureList = new ArrayList<>();

    protected TcpNettyServer(ConnectInfoMapper connectInfoMapper, ChannelInfoMapper channelInfoMapper) {
        super(connectInfoMapper, channelInfoMapper);
    }

    /**
     * 根据设备信息获取启动时需要监听的端口信息
     */
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
    public CommonResult<String> addMonitor(TcpCommonReq req) {
        Long count = channelInfoMapper.selectCount(new LambdaQueryWrapper<ChannelInfo>()
                .eq(ChannelInfo::getIp, req.getIp())
                .eq(ChannelInfo::getPort, req.getPort()));
        if (count > 0) {
            return CommonResult.error(500, "此地址监听已存在");
        }
        if (Lists.newArrayList(8080, 8081, 8082, 8083, 8084, 3306, 22, 2375, 80, 6379).contains(req.getPort())) {
            return CommonResult.error(500, "不能新增已占用端口的监听");
        }

        ChannelInfo channelInfo = new ChannelInfo()
                .setIp(req.getIp())
                .setPort(req.getPort())
                .setChannelCode(codeGenUtil.getCode(CodeGenEnum.CHANNEL));
        channelInfoMapper.insert(channelInfo);
        return CommonResult.success();
    }

    public CommonResult<String> openMonitor(TcpCommonReq req) {
        CompletableFuture<CommonResult<String>> future = CompletableFuture.supplyAsync(() -> {
            try {
                ChannelFuture cf = bootstrap.bind(req.getIp(), req.getPort()).sync();
                channelFutureList.add(cf);
                channelInfoMapper.update(new LambdaUpdateWrapper<ChannelInfo>()
                        .eq(ChannelInfo::getIp, req.getIp())
                        .eq(ChannelInfo::getPort, req.getPort())
                        .set(ChannelInfo::getStatus, 0));
                log.info("新增监听ip和端口号:" + req.getIp() + ":" + req.getPort());
                cf.channel().closeFuture().sync();
                return CommonResult.success();
            } catch (Exception e) {
                log.error("Netty connect error", e);
                return CommonResult.error(500, e.getMessage());
            }
        }, threadPoolTaskExecutor);

        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            return CommonResult.success();
        } catch (ExecutionException | InterruptedException e) {
            log.error("Netty connect error", e);
            return CommonResult.error(500, e.getMessage());
        }
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

    @Override
    public List<TcpInfoResq> getChannelInfo() {
        List<ChannelInfo> channelInfos = channelInfoMapper.selectList();
        // 查询初始化信息
        List<TcpInfoResq> tcpInfoResqs = channelInfos.stream()
                .map(info -> new TcpInfoResq(info.getIp(), info.getPort(), info.getStatus(), null))
                .collect(Collectors.toList());
        for (TcpInfoResq tcpInfoResq : tcpInfoResqs) {
            List<TcpInfoResq.RemoteInfo> list = new ArrayList<>();
            DEVICE_MAP.forEach((key, value) -> {
                String[] split1 = key.split(":");
                String ip = split1[0];
                Integer port = Integer.valueOf(split1[1]);
                if (tcpInfoResq.getPort().equals(port) && (tcpInfoResq.getIp().equals(ip) || "0.0.0.0".equals(tcpInfoResq.getIp()))) {
                    for (KeyValue<String, ChannelId> keyValue : value) {
                        String[] split = keyValue.getKey().split(":");
                        list.add(new TcpInfoResq.RemoteInfo(split[0], split[1]));
                    }
                }
            });
            tcpInfoResq.setRemoteInfoList(list);
        }
        return tcpInfoResqs;
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

                            //业务处理类，最终的消息因为多包情况，放置在拆包方法当中
                            ch.pipeline().addLast("unpack", new MyProtocolDecoder());

                            //加入一个netty 提供 IdleStateHandler
                            /*
                            说明
                            1. IdleStateHandler 是netty 提供的处理空闲状态的处理器
                            2. long readerIdleTime : 表示多长时间没有读, 就会发送一个心跳检测包检测是否连接
                            3. long writerIdleTime : 表示多长时间没有写, 就会发送一个心跳检测包检测是否连接
                            4. long allIdleTime : 表示多长时间没有读写, 就会发送一个心跳检测包检测是否连接
                            5. 文档说明
                            triggers an {@link IdleStateEvent} when a {@link Channel} has not performed read, write, or both operation for a while.
                            6. 当 IdleStateEvent 触发后 , 就会传递给管道 的下一个handler去处理通过调用(触发)下一个handler 的 userEventTiggered , 在该方法中去处理 IdleStateEvent(读空闲，写空闲，读写空闲)
                             */
                            ch.pipeline().addLast(new IdleStateHandler(7000, 7000, 300, TimeUnit.SECONDS));
                            //编码器。发送消息时候用
                            ch.pipeline().addLast("decode", new ServerHandler(new TcpNettyServer(connectInfoMapper, channelInfoMapper)));
                        }
                    });
            //启动服务器(并绑定端口),此处必须使用2个循环若改变顺序就无法监听多个端口
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


    public ErrorCode closeMonitor(TcpCommonReq tcpCommonReq) {
        List<ChannelFuture> removeList = new ArrayList<>();
        DEVICE_MAP.remove(tcpCommonReq.getIp() + ":" + tcpCommonReq.getPort());
        for (ChannelFuture channelFuture : channelFutureList) {
            Channel channel = channelFuture.channel();
            String localAddress = channel.localAddress().toString().replace("/", "");
            String ip = tcpCommonReq.getIp();
            Integer port = tcpCommonReq.getPort();
            if (localAddress.equals(ip + ":" + port)) {
                // 关闭 Channel
                try {
                    channel.close().sync();
                    removeList.add(channelFuture);
                    channelFutureList.removeAll(removeList);
                    channelInfoMapper.update(new LambdaUpdateWrapper<ChannelInfo>()
                            .eq(ChannelInfo::getIp, ip)
                            .eq(ChannelInfo::getPort, port)
                            .set(ChannelInfo::getStatus, 1));
                    return GlobalErrorCodeConstants.SUCCESS;
                } catch (InterruptedException e) {
                    log.error("监听关闭异常", e);
                    return new ErrorCode(500, e.getMessage());
                }
            }
        }
        return new ErrorCode(500, "不存在对应的通道信息");
    }

    public ErrorCode deleteMonitor(TcpCommonReq req) {
        LambdaQueryWrapper<ChannelInfo> wrapper = new LambdaQueryWrapper<ChannelInfo>()
                .eq(ChannelInfo::getIp, req.getIp())
                .eq(ChannelInfo::getPort, req.getPort());
        ChannelInfo channelInfo = channelInfoMapper.selectOne(wrapper);
        if (ObjectUtils.isEmpty(channelInfo)) {
            return new ErrorCode(500, "找不到对应的通道信息");
        }
        if (Objects.equals(channelInfo.getStatus(), 0)) {
            return new ErrorCode(500, "启动状态的服务器监听不允许删除");
        }
        channelInfoMapper.delete(wrapper);
        return GlobalErrorCodeConstants.SUCCESS;
    }
}