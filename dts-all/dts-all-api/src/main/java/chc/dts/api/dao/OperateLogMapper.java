package chc.dts.api.dao;

import chc.dts.api.entity.OperateLog;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志记录 V2 版本 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Mapper
@DS("system")
public interface OperateLogMapper extends BaseMapperX<OperateLog> {

}
