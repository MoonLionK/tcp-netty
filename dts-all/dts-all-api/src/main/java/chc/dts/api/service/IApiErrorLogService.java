package chc.dts.api.service;

import chc.dts.api.entity.ApiErrorLog;
import chc.dts.api.pojo.dto.ApiErrorLogCreateReqDTO;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 系统异常日志 服务类
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
public interface IApiErrorLogService extends IService<ApiErrorLog> {

    void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO);
}
