package chc.dts.all.service.impl;

import chc.dts.all.dao.UserMapper;
import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
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

}
