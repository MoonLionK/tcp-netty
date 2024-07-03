package chc.dts.api.controller.vo;

import lombok.Data;

/**
 * @author xgy
 * @date 2024/6/18 11:06
 */
@Data
public class DeviceCodeAndNameResq {
    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编码
     */
    private String deviceCode;
}
