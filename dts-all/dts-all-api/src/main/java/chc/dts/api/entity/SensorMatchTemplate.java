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
 * 匹配规则模版表
 * </p>
 *
 * @author xgy
 * @since 2024-06-05
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sensor_match_template")
@Schema(name = "SensorMatchTemplate", description = "匹配规则模版表")
public class SensorMatchTemplate extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "协议分组名称")
    @TableField("protocol_group_name")
    private String protocolGroupName;

    @Schema(description = "设备分组名称")
    @TableField("device_group_name")
    private String deviceGroupName;

    @Schema(description = "传感器分组名称")
    @TableField("sensor_group_name")
    private String sensorGroupName;

    @Schema(description = "匹配的key值")
    @TableField("key")
    private String key;

    @Schema(description = "匹配的value值")
    @TableField("value")
    private String value;

    @Schema(description = "匹配方式,0为key+value，1为key匹配,2为value包含")
    @TableField("check_type")
    private Integer checkType;

    @Schema(description = "逻辑规则：0:必须满足 1:满足其中一个即可")
    @TableField("logic_type")
    private Integer logicType;

    @Schema(description = "值校验的正则表达式")
    @TableField("regular")
    private String regular;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
