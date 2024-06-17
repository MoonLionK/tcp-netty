package chc.dts.api.entity;

import chc.dts.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 传感器计算公式表
 * </p>
 *
 * @author xgy
 * @since 2024-06-05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sensor_calculate_config")
@Schema(name = "SensorCalculateConfig", description = "传感器计算公式表")
public class SensorCalculateConfig extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "传感器code")
    @TableField("sensor_code")
    private String sensorCode;

    @Schema(description = "计算规则")
    @TableField("calculate_rule")
    private String calculateRule;

    @Schema(description = "计算方式,0为公式计算，1为直接取值")
    @TableField("calculate_type")
    private Integer calculateType;

    @Schema(description = "常量计算参数")
    @TableField("calculate_param")
    private String calculateParam;

    @Schema(description = "计算结果名称")
    @TableField("result_name")
    private String resultName;

    @Schema(description = "计算时需要关联查询的其他传感器code")
    @TableField("other_sensor_code")
    private String otherSensorCode;

    @Schema(description = "时间范围")
    @TableField("time_range")
    private String timeRange;
}
