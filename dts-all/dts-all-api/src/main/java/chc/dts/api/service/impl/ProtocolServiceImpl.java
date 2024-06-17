package chc.dts.api.service.impl;

import chc.dts.api.dao.ProtocolMapper;
import chc.dts.api.entity.Protocol;
import chc.dts.api.service.IProtocolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 协议信息表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@Service
public class ProtocolServiceImpl extends ServiceImpl<ProtocolMapper, Protocol> implements IProtocolService {

}
