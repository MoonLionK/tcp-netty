package chc.dts.common.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zygong
 * @date 2022/3/23 9:19
 */
@Data
public class UserVo {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Integer id;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 机构ID
     */
    @ApiModelProperty("机构ID")
    private Integer orgId;

    /**
     * 机构全量id
     */
    @ApiModelProperty("机构全量id")
    private String orgFullId;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String orgName;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String sex;

    /**
     * 手机
     */
    @ApiModelProperty("手机")
    //@Pattern(regexp = "^1[0-9]{10}",message = "手机号格式不正确")
    private String mobilephone;

    @ApiModelProperty("区号")
    private String code;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    //@Pattern(regexp = "^$|^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",message = "邮箱格式不正确")
    private String email;

    @ApiModelProperty("部门")
    private String department;

    @ApiModelProperty("职务")
    private String job;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 数据状态（是否禁用，0禁用，1未禁用）
     */
    @ApiModelProperty("数据状态（是否禁用，0禁用，1未禁用）")
    private Short status;

    /**
     * 是否开启声音 1：是 0：否 默认1
     */
    private Integer voiceStatus;

    @ApiModelProperty("角色id集合")
    private String roleIds;

    /**
     * 设备类型：PC，APP
     */
    private String deviceType;

    @ApiModelProperty("角色名称集合")
    private String roleNames;
}
