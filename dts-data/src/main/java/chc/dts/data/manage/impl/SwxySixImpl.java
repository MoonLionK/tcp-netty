package chc.dts.data.manage.impl;

import chc.dts.api.entity.Device;
import chc.dts.data.manage.IDataInterface;
import chc.dts.data.pojo.dto.CalculatedDataDto;
import chc.dts.data.pojo.dto.MatchedDataDto;
import chc.dts.data.pojo.dto.ParsedData;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static chc.dts.data.util.ParseUtil.*;

/**
 * 六通道振弦RTU报文解析
 *
 * @author xgy
 * @date 2024/5/13 16:10
 */
@Slf4j
@Service
public class SwxySixImpl implements IDataInterface {
    public List<ParsedData> parse(String message) {
        //1 获取device_data_config中的配置信息
        String msg = message;
        // 将十六进制字符串转换为字节数组
        byte[] byteArray = hexStringToByteArray(msg);
        // 将字节数组转换为字符串
        String resultString = new String(byteArray);
        String string = resultString.split("ST")[1];
        // 打印结果
        System.out.println("Resulting String: " + resultString);

        //中心站地址
        String resultString1 = getSubArray(byteArray, 1, 2);
        //遥测站地址
        String resultString2 = getSubArray(byteArray, 3, 12);
        //密码
        String resultString3 = getSubArray(byteArray, 13, 16);
        //功能码
        String resultString4 = getSubArray(byteArray, 17, 18);
        //报文上下行标识及长度
        String resultString5 = getSubArray(byteArray, 19, 22);
        //报文正文
        String resultString6 = getMiddleArray(byteArray, (byte) 2, (byte) 3);
        //2 根据协议组装返回对象
        return null;
    }


    public void calculate(List<ParsedData> parse, Device device) {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("VT", 1);
        context.put("2", 2);
        context.put("SIG", 2);
        context.put("SI", 3);
        String express = "VT+SIG/2-SI";
        Object r = null;
        try {
            r = runner.execute(express, context, null, true, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(r);

    }


/*    public static void main(String[] args) {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<>();
        context.put("VT", "1");
        context.put("f2", 0.5);
        context.put("f", 0.05);
        context.put("2f", 0.5);
        context.put("SIG",30.0);
        context.put("SI", 3);
        String express = "VT+sin(radian(SIG))";
        Object r = null;
        try {
            r = ExpressRunnerPack.getInstance().getExpressRunner().execute(express, context, null, true, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(r);
    }*/

    @Override
    public ParsedData parse(String deviceCode, String message) {
        return null;
    }

    @Override
    public List<MatchedDataDto> matching(ParsedData parsedData) {
        return null;
    }

    @Override
    public List<CalculatedDataDto> calculate(List<MatchedDataDto> matchedDataDtoList) {
        return null;
    }

    @Override
    public void transmit(List<CalculatedDataDto> calculatedDataDtoList) {
    }

}
