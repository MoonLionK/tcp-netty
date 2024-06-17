package chc.dts.all.entity;

import chc.dts.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * <p>
 * 系统注册授权表
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_authorization")
@Schema(name = "Authorization", description = "系统注册授权表")
public class Authorization extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "注册模式 1:机器码")
    @TableField("type")
    private Short type;

    @Schema(description = "申请码")
    @TableField("app_code")
    private String appCode;

    @Schema(description = "注册码")
    @TableField("reg_code")
    private String regCode;

    @Schema(description = "设备数")
    @TableField("device_num")
    private Integer deviceNum;

    @Schema(description = "有效期")
    @TableField("validity_time")
    private LocalDate validityTime;

    @Schema(description = "功能")
    @TableField("function")
    private String function;

    @Schema(description = "状态(0未注册,1:注册成功)")
    @TableField("status")
    private Short status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
