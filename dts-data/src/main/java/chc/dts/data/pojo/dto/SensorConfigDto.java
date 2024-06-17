package chc.dts.data.pojo.dto;

import chc.dts.api.pojo.dto.SensorCalculateDTO;
import chc.dts.api.pojo.dto.SensorMatchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author xgy
 * @date 2024/05/29
 */
@AllArgsConstructor
@Data
public class SensorConfigDto {
    /**
     * 传感器编码
     */
    private String sensorCode;

    /**
     * 计算公式
     */
    private List<SensorCalculateDTO> calculateConfigs;

    /**
     * 匹配规则
     */
    private List<SensorMatchDTO> sensorMatchConfigs;


}
