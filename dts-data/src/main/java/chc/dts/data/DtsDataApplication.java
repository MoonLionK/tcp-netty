package chc.dts.data;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目的启动类
 * @author xgy
 */
@SpringBootApplication(scanBasePackages = {"chc.dts"})
public class DtsDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtsDataApplication.class, args);
    }

}
