package chc.dts.api.service;


import chc.dts.api.entity.DictType;
import chc.dts.api.pojo.vo.type.DictTypePageReqVO;
import chc.dts.api.pojo.vo.type.DictTypeSaveReqVO;
import chc.dts.common.pojo.PageResult;

import java.util.List;

/**
 * 字典类型 Service 接口
 *
 * @author xgy
 */
public interface DictTypeService {

    /**
     * 创建字典类型
     *
     * @param createReqVO 字典类型信息
     * @return 字典类型编号
     */
    Long createDictType(DictTypeSaveReqVO createReqVO);

    /**
     * 更新字典类型
     *
     * @param updateReqVO 字典类型信息
     */
    void updateDictType(DictTypeSaveReqVO updateReqVO);

    /**
     * 删除字典类型
     *
     * @param id 字典类型编号
     */
    void deleteDictType(Long id);

    /**
     * 获得字典类型分页列表
     *
     * @param pageReqVO 分页请求
     * @return 字典类型分页列表
     */
    PageResult<DictType> getDictTypePage(DictTypePageReqVO pageReqVO);

    /**
     * 获得字典类型详情
     *
     * @param id 字典类型编号
     * @return 字典类型
     */
    DictType getDictType(Long id);

    /**
     * 获得字典类型详情
     *
     * @param type 字典类型
     * @return 字典类型详情
     */
    DictType getDictType(String type);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<DictType> getDictTypeList();

}
