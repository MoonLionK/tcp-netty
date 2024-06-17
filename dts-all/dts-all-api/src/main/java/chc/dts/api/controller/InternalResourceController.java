package chc.dts.api.controller;

import chc.dts.api.common.lang.Langutil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 国际化资源表 前端控制器
 * </p>
 *
 * @author fyh
 * @since 2024-06-13
 */
@RestController
@RequestMapping("/internalResource")
public class InternalResourceController {

    @Resource
    Langutil langutil;

    @ApiOperation("多语言测试")
    @ApiImplicitParam(name = "deviceType", value = "终端类型(1:pc,2:app)", required = false, dataType = "int", paramType = "query")
    @PostMapping("/test")
    public String languageTest() {
        return langutil.getMsg("evaporation");
    }
}
