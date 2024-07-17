package chc.dts.api.pojo.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xgy
 * @date 2024/06/18
 */
@Schema(description = "管理后台 - 用户新增或者更新呢body")
@Data
public class UserAddOrUpdateVO  {

    @Schema(description = "主键id")
    private Integer id;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "用户名")
    private String username;


    @Schema(description = "机构")
    private Integer orgId;

    @Schema(description = "机构全量id")
    private String orgFullId;

    @Schema(description = "性别")
    private String sex;

    @Schema(description = "手机")
    private String mobilePhone;

    @Schema(description = "区号")
    private String code;

    @Schema(description = "电话")
    private String telephone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "部门")
    private String department;

    @Schema(description = "职务")
    private String job;

    @Schema(description = "数据状态（是否禁用，0禁用，1未禁用）")
    private Short status;

    @Schema(description = "是否开启声音 1：是 0：否 ")
    private Integer voiceStatus;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "密码")
    private String password;


}
