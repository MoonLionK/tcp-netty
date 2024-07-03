package chc.dts.api.dao;

import chc.dts.api.entity.ConnectInfo;
import chc.dts.common.core.KeyValue;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * <p>
 * 连接信息表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Mapper
public interface ConnectInfoMapper extends BaseMapperX<ConnectInfo> {
    /**
     * 查询DTS服务作为tcpClient启动时需要监听的ip和端口
     *
     * @return ip:port
     */
    ArrayList<KeyValue<String, Integer>> selectActiveIpAndPort();
}
