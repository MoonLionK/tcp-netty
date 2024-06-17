package chc.dts.data.manage.impl;

import chc.dts.common.core.KeyValue;
import chc.dts.common.util.string.StrUtils;
import chc.dts.data.manage.CommonPaseImpl;
import chc.dts.data.pojo.dto.CalculatedDataDto;
import chc.dts.data.pojo.dto.MatchedDataDto;
import chc.dts.data.pojo.dto.ParsedData;
import chc.dts.data.util.CrcTool;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static chc.dts.common.util.string.StrUtils.hexStringToByteArray;
import static java.lang.String.format;
import static java.lang.String.valueOf;


/**
 * @author fyh
 */
@Slf4j
@Service
public class NjgnImpl extends CommonPaseImpl {

    private static final int CHECK_LENGTH =2;

    public static void main(String[] args) {
        String hexString = "FF16080E004B11014463A7BCB00364F8538340044400001332B064007CB045B864CDCCE041B16466CAB145B9649A99D541B2640000C07FBA640000C07FB3640000C07FBB640000C07FDDA1";
        ParsedData parsedData = new NjgnImpl().parse("test",hexString);
        String jsonString = JSONArray.toJSONString(parsedData);
        System.out.println("jsonString = " + jsonString);
    }




    private static String getValue(byte[] data, int flagIndex, int[] len) {
        String result = null;
        int length = (data[flagIndex + 1] & 0x1F);

        // 取后5位然后加上2位标识
        len[0] = length + 2;
        int type = (data[flagIndex + 1] & 0xE0)>>5;
        switch (type) {
            case 0:
                result = Integer.toString(data[flagIndex + 2]);
                break;
            case 1:
                result = valueOf(CrcTool.getUint16(data, flagIndex + 2, false));
                break;
            case 2:
                result = valueOf(CrcTool.getUint32(data, flagIndex + 2, false));
                break;
            case 3:
                result = valueOf(CrcTool.getFloat(data, flagIndex + 2, false));
                break;
            case 4:
                result = new String(data, flagIndex + 2, length);
                break;
            default:
        }
        if (data[flagIndex + 1] == 0x60) {
            return null;
        } else {
            return result;
        }
    }


    @Override
    public ParsedData parse(String deviceCode, String message) {
        byte[] data = hexStringToByteArray(message);
        List<KeyValue<String, String>> values = new ArrayList<>();

        byte[] lrc = CrcTool.crc16(data, 0, data.length-2 );
        if (lrc[0] != data[data.length - CHECK_LENGTH] || lrc[1] != data[data.length - 1]) {
            log.error(format("CRC Check Error calc:[%d][%d] rec:[%d][%d]",
                    lrc[0], lrc[1], data[data.length - 2], data[data.length - 1]));
            return null;
        }
        // 设备型号 赋值 key=1
        int deviceId = data[1];
        // 设备地址 赋值 key=2
        int  deviceType= (data[2] << 8) | data[3];
        // 功能码 赋值 key=3
        int funCode = data[6];
        values.add(new KeyValue<>("deviceType",valueOf(deviceType)));
        values.add(new KeyValue<>("deviceId",valueOf(deviceId)));
        values.add(new KeyValue<>("funCode",valueOf(funCode)));


        int step ;
        int[] lenArray = {0};
        for (int i = 7; i < data.length - 2; i = i + step) {
            step = 0;
            String v = getValue(data, i, lenArray);
            step += lenArray[0];
            values.add(new KeyValue<>("P"+ StrUtils.bytesToHex(data[i]),v));
        }

        return new ParsedData(values,deviceCode);
    }

    @Override
    public List<MatchedDataDto> matching(ParsedData parsedData) {
        return super.matching(parsedData);
    }

    @Override
    public List<CalculatedDataDto> calculate(List<MatchedDataDto> matchedDataDtoList) {
        return super.calculate(matchedDataDtoList);
    }

    @Override
    public void transmit(List<CalculatedDataDto> calculatedDataDtoList) {

    }
}
