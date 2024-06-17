package chc.dts.api.dao;

import chc.dts.api.entity.MatchedData;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 匹配成功数据记录表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Mapper
public interface MatchedDataMapper extends BaseMapperX<MatchedData> {

}
