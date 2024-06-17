package chc.dts.api.dao;


import chc.dts.api.entity.ApiErrorLog;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统异常日志 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Mapper
@DS("system")
public interface ApiErrorLogMapper extends BaseMapperX<ApiErrorLog> {

}
