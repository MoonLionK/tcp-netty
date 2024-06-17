package chc.dts.data.pojo.dto;

import chc.dts.common.core.KeyValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 解析后的数据
 *
 * @author xgy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParsedData {
    /**
     * 解析后的数据
     */
    public List<KeyValue<String, String>> parseData;
    /**
     * 设备id
     */
    private String deviceCode;
}


