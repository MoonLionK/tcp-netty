package chc.dts.api.service.impl;

import chc.dts.api.dao.DictDataMapper;
import chc.dts.api.entity.DictData;
import chc.dts.api.entity.DictType;
import chc.dts.api.pojo.constants.ErrorCodeConstants;
import chc.dts.api.pojo.vo.data.DictDataPageReqVO;
import chc.dts.api.pojo.vo.data.DictDataSaveReqVO;
import chc.dts.api.service.DictDataService;
import chc.dts.api.service.DictTypeService;
import chc.dts.common.enums.CommonStatusEnum;
import chc.dts.common.exception.util.ServiceExceptionUtil;
import chc.dts.common.pojo.PageResult;
import chc.dts.common.util.collection.CollectionUtils;
import chc.dts.common.util.object.BeanUtils;
import cn.hutool.core.collection.CollUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static chc.dts.common.exception.util.ServiceExceptionUtil.exception;


/**
 * 字典数据 Service 实现类
 *
 * @author dts
 */
@Service
@Slf4j
public class DictDataServiceImpl implements DictDataService {

    /**
     * 排序 dictType > sort
     */
    private static final Comparator<DictData> COMPARATOR_TYPE_AND_SORT = Comparator
            .comparing(DictData::getDictType)
            .thenComparingInt(DictData::getSort);

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataMapper dictDataMapper;

    @Override
    public List<DictData> getDictDataList(Integer status, String dictType) {
        List<DictData> list = dictDataMapper.selectListByStatusAndDictType(status, dictType);
        list.sort(COMPARATOR_TYPE_AND_SORT);
        return list;
    }

    @Override
    public PageResult<DictData> getDictDataPage(DictDataPageReqVO pageReqVO) {
        return dictDataMapper.selectPage(pageReqVO);
    }

    @Override
    public DictData getDictData(Long id) {
        return dictDataMapper.selectById(id);
    }

    @Override
    public Long createDictData(DictDataSaveReqVO createReqVO) {
        // 校验字典类型有效
        validateDictTypeExists(createReqVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(null, createReqVO.getDictType(), createReqVO.getValue());

        // 插入字典类型
        DictData dictData = BeanUtils.toBean(createReqVO, DictData.class);
        dictDataMapper.insert(dictData);
        return dictData.getId();
    }

    @Override
    public void updateDictData(DictDataSaveReqVO updateReqVO) {
        // 校验自己存在
        validateDictDataExists(updateReqVO.getId());
        // 校验字典类型有效
        validateDictTypeExists(updateReqVO.getDictType());
        // 校验字典数据的值的唯一性
        validateDictDataValueUnique(updateReqVO.getId(), updateReqVO.getDictType(), updateReqVO.getValue());

        // 更新字典类型
        DictData updateObj = BeanUtils.toBean(updateReqVO, DictData.class);
        dictDataMapper.updateById(updateObj);
    }

    @Override
    public void deleteDictData(Long id) {
        // 校验是否存在
        validateDictDataExists(id);

        // 删除字典数据
        dictDataMapper.deleteById(id);
    }

    @Override
    public long getDictDataCountByDictType(String dictType) {
        return dictDataMapper.selectCountByDictType(dictType);
    }

    @VisibleForTesting
    public void validateDictDataValueUnique(Long id, String dictType, String value) {
        DictData dictData = dictDataMapper.selectByDictTypeAndValue(dictType, value);
        if (dictData == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的字典数据
        if (id == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_DATA_VALUE_DUPLICATE);
        }
        if (!dictData.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_DATA_VALUE_DUPLICATE);
        }
    }

    @VisibleForTesting
    public void validateDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictData dictData = dictDataMapper.selectById(id);
        if (dictData == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_DATA_NOT_EXISTS);
        }
    }

    @VisibleForTesting
    public void validateDictTypeExists(String type) {
        DictType dictType = dictTypeService.getDictType(type);
        if (dictType == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NOT_EXISTS);
        }
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_NOT_ENABLE);
        }
    }

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        Map<String, DictData> dictDataMap = CollectionUtils.convertMap(
                dictDataMapper.selectByDictTypeAndValues(dictType, values), DictData::getValue);
        // 校验
        values.forEach(value -> {
            DictData dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_DATA_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(dictData.getStatus())) {
                throw exception(ErrorCodeConstants.DICT_DATA_NOT_ENABLE, dictData.getLabel());
            }
        });
    }

    @Override
    public DictData getDictData(String dictType, String value) {
        return dictDataMapper.selectByDictTypeAndValue(dictType, value);
    }

    @Override
    public DictData parseDictData(String dictType, String label) {
        return dictDataMapper.selectByDictTypeAndLabel(dictType, label);
    }

    @Override
    public List<DictData> getDictDataListByDictType(String dictType) {
        List<DictData> list = dictDataMapper.selectList(DictData::getDictType, dictType);
        list.sort(Comparator.comparing(DictData::getSort));
        return list;
    }

}
