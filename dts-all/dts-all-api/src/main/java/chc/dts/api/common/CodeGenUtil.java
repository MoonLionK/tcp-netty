package chc.dts.api.common;

import chc.dts.api.dao.CodeGenMapper;
import chc.dts.api.pojo.constants.CodeGenEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 编码生成工具类
 *
 * @author xgy
 * @date 2024/5/30 9:34
 */
@Component
@Slf4j
public class CodeGenUtil {
    @Resource
    private CodeGenMapper codeGenMapper;

    /**
     * 获取编码
     *
     * @param codeGenEnum 编码枚举
     * @return 编码
     */
    public String getCode(CodeGenEnum codeGenEnum) {
        Long id = codeGenMapper.getNextId(codeGenEnum.getTableName());
        return codeGenEnum.getStr() + String.format("%05d", id);
    }

    /**
     * 获取传感器编码
     *
     * @param deviceCode 设备编码
     * @return 传感器编码
     */
    public String getSensorCode(String deviceCode) {
        Long id = codeGenMapper.getNextId("sensor_info");
        return deviceCode + "-" + String.format("%03d", id);
    }

}
