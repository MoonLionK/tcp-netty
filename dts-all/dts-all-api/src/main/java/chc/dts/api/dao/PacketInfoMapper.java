package chc.dts.api.dao;

import chc.dts.api.entity.PacketInfo;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-05-14
 */
@Mapper
public interface PacketInfoMapper extends BaseMapperX<PacketInfo> {
    /** 根据
     * @param localAddress 本地地址
     * @return 设备对应的拆包方法
     */
    PacketInfo getByLocalAddress(@Param("localAddress") String localAddress);
}
