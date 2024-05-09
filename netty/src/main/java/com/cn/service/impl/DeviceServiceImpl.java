package com.cn.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.common.core.KeyValue;
import com.cn.dao.DeviceMapper;
import com.cn.entity.Device;
import com.cn.service.IDeviceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 * 设备信息表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-04-29
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Override
    public ArrayList<KeyValue<String, Integer>> selectActiveIpAndPort(String model) {
        return this.getBaseMapper().selectActiveIpAndPortByTcpModel(model);
    }
}

