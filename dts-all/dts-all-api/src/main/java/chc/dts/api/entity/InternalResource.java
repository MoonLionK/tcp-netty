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
 * 国际化资源表
 * </p>
 *
 * @author fyh
 * @since 2024-06-13
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("internal_resource")
@Schema(name = "InternalResource", description = "国际化资源表")
public class InternalResource extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "资源key值")
    @TableField("`key`")
    private String key;

    @Schema(description = "语言标识")
    @TableField("lang")
    private String lang;

    @Schema(description = "显示内容")
    @TableField("text")
    private String text;

    @Schema(description = "资源类型：1-前端；2-后端")
    @TableField("type")
    private Integer type;
}
