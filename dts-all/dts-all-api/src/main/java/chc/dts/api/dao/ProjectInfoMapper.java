package chc.dts.api.dao;

import chc.dts.api.entity.ProjectInfo;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目信息表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Mapper
public interface ProjectInfoMapper extends BaseMapperX<ProjectInfo> {

}
