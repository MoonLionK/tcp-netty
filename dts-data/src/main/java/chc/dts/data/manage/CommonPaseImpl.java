package chc.dts.data.manage;

import chc.dts.api.dao.SensorCalculateConfigMapper;
import chc.dts.api.dao.SensorMatchConfigMapper;
import chc.dts.api.entity.CalculatedData;
import chc.dts.api.entity.MatchedData;
import chc.dts.api.entity.SensorInfo;
import chc.dts.api.pojo.dto.SensorCalculateDTO;
import chc.dts.api.pojo.dto.SensorMatchDTO;
import chc.dts.api.service.impl.CalculatedDataServiceImpl;
import chc.dts.api.service.impl.MatchedDataServiceImpl;
import chc.dts.api.service.impl.SensorInfoServiceImpl;
import chc.dts.common.core.KeyValue;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.data.manage.function.ExpressRunnerPack;
import chc.dts.data.pojo.dto.CalculatedDataDto;
import chc.dts.data.pojo.dto.MatchedDataDto;
import chc.dts.data.pojo.dto.ParsedData;
import chc.dts.data.pojo.dto.SensorConfigDto;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ql.util.express.DefaultContext;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 协议解析公共抽象类
 *
 * @author xgy
 */
@Slf4j
public abstract class CommonPaseImpl implements IDataInterface {

    @Resource
    SensorCalculateConfigMapper sensorCalculateConfigMapper;

    @Resource
    SensorMatchConfigMapper sensorMatchConfigMapper;

    @Resource
    SensorInfoServiceImpl sensorInfoService;

    @Resource
    MatchedDataServiceImpl matchedDataService;

    @Resource
    CalculatedDataServiceImpl calculatedDataService;


    public List<SensorInfo> getSensorList(String deviceCode) {
        return sensorInfoService.getListByDeviceId(deviceCode);
    }

    public Map<String, SensorConfigDto> getSensorConfigList(List<String> sensorCode) {
        List<SensorCalculateDTO> calculateConfigs = sensorCalculateConfigMapper.getCalculateConfigs(sensorCode);
        List<SensorMatchDTO> matchConfigs = sensorMatchConfigMapper.getMatchConfigs(sensorCode);
        Map<String, List<SensorMatchDTO>> matchList = matchConfigs.stream().collect(Collectors.groupingBy(SensorMatchDTO::getSensorCode));
        Map<String, List<SensorCalculateDTO>> calculateList = calculateConfigs.stream().collect(Collectors.groupingBy(SensorCalculateDTO::getSensorCode));
        Map<String, SensorConfigDto> result = new HashMap<>(64);
        matchList.forEach((k, v) -> {
            result.put(k, new SensorConfigDto(k, calculateList.get(k), v));
        });
        return result;
    }



    public Boolean check(List<KeyValue<String, String>> parseData, List<SensorMatchDTO> configs) {
        Map<String, String> resourceMap = parseData.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));

        //空匹配直接返回错误
        if (CollUtil.isEmpty(configs)){
            return false;
        }

        Map<Integer, List<SensorMatchDTO>> logicCollect = configs.stream().collect(Collectors.groupingBy(SensorMatchDTO::getLogicType));

        //check必须满足的条件
        for (SensorMatchDTO sensorMatchDTO : logicCollect.get(0)) {
            Boolean onceCheckResult= checkOnce(sensorMatchDTO,resourceMap);
            if (!onceCheckResult){
                return false;
            }
        }
        //check满足一个的条件
        List<SensorMatchDTO> sensorMatchDTOS = logicCollect.get(1);
        if (!CollUtil.isEmpty(sensorMatchDTOS)){
            boolean otherResult;
            for (SensorMatchDTO sensorMatchDTO : sensorMatchDTOS) {
                otherResult=checkOnce(sensorMatchDTO,resourceMap);
                if (otherResult){
                    return true;
                }
            }
            return false;
        }


        return true;
    }

    private Boolean checkOnce(SensorMatchDTO sensorMatchDTO, Map<String, String> resourceMap) {
        Integer checkType = sensorMatchDTO.getCheckType();
        //kye_value都需要匹配
        if (checkType == 0) {
            String[] split = sensorMatchDTO.getMatchRule().split(":");
            if (split.length != 2) {
                throw new ServiceException(new ErrorCode(500, "配置规则错误 "));
            }
            String values = resourceMap.get(split[0]);
            if (values == null || !values.equals(split[1])) {
                log.info("匹配失败");
                return false;
            }
            //只需要匹配key,大小写都检索
        } else {
            if (!resourceMap.containsKey(sensorMatchDTO.getMatchRule())) {
                log.info("匹配失败");
                return false;
            }
        }
        return true;
    }

    @Override
    public List<MatchedDataDto> matching(ParsedData parsedData) {
        //查询所有的传感器
        List<MatchedDataDto> result = new ArrayList<>();

        List<SensorInfo> sensorInfos = getSensorList(parsedData.getDeviceCode());
        if (CollUtil.isEmpty(sensorInfos)) {
            return result;
        }

        List<String> sensorCodes = sensorInfos.stream().map(SensorInfo::getSensorCode).collect(Collectors.toList());
        Map<String, SensorConfigDto> sensorConfigMap = getSensorConfigList(sensorCodes);
        for (SensorInfo sensorInfo : sensorInfos) {
            /*
             * 1.获取本次传感器当中的所有配置
             * 2.根据配置进行校验
             * 3.返回匹配后的结果
             * */
            List<SensorMatchDTO> sensorMatchConfigs = sensorConfigMap.get(sensorInfo.getSensorCode()).getSensorMatchConfigs();
            Boolean check;

             check = check(parsedData.getParseData(), sensorMatchConfigs);

            if (check) {
                MatchedDataDto matchedDataDto = new MatchedDataDto();
                matchedDataDto.setSensorCode(sensorInfo.getSensorCode());
                matchedDataDto.setMatchData(parsedData.parseData);

                result.add(matchedDataDto);
            }

        }
        addMatchedData(result);
        return result;
    }

    public Boolean addMatchedData(List<MatchedDataDto> matchedDataDtos) {
        List<MatchedData> toAdd = new ArrayList<>();
        for (MatchedDataDto matchedDataDto : matchedDataDtos) {
            //添加到记录当中
            MatchedData matchedData = new MatchedData();
            matchedData.setSensorCode(matchedDataDto.getSensorCode());
            Map<String, Object> json = matchedDataDto.getMatchData().stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));

            matchedData.setMatchedData(JSONObject.parseObject(JSON.toJSONString(json)));
            matchedData.setCreator(0);
            matchedData.setUpdater(0);
            toAdd.add(matchedData);
        }
        return matchedDataService.getBaseMapper().insertBatch(toAdd);
    }


    @Override
    public List<CalculatedDataDto> calculate(List<MatchedDataDto> matchedDataDtoList) {
        //查询所有的传感器
        List<String> sensorCodes = matchedDataDtoList.stream().map(MatchedDataDto::getSensorCode).collect(Collectors.toList());
        Map<String, SensorConfigDto> sensorConfigMap = getSensorConfigList(sensorCodes);
        List<CalculatedDataDto> result = new ArrayList<>();
        for (MatchedDataDto matchedDataDto : matchedDataDtoList) {
            /*
             * 1.获取本次传感器当中的所有配置
             * 2.根据配置进行计算
             * 3.返回计算后的值
             * */
            List<SensorCalculateDTO> calculateConfigs = sensorConfigMap.get(matchedDataDto.getSensorCode()).getCalculateConfigs();
            if (!CollUtil.isEmpty(calculateConfigs)) {
                List<KeyValue<String, Object>> calculateResult = calculateOnce(matchedDataDto.getMatchData(), calculateConfigs);
                CalculatedDataDto calculatedDataDto = new CalculatedDataDto(matchedDataDto.getSensorCode(), calculateResult);
                result.add(calculatedDataDto);
            }
        }
        addCalculatedData(result);
        return result;
    }

    public List<KeyValue<String, Object>> calculateOnce(List<KeyValue<String, String>> parse, List<SensorCalculateDTO> configs) {
        List<KeyValue<String, Object>> result = new ArrayList<>();
        Map<String, String> resourceMap = parse.stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
        //校验完所有规则开始计算
        for (SensorCalculateDTO sensorConfigDto : configs) {
            Integer calculateType = sensorConfigDto.getCalculateType();
            String calculateRule = sensorConfigDto.getCalculateRule();

            if (ObjectUtil.isEmpty(calculateType)) {
                continue;
            }
            if (calculateType == 1) {
                result.add(new KeyValue<>(sensorConfigDto.getResultName(), resourceMap.get(calculateRule)));
            } else {
                ExpressRunnerPack runner = new ExpressRunnerPack();
                DefaultContext<String, Object> context = new DefaultContext<>();
                //放入计算参数，如果是数字修改为double类型
                resourceMap.forEach((t, v) -> {
                    context.put(t, v.matches("-?\\d+(\\.\\d+)?") ? Double.valueOf(v) : v);
                });
                context.putAll(JSONObject.toJavaObject(sensorConfigDto.getCalculateParam(), Map.class));

                Object calculateResult;
                try {
                    calculateResult = runner.getExpressRunner().execute(sensorConfigDto.getCalculateRule(), context, null, true, true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                result.add(new KeyValue<>(sensorConfigDto.getResultName(), calculateResult));
            }
        }

        return result;
    }

    public Boolean addCalculatedData(List<CalculatedDataDto> calculatedDataDtos) {
        List<CalculatedData> toAdd = new ArrayList<>();
        for (CalculatedDataDto calculatedData : calculatedDataDtos) {
            //添加到记录当中
            CalculatedData calculatedData1 = new CalculatedData();
            calculatedData1.setSensorCode(calculatedData.getSensorCode());

            Map<String, Object> json = calculatedData.getCalculateData().stream().collect(Collectors.toMap(KeyValue::getKey, KeyValue::getValue));
            calculatedData1.setCalculatedData(JSONObject.parseObject(JSON.toJSONString(json)));
            calculatedData1.setCreator(0);
            calculatedData1.setUpdater(0);
            toAdd.add(calculatedData1);
        }
        return calculatedDataService.getBaseMapper().insertBatch(toAdd);
    }


}
