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

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_user")
@Schema(name = "User", description = "用户表")
public class User extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "昵称")
    @TableField("nickname")
    private String nickname;

    @Schema(description = "用户名")
    @TableField("username")
    private String username;

    @Schema(description = "密码")
    @TableField("password")
    private String password;

    @Schema(description = "机构")
    @TableField("org_id")
    private Integer orgId;

    @Schema(description = "机构全量id")
    @TableField("org_full_id")
    private String orgFullId;

    @Schema(description = "性别")
    @TableField("sex")
    private String sex;

    @Schema(description = "手机")
    @TableField("mobile_phone")
    private String mobilePhone;

    @Schema(description = "区号")
    @TableField("code")
    private String code;

    @Schema(description = "电话")
    @TableField("telephone")
    private String telephone;

    @Schema(description = "邮箱")
    @TableField("email")
    private String email;

    @Schema(description = "部门")
    @TableField("department")
    private String department;

    @Schema(description = "职务")
    @TableField("job")
    private String job;

    @Schema(description = "数据状态（是否禁用，0禁用，1未禁用）")
    @TableField("status")
    private Short status;

    @Schema(description = "是否开启声音 1：是 0：否 ")
    @TableField("voice_status")
    private Integer voiceStatus;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
