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
 * 传感器匹配配置表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sensor_match_config")
@Schema(name = "SensorMatchConfig", description = "传感器匹配配置表")
public class SensorMatchConfig extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "传感器code")
    @TableField("sensor_code")
    private String sensorCode;

    @Schema(description = "匹配规则")
    @TableField("match_rule")
    private String matchRule;

    @Schema(description = "匹配方式,0为key+value，1为key匹配,2为value包含")
    @TableField("check_type")
    private Integer checkType;

    @Schema(description = "逻辑规则：0:必须满足 1:满足其中一个即可")
    @TableField("logic_type")
    private Integer logicType;
}
