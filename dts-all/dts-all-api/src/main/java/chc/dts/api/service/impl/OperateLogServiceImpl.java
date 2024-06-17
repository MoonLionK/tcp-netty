package chc.dts.api.service.impl;


import chc.dts.api.dao.OperateLogMapper;
import chc.dts.api.entity.OperateLog;
import chc.dts.api.service.IOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 V2 版本 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements IOperateLogService {

}
