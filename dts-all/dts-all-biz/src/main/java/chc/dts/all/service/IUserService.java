package chc.dts.all.service;

import chc.dts.all.entity.User;
import chc.dts.api.pojo.vo.data.UserReqVo;
import chc.dts.common.pojo.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
public interface IUserService extends IService<User> {

    /**
     * 根据账户获取用户信息
     * @param userName 用户名
     * @return 用户
     */
    public User getUserByName(String userName);

    PageResult<User> getUserPage(UserReqVo pageReqVO);

    Integer createUser(UserReqVo createReqVO);

    void validateUserNameExists(String userName,User oldUser);

    void updateUser(UserReqVo updateReqVO);

    void deleteUser(Integer id);
}
