package chc.dts.all.controller;

import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
import chc.dts.api.pojo.vo.data.LoginVo;
import chc.dts.common.pojo.CommonResult;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static chc.dts.common.pojo.CommonResult.error;
import static chc.dts.common.pojo.CommonResult.success;

/**
 * @author xgy
 * @date 2024/06/17
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    IUserService iUserService;


    @Operation(summary = "登录")
    @PostMapping("/login")
    public CommonResult<String> login(@RequestBody LoginVo loginVo)  {
        User userByName = iUserService.getUserByName(loginVo.getUsername());
        // 验证用户名和密码（这里直接假设验证通过）
        if (!ObjectUtil.isEmpty(userByName) && userByName.getPassword().equals(loginVo.getPassword())) {
            // 用id进行登录
            StpUtil.login(userByName.getId());
            return success(StpUtil.getTokenValue());
        }
        return error(500, "用户名或密码错误");
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public CommonResult<String> logout() {
        StpUtil.logout();
        return success("登出成功");
    }

    /**
     * 获取登录id
     */
    @Operation(summary = "获取登录id")
    @GetMapping("/getUserId")
    public CommonResult<Long> getUserId() {
        if (StpUtil.isLogin()) {
            return success((Long) StpUtil.getLoginId());
        } else {
            return error(500, "当前未登录");
        }
    }

    /**
     * 获取登录用户
     */
    @Operation(summary = "获取登录用户信息")
    @GetMapping("/getUser")
    public CommonResult<User> getUser() {
        if (StpUtil.isLogin()) {
            Long loginId = Long.valueOf((String) StpUtil.getLoginId());
            User user = iUserService.getById(loginId).setPassword("******");
            return success(user);
        } else {
            return error(500, "当前未登录");
        }
    }


}
