package chc.dts.receive.netty.client;

import chc.dts.api.dao.ChannelInfoMapper;
import chc.dts.api.dao.ConnectInfoMapper;
import chc.dts.api.entity.ConnectInfo;
import chc.dts.api.pojo.vo.TcpCommonReq;
import chc.dts.api.pojo.vo.TcpInfoResq;
import chc.dts.common.core.KeyValue;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.exception.enums.GlobalErrorCodeConstants;
import chc.dts.common.pojo.CommonResult;
import chc.dts.common.pojo.PageParam;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.object.BeanUtils;
import chc.dts.mybatis.core.query.LambdaQueryWrapperX;
import chc.dts.receive.controller.vo.ClientUpdateReq;
import chc.dts.receive.netty.TcpAbstract;
import chc.dts.receive.netty.TcpInterface;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static chc.dts.common.config.ThreadPoolConfig.COMMON_POOL;

/**
 * TcpNetty客户端代码支持多线程数据处理和多端口监听
 *
 * @author xgy
 * @date 2024/5/6
 */
@Slf4j
@Component
public class TcpNettyClient extends TcpAbstract implements TcpInterface {
    @Resource
    private ConnectInfoMapper connectInfoMapper;
    @Resource(name = COMMON_POOL)
    private ThreadPoolTaskExecutor commonThreadPoolTaskExecutor;

    //创建客户端端的启动对象，配置参数
    Bootstrap bootstrap = new Bootstrap();

    protected TcpNettyClient(ConnectInfoMapper connectInfoMapper, ChannelInfoMapper channelInfoMapper) {
        super(connectInfoMapper, channelInfoMapper);
    }

    @PostConstruct
    public void startNetty() {
        //项目启动时修改所有连接状态为1,因为服务关闭时所有连接会断开
        connectInfoMapper.update(new LambdaUpdateWrapper<ConnectInfo>().set(ConnectInfo::getStatus, 1));
        ArrayList<KeyValue<String, Integer>> keyValues = connectInfoMapper.selectActiveIpAndPort();
        commonThreadPoolTaskExecutor.execute(() -> {
            try {
                this.init(keyValues);
            } catch (Exception e) {
                log.error("TcpNettyClient startClient error", e);
            }
        });
    }

    private void init(ArrayList<KeyValue<String, Integer>> keyValues) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        //设置log监听器，并且日志级别为debug，方便观察运行流程
                        ch.pipeline().addLast("logging", new LoggingHandler("DEBUG"));
                        //编码器。发送消息时候用
                        ch.pipeline().addLast("encode", new StringEncoder());
                        //解码器，接收消息时候用
                        ch.pipeline().addLast("decode", new StringDecoder());
                        //业务处理类，最终的消息会在这个handler中进行业务处理
                        TcpNettyClient tcpNettyClient = new TcpNettyClient(connectInfoMapper, channelInfoMapper);
                        ch.pipeline().addLast("handler", new ClientHandler(tcpNettyClient));
                    }
                });
        List<ChannelFuture> channelFutureList = new ArrayList<>();
        for (KeyValue<String, Integer> keyValue : keyValues) {
            ChannelFuture cf;
            try {
                cf = bootstrap.connect(keyValue.getKey(), keyValue.getValue()).sync();
            } catch (Exception e) {
                log.info("连接不上的ip和端口号:" + keyValue.getKey() + ":" + keyValue.getValue());
                continue;
            }
            log.info("已连接ip和端口号:" + keyValue.getKey() + ":" + keyValue.getValue());
            channelFutureList.add(cf);
        }
        //对关闭通道进行监听
        for (ChannelFuture channelFuture : channelFutureList) {
            channelFuture.channel().closeFuture().sync();
        }

    }

    public CommonResult<String> connect(TcpCommonReq req) {
        ChannelFuture f;
        try {
            f = bootstrap.connect(req.getIp(), req.getPort()).syncUninterruptibly();
        } catch (Exception e) {
            return CommonResult.error(new ErrorCode(500, e.getMessage()));
        }
        if (!f.isSuccess() && null != f.cause()) {
            log.error("The client failed to connect the server:" + req.getIp() + ":" + req.getPort() + ",error message is:" + f.cause().getMessage());
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
        String remoteAddress = f.channel().remoteAddress().toString().replace("/", "");
        String localAddress = f.channel().localAddress().toString().replace("/", "");
        return CommonResult.success(localAddress + "," + remoteAddress);
    }

    @Override
    public ErrorCode sendMessage(TcpCommonReq req) {
        Map<ChannelId, ChannelHandlerContext> clientContextMap = ClientHandler.getCLIENT_CONTEXT_MAP();
        return send(req, clientContextMap);
    }

    @Override
    public ErrorCode closeChannel(List<TcpCommonReq> tcpCommonReqList) {
        return close(tcpCommonReqList);
    }

    @Override
    public List<TcpInfoResq> getChannelInfo() {
        return null;
    }

    /**
     * 查询客户端连接信息
     *
     * @return CommonResult<PageResult < ConnectInfo>>
     */
    public CommonResult<PageResult<ConnectInfo>> info(@Valid PageParam pageParam) {
        PageResult<ConnectInfo> connectInfoPageResult = connectInfoMapper.selectPage(pageParam, new LambdaQueryWrapperX<ConnectInfo>()
                .orderByDesc(ConnectInfo::getUpdateTime));
        return CommonResult.success(connectInfoPageResult);
    }

    public CommonResult<Boolean> save(String ip, Integer port) {
        return CommonResult.success(connectInfoMapper.insert(new ConnectInfo().setIp(ip).setPort(port)) == 1);
    }

    public CommonResult<Boolean> update(ClientUpdateReq req) {
        ConnectInfo connectInfo = BeanUtils.toBean(req, ConnectInfo.class);
        int i = connectInfoMapper.updateById(connectInfo);
        if (i == 0) {
            throw new ServiceException(500, "对应的数据不存在");
        }
        return CommonResult.success(i == 1);
    }

    public CommonResult<Boolean> delete(Integer id) {
        ConnectInfo connectInfo = connectInfoMapper.selectById(id);
        if (Objects.equals(connectInfo.getStatus(), 0)) {
            return CommonResult.error(new ErrorCode(500, "启动状态的服务器监听不允许删除"));
        }
        return CommonResult.success(connectInfoMapper.deleteById(id) == 1);
    }
}
