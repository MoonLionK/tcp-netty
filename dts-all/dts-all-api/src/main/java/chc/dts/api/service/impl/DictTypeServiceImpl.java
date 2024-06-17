package chc.dts.api.service.impl;

import chc.dts.api.dao.DictTypeMapper;
import chc.dts.api.entity.DictType;
import chc.dts.api.pojo.constants.ErrorCodeConstants;
import chc.dts.api.pojo.vo.type.DictTypePageReqVO;
import chc.dts.api.pojo.vo.type.DictTypeSaveReqVO;
import chc.dts.api.service.DictDataService;
import chc.dts.api.service.DictTypeService;
import chc.dts.common.exception.util.ServiceExceptionUtil;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.date.LocalDateTimeUtils;
import chc.dts.common.util.object.BeanUtils;
import cn.hutool.core.util.StrUtil;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典类型 Service 实现类
 *
 * @author xgy
 */
@Service
public class DictTypeServiceImpl implements DictTypeService {

    @Resource
    private DictDataService dictDataService;

    @Resource
    private DictTypeMapper dictTypeMapper;

    @Override
    public PageResult<DictType> getDictTypePage(DictTypePageReqVO pageReqVO) {
        return dictTypeMapper.selectPage(pageReqVO);
    }

    @Override
    public DictType getDictType(Long id) {
        return dictTypeMapper.selectById(id);
    }

    @Override
    public DictType getDictType(String type) {
        return dictTypeMapper.selectByType(type);
    }

    @Override
    public Long createDictType(DictTypeSaveReqVO createReqVO) {
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(null, createReqVO.getName());
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(null, createReqVO.getType());

        // 插入字典类型
        DictType dictType = BeanUtils.toBean(createReqVO, DictType.class);
        dictType.setDeletedTime(LocalDateTimeUtils.EMPTY); // 唯一索引，避免 null 值
        dictTypeMapper.insert(dictType);
        return dictType.getId();
    }

    @Override
    public void updateDictType(DictTypeSaveReqVO updateReqVO) {
        // 校验自己存在
        validateDictTypeExists(updateReqVO.getId());
        // 校验字典类型的名字的唯一性
        validateDictTypeNameUnique(updateReqVO.getId(), updateReqVO.getName());
        // 校验字典类型的类型的唯一性
        validateDictTypeUnique(updateReqVO.getId(), updateReqVO.getType());

        // 更新字典类型
        DictType updateObj = BeanUtils.toBean(updateReqVO, DictType.class);
        dictTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictType(Long id) {
        // 校验是否存在
        DictType dictType = validateDictTypeExists(id);
        // 校验是否有字典数据
        if (dictDataService.getDictDataCountByDictType(dictType.getType()) > 0) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_HAS_CHILDREN);
        }
        // 删除字典类型
        dictTypeMapper.updateToDelete(id, LocalDateTime.now());
    }

    @Override
    public List<DictType> getDictTypeList() {
        return dictTypeMapper.selectList();
    }

    @VisibleForTesting
    void validateDictTypeNameUnique(Long id, String name) {
        DictType dictType = dictTypeMapper.selectByName(name);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NAME_DUPLICATE);
        }
    }

    @VisibleForTesting
    void validateDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictType dictType = dictTypeMapper.selectByType(type);
        if (dictType == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典类型
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
        }
        if (!dictType.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_TYPE_DUPLICATE);
        }
    }

    @VisibleForTesting
    DictType validateDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictType dictType = dictTypeMapper.selectById(id);
        if (dictType == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

}
