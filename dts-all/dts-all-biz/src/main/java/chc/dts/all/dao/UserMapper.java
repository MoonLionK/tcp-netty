package chc.dts.all.dao;

import chc.dts.all.entity.User;
import chc.dts.api.pojo.vo.data.UserReqVo;
import chc.dts.common.pojo.PageResult;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import chc.dts.mybatis.core.query.LambdaQueryWrapperX;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Mapper
@DS("system")
public interface UserMapper extends BaseMapperX<User> {

    /**
     * 用户分页查询
     * @param userReqVo 请求体
     * @return 用户分页列表
     */
    default PageResult<User> selectPage(UserReqVo userReqVo) {
        return selectPage(userReqVo, new LambdaQueryWrapperX<User>()
                .likeIfPresent(User::getUsername, userReqVo.getUsername())
                .orderByDesc(User::getCreateTime));
    }

}
