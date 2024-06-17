package chc.dts.api.dao;

import chc.dts.api.entity.SensorCalculateConfig;
import chc.dts.api.pojo.dto.SensorCalculateDTO;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 传感器计算公式表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-06-05
 */
@Mapper
public interface SensorCalculateConfigMapper extends BaseMapperX<SensorCalculateConfig> {

    /**
     * 获取传感器对应的计算公式
     *
     * @param sensorCode 传感器编码
     * @return 计算公式
     */
    List<SensorCalculateDTO> getCalculateConfigs(@Param("sensorCode") List<String> sensorCode);
}
