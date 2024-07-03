package chc.dts.all.controller;

import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
    public String login(@RequestParam String username, @RequestParam String password) {
        User userByName = iUserService.getUserByName(username);
        // 验证用户名和密码（这里直接假设验证通过）
        if (!ObjectUtil.isEmpty(userByName) && userByName.getPassword().equals(password)) {
            // 用id进行登录
            StpUtil.login(userByName.getId());
            return "登录成功，Token: " + StpUtil.getTokenValue();
        }
        return "用户名或密码错误";
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public String logout() {
        StpUtil.logout();
        return "登出成功";
    }

    // 获取用户 ID 接口
    @Operation(summary = "获取登录id")
    @GetMapping("/getUserId")
    public String getUserId() {
        if (StpUtil.isLogin()) {
            return "当前登录用户的ID是：" + StpUtil.getLoginId();
        } else {
            return "当前未登录";
        }
    }


}
