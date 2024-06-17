package chc.dts.api.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 传感器匹配DTO
 * </p>
 *
 * @author fyh
 * @since 2024-05-29
 */
@Data
@Accessors(chain = true)
public class SensorMatchDTO {

    @Schema(description = "传感器code")
    private String sensorCode;

    @Schema(description = "匹配规则")
    private String matchRule;

    @Schema(description = "匹配方式,0为key+value，1为key匹配,2为value包含")
    private Integer checkType;

    @Schema(description = "逻辑规则：0:必须满足 1:满足其中一个即可")
    private Integer logicType;
}
