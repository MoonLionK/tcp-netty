package chc.dts.api.service.impl;

import chc.dts.api.dao.SensorInfoMapper;
import chc.dts.api.entity.SensorInfo;
import chc.dts.api.service.ISensorInfoService;
import chc.dts.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 传感器信息表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@Service
public class SensorInfoServiceImpl extends ServiceImpl<SensorInfoMapper, SensorInfo> implements ISensorInfoService {

    @Override
    public List<SensorInfo> getListByDeviceId(String deviceCode){
        LambdaQueryWrapperX<SensorInfo> queryWrapper=new LambdaQueryWrapperX<>();
        queryWrapper.eq(SensorInfo::getDeviceCode,deviceCode).eq(SensorInfo::getDeleted,false);
        return list(queryWrapper);
    }

}
