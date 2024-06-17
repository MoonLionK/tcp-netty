package chc.dts.data.pojo.dto;

import chc.dts.common.core.KeyValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 匹配后的数据
 *
 * @author xgy
 * @date 2024/5/28 16:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatedDataDto {
    /**
     * 传感器code
     */
    private String sensorCode;
    /**
     * 计算后的数据
     */
    private List<KeyValue<String, Object>> calculateData;
}
