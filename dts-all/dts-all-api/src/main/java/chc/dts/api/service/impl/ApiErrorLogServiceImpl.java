package chc.dts.api.service.impl;

import chc.dts.api.dao.ApiErrorLogMapper;
import chc.dts.api.entity.ApiErrorLog;
import chc.dts.api.pojo.dto.ApiErrorLogCreateReqDTO;
import chc.dts.api.service.IApiErrorLogService;
import chc.dts.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 系统异常日志 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Service
public class ApiErrorLogServiceImpl extends ServiceImpl<ApiErrorLogMapper, ApiErrorLog> implements IApiErrorLogService {
    @Resource
    private ApiErrorLogMapper apiErrorLogMapper;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        ApiErrorLog apiErrorLog = BeanUtils.toBean(createDTO, ApiErrorLog.class)
                .setProcessStatus(0);
        apiErrorLogMapper.insert(apiErrorLog);
    }
}
