package chc.dts.all.service;

import chc.dts.all.entity.User;
import chc.dts.api.pojo.vo.data.UserAddOrUpdateVO;
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
     User getUserByName(String userName);


    /**
     * 获取用户分页信息
     * @param pageReqVO 分页请求参数
     * @return 分页结果
     */
    PageResult<User> getUserPage(UserReqVo pageReqVO);


    /**
     * * 验证用户名是否存在
     * @param userName 用户名
     * @param oldUser 旧用户
     */
    void validateUserNameExists(String userName,User oldUser);

    /**
     * * 更新或者新增用户信息
     * @param updateReqVO 更新或者新增用户信息
     * @return 更新或者新增结果
     */
    boolean updateOrInsertUser(UserAddOrUpdateVO updateReqVO);


    /**
     * * 删除用户
     * @param userName 用户名
     * @return  删除结果
     */
    boolean deleteUser(String userName);
}
