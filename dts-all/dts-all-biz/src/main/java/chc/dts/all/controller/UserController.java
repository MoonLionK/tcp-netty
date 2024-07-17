package chc.dts.all.controller;

import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
import chc.dts.api.pojo.vo.data.UserAddOrUpdateVO;
import chc.dts.api.pojo.vo.data.UserReqVo;
import chc.dts.common.pojo.CommonResult;
import chc.dts.common.pojo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static chc.dts.common.pojo.CommonResult.success;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    IUserService userService;

    @PostMapping("/createOrUpdate")
    @Operation(summary = "新增或修改用户数据")
    public CommonResult<Boolean> createDictData(@Valid @RequestBody UserAddOrUpdateVO reqVO) {
        Boolean result = userService.updateOrInsertUser(reqVO);
        return success(result);
    }


    @DeleteMapping("/delete")
    @Operation(summary = "删除用户数据")
    @Parameter(name = "userName", description = "用户名称", required = true, example = "admin")
    public CommonResult<Boolean> deleteUser(String userName) {
        Boolean result = userService.deleteUser(userName);
        return success(result);
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户的分页列表")
    public CommonResult<PageResult<User>> getUserPage(@Valid @RequestBody UserReqVo pageReqVO) {
        PageResult<User> pageResult = userService.getUserPage(pageReqVO);
        return success(pageResult);
    }
}
