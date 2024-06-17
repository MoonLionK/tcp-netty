package chc.dts.common.util.object;


import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author xgy
 * @date 2024/5/7 14:02
 */

@AutoConfiguration
public class SpringUtils implements ApplicationContextAware {

    /**
     * -- GETTER --
     * 获取applicationContext的实例applCon
     */
    @Getter
    private static ApplicationContext applicationContext;

    /**
     * 为空时，设置实例
     */
    @Override
    public void setApplicationContext(ApplicationContext applCon) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applCon;
        }
    }

    /**
     * 通过clazz获取Bean（推荐使用这种）
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

}


