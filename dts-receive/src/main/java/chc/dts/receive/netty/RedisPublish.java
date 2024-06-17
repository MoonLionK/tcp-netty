package chc.dts.receive.netty;

import chc.dts.common.constant.TcpConstant;
import chc.dts.common.pojo.RedisMessage;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * redis 消息发布
 *
 * @author xgy
 * @date 2024/5/13 15:45
 */
@Component
@Slf4j
public class RedisPublish {
    @Resource
    private RedissonClient redisson;
    private RTopic topic;

    /**
     * 开启监听
     */
    @PostConstruct
    void openReceiving() {
        topic = redisson.getTopic(TcpConstant.TCP_MESSAGE, new SerializationCodec());
    }

    /**
     * 业务需要的地方可以直接待用
     **/
    public void sendNotice(RedisMessage message) {
        //redis 发广播
        try {
            String alarmStr = JSONUtil.toJsonStr(message);
            topic.publish(alarmStr);
        } catch (Exception e) {
            log.error("sendNotice失败：", e);
        }
    }

}

