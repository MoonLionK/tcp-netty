package chc.dts.data.hander;

import chc.dts.common.constant.TcpConstant;
import chc.dts.common.pojo.RedisMessage;
import chc.dts.data.manage.DataManage;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import static chc.dts.common.config.ThreadPoolConfig.REDIS_CONSUMER_POOL;

/**
 * redis消费端
 *
 * @author xgy
 * @date 2024/5/13 15:00
 */
@Component
@Slf4j
public class RedisConsumer {

    @Resource
    private RedissonClient redisson;
    @Resource
    private DataManage manage;
    @Resource(name = REDIS_CONSUMER_POOL)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 开启监听
     */
    @PostConstruct
    void openReceiving() {
        RTopic topic = redisson.getTopic(TcpConstant.TCP_MESSAGE, new SerializationCodec());
        topic.addListener(String.class, (charSequence, msgStr) -> {
            if (StringUtils.isNotEmpty(msgStr) && JSONUtil.isTypeJSON(msgStr)) {
                RedisMessage message = JSON.parseObject(msgStr, RedisMessage.class);
                log.info("message:{}", message);
                //处理已组包拆包处理编码后的数据,解析->计算->转发
                threadPoolTaskExecutor.execute(() -> {
                    try {
                        manage.dealData(message);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
}

