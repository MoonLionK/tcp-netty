package chc.dts.api.controller;

import chc.dts.api.controller.vo.DeviceCodeAndNameResq;
import chc.dts.api.service.IDeviceService;
import chc.dts.common.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 设备信息表 前端控制器
 * </p>
 *
 * @author xgy
 * @since 2024-05-21
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    private IDeviceService iDeviceService;

    @GetMapping("/dropdownList ")
    @Operation(summary = "查询连接信息")
    public CommonResult<List<DeviceCodeAndNameResq>> dropdownList(@RequestParam(value = "model") String model) {
        return CommonResult.success(iDeviceService.dropdownList(model));
    }

}
