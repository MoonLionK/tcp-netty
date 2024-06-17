package chc.dts.api.pojo.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 传感器计算公式DTO
 * </p>
 *
 * @author fyh
 * @since 2024-05-29
 */
@Data
@Accessors(chain = true)
public class SensorCalculateDTO {

    @Schema(description = "传感器code")
    private String sensorCode;

    @Schema(description = "计算规则code")
    private String calculateCode;

    @Schema(description = "计算规则")
    private String calculateRule;

    @Schema(description = "计算方式,0为公式计算，1为直接取值")
    private Integer calculateType;

    @Schema(description = "常量计算参数")
    private JSONObject calculateParam;

    @Schema(description = "计算结果名称")
    private String resultName;

    @Schema(description = "计算时需要关联查询的其他传感器code")
    private String otherSensorCode;

    @Schema(description = "时间范围")
    private String timeRange;
}
