package chc.dts.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义线程池
 *
 * @author xgy
 */
@Configuration(proxyBeanMethods = false)
public class ThreadPoolConfig {

    public static final String NETTY_SERVER_CONNECT_POOL = "NETTY_SERVER_CONNECT_POOL";
    public static final String REDIS_CONSUMER_POOL = "REDIS_CONSUMER_POOL";
    public static final String COMMON_POOL = "COMMON_POOL";
    public static final String PACKET_PROCESSING_POOL = "PACKET_PROCESSING_POOL";

    /**
     * 通用线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean(COMMON_POOL)
    public ThreadPoolTaskExecutor commonThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setKeepAliveSeconds(60);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("dts-common-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 进行加载
        executor.initialize();
        return executor;
    }

    /**
     * TcpNettyServer监听端口专用线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean(NETTY_SERVER_CONNECT_POOL)
    public ThreadPoolTaskExecutor serverThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        executor.setKeepAliveSeconds(60);
        executor.setQueueCapacity(300);
        executor.setThreadNamePrefix("server-connect-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 进行加载
        executor.initialize();
        return executor;
    }
    /**
     * TcpNettyServer监听端口专用线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean(REDIS_CONSUMER_POOL)
    public ThreadPoolTaskExecutor redisPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        executor.setKeepAliveSeconds(60);
        executor.setQueueCapacity(300);
        executor.setThreadNamePrefix("redis-consumer-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 进行加载
        executor.initialize();
        return executor;
    }

    /**
     * 数据解析专用线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean(PACKET_PROCESSING_POOL)
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("packet-task-");
        executor.initialize();
        return executor;
    }

}
