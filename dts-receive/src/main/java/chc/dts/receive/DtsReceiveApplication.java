package chc.dts.receive;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 项目的启动类
 * @author xgy
 */
@SpringBootApplication(scanBasePackages = {"chc.dts"})
@EnableAsync
public class DtsReceiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtsReceiveApplication.class, args);
    }

}
