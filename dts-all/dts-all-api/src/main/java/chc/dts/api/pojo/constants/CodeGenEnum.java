package chc.dts.api.pojo.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 编码生成枚举类
 *
 * @author xgy
 */
@AllArgsConstructor
@Getter
public enum CodeGenEnum {

    /**
     * 项目信息表
     */
    PROJECT("PC", "project_info"),
    /**
     * 协议信息表
     */
    PROTOCOL("PT", "protocol"),
    /**
     * 设备信息表
     */
    DEVICE("DC", "device"),
    /**
     * 通道信息表
     */
    CHANNEL("CC", "channel_info"),
    /**
     * 计算公式表
     */
    CALCULATE("CA", "calculate_config");

    /**
     * 编码起始符
     */
    private final String str;
    /**
     * 表名
     */
    private final String tableName;

}
