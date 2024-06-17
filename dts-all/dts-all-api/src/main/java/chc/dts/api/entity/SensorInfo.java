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
 * 传感器信息表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sensor_info")
@Schema(name = "SensorInfo", description = "传感器信息表")
public class SensorInfo extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "设备code")
    @TableField("device_code")
    private String deviceCode;

    @Schema(description = "传感器名称")
    @TableField("sensor_name")
    private String sensorName;

    @Schema(description = "传感器编码")
    @TableField("sensor_code")
    private String sensorCode;
}
