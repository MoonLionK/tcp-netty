package chc.dts.data.manage;

import chc.dts.api.entity.Device;
import chc.dts.api.service.IDeviceService;
import chc.dts.common.exception.ErrorCode;
import chc.dts.common.exception.ServiceException;
import chc.dts.common.pojo.RedisMessage;
import chc.dts.data.manage.impl.NjgnImpl;
import chc.dts.data.manage.impl.SwxyHexDbaqDemo;
import chc.dts.data.manage.impl.SwxySixImpl;
import chc.dts.data.pojo.dto.CalculatedDataDto;
import chc.dts.data.pojo.dto.MatchedDataDto;
import chc.dts.data.pojo.dto.ParsedData;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static chc.dts.data.pojo.constans.DeviceTypeConstant.*;

/**
 * 数据处理组合类
 *
 * @author xgy
 * @date 2024/5/13 16:09
 */
@Component
public class DataManage {
    @Resource
    private IDeviceService deviceService;
    @Resource
    private SwxySixImpl swxySix;
    @Resource
    private SwxyHexDbaqDemo swxyHexDbaqDemo;

    @Resource
    private NjgnImpl njgnImpl;


    //key:设备类型 value:数据处理实现类
    private final static HashMap<String, IDataInterface> DATA_IMPL_HASH_MAP = new HashMap<>();

    /**
     * 维护设备和数据处理类的对应关系
     */
    @PostConstruct
    void init() {
        DATA_IMPL_HASH_MAP.put(SWXY_SIX, swxySix);
        DATA_IMPL_HASH_MAP.put(DB_AQ, swxyHexDbaqDemo);
        DATA_IMPL_HASH_MAP.put(NJGN_CXY, njgnImpl);
    }

    /**
     * 数据处理组合方法
     *
     * @param message RedisMessage
     */
    public void dealData(RedisMessage message) {
        String localAddress = message.getLocalAddress();
        if (StringUtils.isEmpty(localAddress)) {
            throw new ServiceException(new ErrorCode(500, localAddress + " is not exist"));
        }
        Device device = deviceService.selectDeviceTypeByLocalAddress(localAddress);
        IDataInterface iDataInterface = DATA_IMPL_HASH_MAP.get(device.getDeviceType());
        if (ObjectUtils.isEmpty(iDataInterface)) {
            throw new ServiceException(new ErrorCode(500, localAddress + " is not exist in DATA_IMPL_HASH_MAP"));
        }
        //解析
        ParsedData parse = iDataInterface.parse(device.getDeviceCode(), message.getMessage());
        //匹配
        List<MatchedDataDto> matching = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(parse)) {
            matching = iDataInterface.matching(parse);
        }
        //计算
        List<CalculatedDataDto> calculate = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(matching)) {
            calculate = iDataInterface.calculate(matching);
        }
        //转发
        if (CollectionUtils.isNotEmpty(calculate)) {
            iDataInterface.transmit(calculate);
        }
    }
}
