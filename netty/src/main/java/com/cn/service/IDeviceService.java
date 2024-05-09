package com.cn.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.common.core.KeyValue;
import com.cn.entity.Device;

import java.util.ArrayList;

/**
 * <p>
 * 设备信息表 服务类
 * </p>
 *
 * @author xgy
 * @since 2024-04-29
 */
public interface IDeviceService extends IService<Device> {

    /**
     * 查询DTS服务作为tcpServer启动时需要监听的ip和端口
     *
     * @return ip:port
     */
    ArrayList<KeyValue<String, Integer>> selectActiveIpAndPort(String model);
}
