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
 * 协议信息表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("protocol")
@Schema(name = "Protocol", description = "协议信息表")
public class Protocol extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "应用ID")
    @TableField("app_id")
    private String appId;

    @Schema(description = "协议名称")
    @TableField("protocol_name")
    private String protocolName;

    @Schema(description = "协议编码")
    @TableField("protocol_code")
    private String protocolCode;

    @Schema(description = "通讯协议类型 ：mqtt ||tcp || http ||串口(serial)")
    @TableField("protocol_type")
    private String protocolType;

    @Schema(description = "产品协议详细类型 ：水文协议 ||北京地灾协议")
    @TableField("protocol_detail_type")
    private String protocolDetailType;

    @Schema(description = "状态(字典值：0启用  1停用)")
    @TableField("status")
    private Integer status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
