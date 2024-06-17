package chc.dts.api.dao;

import chc.dts.api.entity.Protocol;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 协议信息表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@Mapper
public interface ProtocolMapper extends BaseMapperX<Protocol> {

}
