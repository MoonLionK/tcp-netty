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
 * 采集仪配置表)
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("collecting_config")
@Schema(name = "CollectingConfig", description = "采集仪配置表)")
public class CollectingConfig extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "采集仪id")
    @TableField("collecting_id")
    private Long collectingId;

    @Schema(description = "匹配规则")
    @TableField("match_rule")
    private String matchRule;

    @Schema(description = "计算规则")
    @TableField("calculate_rule")
    private String calculateRule;

    @TableField("result_name")
    private String resultName;
}
