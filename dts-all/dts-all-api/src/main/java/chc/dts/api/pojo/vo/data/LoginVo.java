package chc.dts.api.pojo.vo.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xgy
 * @date 2024/07/15
 */
@Schema(description = "登录信息")
@Data
public class LoginVo {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;
}
