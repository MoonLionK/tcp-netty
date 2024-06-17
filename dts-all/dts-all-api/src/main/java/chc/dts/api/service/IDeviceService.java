package chc.dts.api.service;

import chc.dts.api.entity.Device;
import chc.dts.common.core.KeyValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;

/**
 * <p>
 * 设备信息表 服务类
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
public interface IDeviceService extends IService<Device> {

    /**
     * 查询DTS服务作为tcpServer启动时需要监听的ip和端口
     *
     * @return ip:port
     */
    ArrayList<KeyValue<String, Integer>> selectActiveIpAndPort(String model);

    /**
     * 根据ip和端口查询设备类型
     *
     * @param localAddress 本队ip+端口
     * @return DeviceType
     */
    Device selectDeviceTypeByLocalAddress(String localAddress);
}
