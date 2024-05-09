package com.cn.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.common.core.KeyValue;
import com.cn.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 * 设备信息表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-04-29
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

    ArrayList<KeyValue<String, Integer>> selectActiveIpAndPortByTcpModel(@Param("model") String model);
}
