package chc.dts.api.dao;

import chc.dts.api.entity.SensorMatchConfig;
import chc.dts.api.pojo.dto.SensorMatchDTO;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 传感器匹配配置表 Mapper 接口
 * </p>
 *
 * @author fyh
 * @since 2024-05-29
 */
@Mapper
public interface SensorMatchConfigMapper extends BaseMapperX<SensorMatchConfig> {
    /** 获取传感器对应的匹配规则
     * @param sensorCode 传感器编码
     * @return 匹配规则
     */
    List<SensorMatchDTO> getMatchConfigs(@Param("sensorCode") List<String> sensorCode);
}
