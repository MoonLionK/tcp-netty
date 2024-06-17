package chc.dts.data.manage;

import chc.dts.data.pojo.dto.CalculatedDataDto;
import chc.dts.data.pojo.dto.MatchedDataDto;
import chc.dts.data.pojo.dto.ParsedData;

import java.util.List;

/**
 * 数据处理通用接口
 *
 * @author xgy
 * @date 2024/5/13 16:05
 */
public interface IDataInterface {

    /**
     * 解析
     */
    ParsedData parse(String deviceCode, String message);

    /**
     * 匹配
     */
    List<MatchedDataDto> matching(ParsedData parsedData);

    /**
     * 计算
     */
    List<CalculatedDataDto> calculate(List<MatchedDataDto> matchedDataDtoList);

    /**
     * 转发
     */
    void transmit(List<CalculatedDataDto> calculatedDataDtoList);
}
