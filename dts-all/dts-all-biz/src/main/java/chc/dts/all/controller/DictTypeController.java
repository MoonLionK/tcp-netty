package chc.dts.all.controller;


import chc.dts.api.entity.DictType;
import chc.dts.api.excel.core.util.ExcelUtils;
import chc.dts.api.pojo.vo.type.DictTypePageReqVO;
import chc.dts.api.pojo.vo.type.DictTypeRespVO;
import chc.dts.api.pojo.vo.type.DictTypeSaveReqVO;
import chc.dts.api.pojo.vo.type.DictTypeSimpleRespVO;
import chc.dts.api.service.DictTypeService;
import chc.dts.common.pojo.CommonResult;
import chc.dts.common.pojo.PageParam;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.object.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static chc.dts.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 字典类型")
@RestController
@RequestMapping("/system/dict-type")
@Validated
public class DictTypeController {

    @Resource
    private DictTypeService dictTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建字典类型")
    public CommonResult<Long> createDictType(@Valid @RequestBody DictTypeSaveReqVO createReqVO) {
        Long dictTypeId = dictTypeService.createDictType(createReqVO);
        return success(dictTypeId);
    }

    @PutMapping("/update")
    @Operation(summary = "修改字典类型")
    public CommonResult<Boolean> updateDictType(@Valid @RequestBody DictTypeSaveReqVO updateReqVO) {
        dictTypeService.updateDictType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除字典类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<Boolean> deleteDictType(Long id) {
        dictTypeService.deleteDictType(id);
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得字典类型的分页列表")
    public CommonResult<PageResult<DictTypeRespVO>> pageDictTypes(@Valid DictTypePageReqVO pageReqVO) {
        PageResult<DictType> pageResult = dictTypeService.getDictTypePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DictTypeRespVO.class));
    }

    @Operation(summary = "/查询字典类型详细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @GetMapping(value = "/get")
    public CommonResult<DictTypeRespVO> getDictType(@RequestParam("id") Long id) {
        DictType dictType = dictTypeService.getDictType(id);
        return success(BeanUtils.toBean(dictType, DictTypeRespVO.class));
    }

    @GetMapping(value = {"/list-all-simple", "simple-list"})
    @Operation(summary = "获得全部字典类型列表", description = "包括开启 + 禁用的字典类型，主要用于前端的下拉选项")
    // 无需添加权限认证，因为前端全局都需要
    public CommonResult<List<DictTypeSimpleRespVO>> getSimpleDictTypeList() {
        List<DictType> list = dictTypeService.getDictTypeList();
        return success(BeanUtils.toBean(list, DictTypeSimpleRespVO.class));
    }

    @Operation(summary = "导出数据类型")
    @GetMapping("/export")
    public void export(HttpServletResponse response, @Valid DictTypePageReqVO exportReqVO) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DictType> list = dictTypeService.getDictTypePage(exportReqVO).getList();
        // 导出
        ExcelUtils.write(response, "字典类型.xls", "数据", DictTypeRespVO.class,
                BeanUtils.toBean(list, DictTypeRespVO.class));
    }

}
