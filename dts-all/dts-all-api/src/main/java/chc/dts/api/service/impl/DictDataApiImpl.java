package chc.dts.api.service.impl;

import chc.dts.api.entity.DictData;
import chc.dts.api.pojo.dto.DictDataRespDTO;
import chc.dts.api.service.DictDataApi;
import chc.dts.api.service.DictDataService;
import chc.dts.common.util.object.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 字典数据 API 实现类
 *
 * @author xgy
 */
@Service
public class DictDataApiImpl implements DictDataApi {

    @Resource
    private DictDataService dictDataService;

    @Override
    public void validateDictDataList(String dictType, Collection<String> values) {
        dictDataService.validateDictDataList(dictType, values);
    }

    @Override
    public DictDataRespDTO getDictData(String dictType, String value) {
        DictData dictData = dictDataService.getDictData(dictType, value);
        return BeanUtils.toBean(dictData, DictDataRespDTO.class);
    }

    @Override
    public DictDataRespDTO parseDictData(String dictType, String label) {
        DictData dictData = dictDataService.parseDictData(dictType, label);
        return BeanUtils.toBean(dictData, DictDataRespDTO.class);
    }

    @Override
    public List<DictDataRespDTO> getDictDataList(String dictType) {
        List<DictData> list = dictDataService.getDictDataListByDictType(dictType);
        return BeanUtils.toBean(list, DictDataRespDTO.class);
    }

}
