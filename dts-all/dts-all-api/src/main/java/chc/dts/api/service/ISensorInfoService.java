package chc.dts.api.service;

import chc.dts.api.entity.SensorInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 传感器信息表 服务类
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
public interface ISensorInfoService extends IService<SensorInfo> {
    /**
     * 根据设备id获取传感器列表
     * @param deviceId 设备id
     * @return 设备下的传感器列表
     */
    public List<SensorInfo> getListByDeviceId(String deviceCode);

}
