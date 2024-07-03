package chc.dts.api.service.impl;

import chc.dts.api.controller.vo.DeviceCodeAndNameResq;
import chc.dts.api.dao.DeviceMapper;
import chc.dts.api.entity.Device;
import chc.dts.api.service.IDeviceService;
import chc.dts.common.core.KeyValue;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 设备信息表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Override
    public ArrayList<KeyValue<String, Integer>> selectActiveIpAndPort(String model) {
        return this.getBaseMapper().selectActiveIpAndPortByTcpModel(model);
    }

    @Override
    public Device selectDeviceTypeByLocalAddress(String localAddress) {
        String[] split = localAddress.split(":");
        return this.getBaseMapper().selectDeviceByLocalAddress(split[0], split[1]);
    }

    @Override
    public List<DeviceCodeAndNameResq> dropdownList(String model) {
        return this.getBaseMapper().selectDropdownList(model);

    }
}

