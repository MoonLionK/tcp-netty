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
 * 转发配置表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("transform_config")
@Schema(name = "TransformConfig", description = "转发配置表")
public class TransformConfig extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "传感器id")
    @TableField("sensor_id")
    private Long sensorId;

    @Schema(description = "ip地址")
    @TableField("ip")
    private String ip;

    @Schema(description = "连接端口")
    @TableField("port")
    private Integer port;

    @Schema(description = "平台名称")
    @TableField("platform_name")
    private String platformName;

    @Schema(description = "转发规则")
    @TableField("transform_rule")
    private String transformRule;

    @Schema(description = "状态： 0:启用 1:禁用")
    @TableField("status")
    private Integer status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
