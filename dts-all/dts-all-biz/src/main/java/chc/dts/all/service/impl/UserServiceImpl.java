package chc.dts.all.service.impl;

import chc.dts.all.dao.UserMapper;
import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
import chc.dts.api.pojo.constants.ErrorCodeConstants;
import chc.dts.api.pojo.vo.data.UserAddOrUpdateVO;
import chc.dts.api.pojo.vo.data.UserReqVo;
import chc.dts.common.exception.util.ServiceExceptionUtil;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public User getUserByName(String userName) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUsername, userName).eq(User::getDeleted, 0);
        return getOne(userLambdaQueryWrapper);
    }

    @Override
    public PageResult<User> getUserPage(UserReqVo pageReqVO) {
        PageResult<User> userPageResult = getBaseMapper().selectPage(pageReqVO);
        userPageResult.getList().forEach(t-> t.setPassword("******"));
        return userPageResult;
    }


    @Override
    public boolean updateOrInsertUser(UserAddOrUpdateVO updateReqVO) {
        //添加
        User user = new User();
        BeanUtils.copyProperties(updateReqVO, user);
        validateUserNameExists(updateReqVO.getUsername(), user);

        if (user.getId() == null) {
            return SqlHelper.retBool(getBaseMapper().insert(user));
        } else {
            return SqlHelper.retBool(getBaseMapper().updateById(user));
        }

    }

    @Override
    public boolean deleteUser(String username) {
        return SqlHelper.retBool(getBaseMapper().delete(new LambdaQueryWrapper<User>().eq(User::getUsername, username)));
    }

    @Override
    public void validateUserNameExists(String userName, User oldUser) {
        User user = getUserByName(userName);
        if (user != null && !user.getId().equals(oldUser.getId())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_USERNAME_EXISTS);
        }
    }
}
