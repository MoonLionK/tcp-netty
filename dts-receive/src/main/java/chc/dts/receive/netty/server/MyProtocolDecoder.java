package chc.dts.receive.netty.server;

import chc.dts.api.dao.PacketInfoMapper;
import chc.dts.api.entity.PacketInfo;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.pojo.RedisMessage;
import chc.dts.common.util.byteUtil.ByteArrayUtils;
import chc.dts.common.util.object.SpringUtils;
import chc.dts.receive.common.LengthGetMethod;
import chc.dts.receive.entity.UnpackResult;
import chc.dts.receive.netty.MessageLog;
import chc.dts.receive.netty.RedisPublish;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.apache.commons.lang3.ObjectUtils;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import static chc.dts.common.util.byteUtil.ByteArrayUtils.subArray;

/**
 * @author xgy
 * @date 2024/05/06
 */

public class MyProtocolDecoder extends LengthFieldBasedFrameDecoder {

    private final PacketInfoMapper packetInfoMapper = SpringUtils.getBean(PacketInfoMapper.class);

    /**
     * 初始化方法，参数从配置当中获取
     */
    public MyProtocolDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) {
        //获取报文头和拆包方法
        SocketAddress socketAddress = ctx.channel().localAddress();
        PacketInfo packetInfo = packetInfoMapper.getByLocalAddress(socketAddress.toString());
        if (ObjectUtils.isEmpty(packetInfo)) {
            throw new ServiceException(500, "Can not find PacketInfo:" + socketAddress);
        }
        packetInfo.setHead(ByteArrayUtils.removeZero(packetInfo.getHead()));


        //获取缓存区的byte数组copy
        if (in.readableBytes() <= 0) {
            return null;
        }

        // 循环拆包
        //1.获取缓存区当中所有数据
        //2.判断开始位置和长度
        //3.抽帧
        //4.移动buffer当中的索引
        byte[] byteArrayFromByteBuf = ByteArrayUtils.getByteArrayFromByteBuf(in);
        UnpackResult listIndex;
        int index = 0;
        do {
            listIndex = processMessage(byteArrayFromByteBuf, packetInfo);
            if (listIndex.isLegal()) {


                int casePackLength = listIndex.getIndex() + listIndex.getLength();

                ByteBuf frame = extractFrame(ctx, in, listIndex.getIndex(), listIndex.getLength());
                index += casePackLength;
                in.readerIndex(index);
                byteArrayFromByteBuf = ByteArrayUtils.removeElements(byteArrayFromByteBuf, casePackLength);
                channelRead0(ctx, frame);
            }
        } while (listIndex.isLegal());

        return null;

    }

    /**
     * 读取客户端传过来的数据
     * 发送到目标，比如redis，websocket等
     */
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf buffer) {
        String remoteAddress = ctx.channel().remoteAddress().toString().replace("/", "");
        String localAddress = ctx.channel().localAddress().toString().replace("/", "");
        String string = ByteBufUtil.hexDump(buffer).toUpperCase();
        MessageLog messageLog = SpringUtils.getBean(MessageLog.class);
        messageLog.messageInfo(localAddress, remoteAddress, string);

        // 构造要发送的消息
        String message = "Hello, client!";

        // 向客户端发送消息
        ctx.channel().writeAndFlush(message);

        RedisPublish redisPublish = SpringUtils.getBean(RedisPublish.class);
        // 创建服务端
        redisPublish.sendNotice(new RedisMessage(localAddress, remoteAddress, string));
    }


    /**
     * @param in             缓冲区数组
     * @param unpackProtocol 拆包方法
     * @return 拆包结果
     */
    private UnpackResult processMessage(byte[] in, PacketInfo unpackProtocol) {

        UnpackResult result = null;
        switch (unpackProtocol.getType()) {
            case 1:
                result = unpackFixed(in, unpackProtocol);
                break;
            case 2:
                result = unpackFixed2(in, unpackProtocol);
                break;
            case 3:
                result = unpackFixed3(in, unpackProtocol);
                break;
            default:
        }
        return result;
    }


    /**
     * @param in         可读的报文
     * @param packetInfo 拆包方法
     * @return 长度类型拆包结果
     */
    private UnpackResult unpackFixed(byte[] in, PacketInfo packetInfo) {
        UnpackResult result = new UnpackResult();
        byte[] head = packetInfo.getHead();

        int startIndex = ByteArrayUtils.findStartIndex(in, head) - packetInfo.getOffset();
        if (startIndex < 0) {
            return result;
        }
        int length = packetInfo.getLength();
        if (startIndex + length > in.length) {
            return null;
        }

        //组装结果
        result.setIndex(startIndex);
        result.setLength(length);
        return result;
    }

    /**
     * @param in         可读的报文
     * @param packetInfo 拆包方法
     * @return 特殊字符结束拆包结果
     */
    private UnpackResult unpackFixed2(byte[] in, PacketInfo packetInfo) {
        UnpackResult result = new UnpackResult();
        byte[] head = packetInfo.getHead();

        //获取开始和结尾index
        int startIndex = ByteArrayUtils.findStartIndex(in, head) - packetInfo.getOffset();
        List<byte[]> bytes = new ArrayList<>();
        bytes.add(ByteArrayUtils.removeZero(packetInfo.getTailFirst()));
        bytes.add(ByteArrayUtils.removeZero(packetInfo.getTailSecond()));
        bytes.add(ByteArrayUtils.removeZero(packetInfo.getTailThird()));
        int endIndex = ByteArrayUtils.findEndIndex(in, bytes);
        if (startIndex < 0 || endIndex < 0) {
            return result;
        }
        int length = endIndex - startIndex + 1;

        //组装结果
        result.setIndex(startIndex);
        result.setLength(length);
        return result;
    }

    /**
     * @param in         可读的报文
     * @param packetInfo 拆包方法
     * @return List 0为报文开始地址  1为报文长度
     */
    private UnpackResult unpackFixed3(byte[] in, PacketInfo packetInfo) {
        UnpackResult result = new UnpackResult();

        //获取包开始位置
        byte[] head = packetInfo.getHead();
        int startIndex = ByteArrayUtils.findStartIndex(in, head) - packetInfo.getOffset();
        if (startIndex < 0) {
            return result;
        }

        //根据长度位获取长度结果
        byte[] toBeLength = subArray(in, packetInfo.getLengthOffset() + startIndex, packetInfo.getLengthOffset() + startIndex + packetInfo.getHeadPacketLength() - 1);
        int length = LengthGetMethod.getLength(packetInfo.getMethodType(), toBeLength);
        if (length < 0) {
            return result;
        }
        length += packetInfo.getAddLength();

        //组装结果
        result.setIndex(startIndex);
        result.setLength(length);
        return result;
    }

}
