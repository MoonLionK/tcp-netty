package chc.dts.all;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 * @author xgy
 */
@SpringBootApplication(scanBasePackages = {"chc.dts"})
public class DtsAllApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtsAllApplication.class, args);
    }

}
