package chc.dts.all.service.impl;

import chc.dts.all.dao.UserMapper;
import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
import chc.dts.api.pojo.constants.ErrorCodeConstants;
import chc.dts.api.pojo.vo.data.UserReqVo;
import chc.dts.common.exception.util.ServiceExceptionUtil;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.auth.TokenUtil;
import chc.dts.common.util.object.BeanUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
        return getBaseMapper().selectPage(pageReqVO);
    }

    @Override
    public Integer createUser(UserReqVo createReqVO) {
        //校验重名
        validateUserNameExists(createReqVO.getUsername(), null);
        //添加
        User user = new User();
        user.setCreator(TokenUtil.getLoginId());
        BeanUtils.copyProperties(createReqVO, user);
        return getBaseMapper().insert(user);
    }

    @Override
    public void updateUser(UserReqVo updateReqVO) {
        //添加
        User user = new User();
        BeanUtils.copyProperties(updateReqVO, user);

        validateUserNameExists(updateReqVO.getUsername(), user);
        getBaseMapper().updateById(user);
    }

    @Override
    public void deleteUser(Integer id) {
        getBaseMapper().delete(new LambdaQueryWrapper<User>().eq(User::getDeleted, id));
    }

    @Override
    public void validateUserNameExists(String userName, User oldUser) {
        User user = getUserByName(userName);
        if (user != null && !user.getId().equals(oldUser.getId())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.USER_USERNAME_EXISTS);
        }
    }
}
