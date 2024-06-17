package chc.dts.data.pojo.dto;

import chc.dts.common.core.KeyValue;
import lombok.Data;

import java.util.List;

/**
 * 匹配后的数据
 *
 * @author xgy
 * @date 2024/5/28 16:35
 */
@Data
public class MatchedDataDto {
    /**
     * 传感器id
     */
    private String sensorCode;
    /**
     * 匹配后的数据
     */
    private List<KeyValue<String, String>> matchData;
}
