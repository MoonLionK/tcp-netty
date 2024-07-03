package chc.dts.all.controller;

import chc.dts.all.entity.User;
import chc.dts.all.service.IUserService;
import chc.dts.api.pojo.vo.data.UserReqVo;
import chc.dts.api.pojo.vo.data.UserRespVO;
import chc.dts.common.pojo.CommonResult;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.object.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

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

    IUserService userService;

    @PostMapping("/create")
    @Operation(summary = "新增用户数据")
    public CommonResult<Integer> createDictData(@Valid @RequestBody UserReqVo createReqVO) {
        Integer userId = userService.createUser(createReqVO);
        return success(userId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户数据")
    public CommonResult<Boolean> updateDictData(@Valid @RequestBody UserReqVo updateReqVO) {
        userService.updateUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Boolean> deleteUser(Integer id) {
        userService.deleteUser(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "/获得用户的分页列表")
    public CommonResult<PageResult<UserRespVO>> getUserPage(@Valid UserReqVo pageReqVO) {
        PageResult<User> pageResult = userService.getUserPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UserRespVO.class));
    }
}
