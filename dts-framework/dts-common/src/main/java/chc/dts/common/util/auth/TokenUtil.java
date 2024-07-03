package chc.dts.common.util.auth;

import chc.dts.common.exception.util.ServiceExceptionUtil;
import cn.dev33.satoken.stp.StpUtil;

import static chc.dts.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;

/**
 * @author xgy
 * @date 2024/06/18
 */
public class TokenUtil {

    /**
     * @return 当前登录id
     */
    public static Integer getLoginId() {
        try {
            return (Integer) StpUtil.getLoginId();
        } catch (Exception e) {
            throw ServiceExceptionUtil.exception(UNAUTHORIZED.getCode(), UNAUTHORIZED.getMsg());
        }


    }
}
