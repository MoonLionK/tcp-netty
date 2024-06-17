package chc.dts.all.dao;

import chc.dts.all.entity.Authorization;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统注册授权表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Mapper
@DS("system")
public interface AuthorizationMapper extends BaseMapperX<Authorization> {

}
