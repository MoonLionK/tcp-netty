package chc.dts.api.pojo.vo.data;

import chc.dts.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author xgy
 * @date 2024/06/18
 */
@Schema(description = "管理后台 - 用户查询body")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserReqVo extends PageParam {

    @Schema(description = "用户名")
    private String username;

}
