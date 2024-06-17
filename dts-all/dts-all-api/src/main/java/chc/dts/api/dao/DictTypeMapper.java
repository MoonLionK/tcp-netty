package chc.dts.api.dao;


import chc.dts.api.entity.DictType;
import chc.dts.api.pojo.vo.type.DictTypePageReqVO;
import chc.dts.common.pojo.PageResult;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import chc.dts.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

@Mapper
@DS("system")
public interface DictTypeMapper extends BaseMapperX<DictType> {

    default PageResult<DictType> selectPage(DictTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictType>()
                .likeIfPresent(DictType::getName, reqVO.getName())
                .likeIfPresent(DictType::getType, reqVO.getType())
                .eqIfPresent(DictType::getStatus, reqVO.getStatus())
                .betweenIfPresent(DictType::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DictType::getId));
    }

    default DictType selectByType(String type) {
        return selectOne(DictType::getType, type);
    }

    default DictType selectByName(String name) {
        return selectOne(DictType::getName, name);
    }

    @Update("UPDATE system_dict_type SET deleted = 1, deleted_time = #{deletedTime} WHERE id = #{id}")
    void updateToDelete(@Param("id") Long id, @Param("deletedTime") LocalDateTime deletedTime);

}
