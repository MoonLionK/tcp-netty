package chc.dts.api.service.impl;

import chc.dts.api.dao.ConnectInfoMapper;
import chc.dts.api.entity.ConnectInfo;
import chc.dts.api.service.IConnectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 连接信息表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Service
public class ConnectInfoServiceImpl extends ServiceImpl<ConnectInfoMapper, ConnectInfo> implements IConnectInfoService {

}
