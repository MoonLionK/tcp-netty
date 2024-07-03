package chc.dts.api.dao;

import chc.dts.api.controller.vo.DeviceCodeAndNameResq;
import chc.dts.api.entity.Device;
import chc.dts.common.core.KeyValue;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 设备信息表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@Mapper
public interface DeviceMapper extends BaseMapperX<Device> {

    /**
     * 查询DTS服务作为tcpServer启动时需要监听的ip和端口
     *
     * @return ip:port
     */
    ArrayList<KeyValue<String, Integer>> selectActiveIpAndPortByTcpModel(@Param("model") String model);

    /**
     * 根据ip和端口查询设备类型
     *
     * @param ip   ip
     * @param port 端口
     * @return Device
     */
    Device selectDeviceByLocalAddress(@Param("ip") String ip, @Param("port") String port);

    /**
     * 查询下拉列表
     *
     * @param model tcp通信模式
     * @return List<DeviceCodeAndNameResq>
     */
    List<DeviceCodeAndNameResq> selectDropdownList(@Param("model") String model);
}
