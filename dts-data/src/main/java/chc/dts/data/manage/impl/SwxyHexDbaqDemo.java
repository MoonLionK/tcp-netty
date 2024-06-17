package chc.dts.data.manage.impl;

import chc.dts.common.core.KeyValue;
import chc.dts.common.util.string.StrUtils;
import chc.dts.data.manage.CommonPaseImpl;
import chc.dts.data.pojo.dto.CalculatedDataDto;
import chc.dts.data.pojo.dto.MatchedDataDto;
import chc.dts.data.pojo.dto.ParsedData;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import static chc.dts.common.util.string.StrUtils.hexStringToByteArray;

/**
 * 水文协议大坝安全监测解析方法
 *
 * @author xgy
 */
@Slf4j
@Service
public class SwxyHexDbaqDemo extends CommonPaseImpl {


    private static KeyValue<String, String> setParsedData(String key, String value) {
        KeyValue<String, String> keyValue = new KeyValue<>();
        keyValue.setKey(key);
        keyValue.setValue(value);
        return keyValue;
    }

    @Override
    public ParsedData parse(String deviceCode, String message) {
        byte[] data = hexStringToByteArray(message);
        List<KeyValue<String, String>> values = new ArrayList<>();
        String v1 = String.valueOf(data[2]);//中心站地址
        String v2 = bytesToHex(data, 3, 5);//遥测站地址
        String v3 = bytesToHex(data, 8, 2);//密码
        String v4 = Integer.toHexString(data[10]);//功能码
        String v5 = String.format("%4s", Integer.toBinaryString(data[11] & 0xFF)).replace(' ', '0').substring(0, 4);//报文上行标识符
        String v6 = String.valueOf((data[12] & 0xFF) + ((data[11] & 0x0F) << 8));//报文长度
        if (Integer.parseInt(v6) <= 8) {
            return null;
        }
        byte[] lsBytes = new byte[2];
        System.arraycopy(data, 14, lsBytes, 0, 2);
        String v7 = String.valueOf(ByteBuffer.wrap(lsBytes).order(ByteOrder.BIG_ENDIAN).getShort());//流水号
        String v8 = bytesToHex(data, 16, 6);//发报时间
        ArrayList<String> valueLists = Lists.newArrayList(v1, v2, v3, v4, v5, v6, v7, v8);
        //构建解析后的数据对象
        for (int i = 0; i < 8; i++) {
            String key = "P" + (i + 1);
            KeyValue<String, String> parsedData = setParsedData(StrUtils.convertToUpperCase(key), valueLists.get(i));
            values.add(parsedData);
        }
        // 截取value值报文
        int length = Integer.parseInt(v6) - 8;
        byte[] btBytes = new byte[length];
        System.arraycopy(data, 22, btBytes, 0, length);
        for (int i = 0; i < btBytes.length; i++) {
            if (btBytes[i] == (byte) 0xF1 && btBytes[i + 1] == (byte) 0xF1) {
                String v9 = bytesToHex(btBytes, i + 2, 5);
                String key1 = "P9";
                // 创建并赋值 Original 对象
                KeyValue<String, String> parsedDataFF1 = setParsedData(StrUtils.convertToUpperCase(key1), v9);
                values.add(parsedDataFF1);
                i += 7;
                if (i < btBytes.length) {
                    //String v10 = String.valueOf(btBytes[i]);
                    String v10 = Integer.toHexString(data[10]);
                    String key2 = "P10";
                    // 创建并赋值 Original 对象
                    KeyValue<String, String> parsedDataFF2 = setParsedData(StrUtils.convertToUpperCase(key2), v10);
                    values.add(parsedDataFF2);
                }
                continue;
            }
            if (btBytes[i] == (byte) 0xF0 && btBytes[i + 1] == (byte) 0xF0) {
                String v11 = bytesToHex(btBytes, i + 2, 5);
                i += 6;

                String key1 = "P11";
                // 创建并赋值 Original 对象
                KeyValue<String, String> parsedDataF01 = setParsedData(StrUtils.convertToUpperCase(key1), v11);
                values.add(parsedDataF01);

                continue; // 继续下一轮循环
            }
            if (btBytes[i] == (byte) 0xFF) {
                byte bt = btBytes[i + 1];
                int btLength;
                int ptLength;
                String dataX = String.format("%8s", Integer.toBinaryString(btBytes[i + 2] & 0xFF)).replace(' ', '0');
                btLength = Integer.parseInt(dataX.substring(0, 5), 2);
                ptLength = Integer.parseInt(dataX.substring(5, 8), 2);
                String value = getBcdString(btBytes, i + 3, btLength, true, ptLength);
                String keyValue = String.format("%02X", bt);

                if (keyValue.length() == 1) {
                    keyValue = "0" + keyValue;
                }
                String key1 = "PFF" + keyValue;

                // 创建并赋值 Original 对象
                KeyValue<String, String> parsedDataF01 = setParsedData(StrUtils.convertToUpperCase(key1), value);
                values.add(parsedDataF01);

                i += btLength + 2;
                continue; // 继续下一轮循环
            }
            if (btBytes[i] == (byte) 0xCD && btBytes[i + 1] == (byte) 0x48) {
                byte bt = btBytes[i + 2];
                int btLength = 4;
                int ptLength = 1;
                String value = getDCBA(btBytes, i + 3, btLength, ptLength);
                String value1 = getDCBA(btBytes, i + 7, btLength, 2);
                String keyValue = String.format("%02X", bt);

                if (keyValue.length() == 1) {
                    keyValue = "0" + keyValue;
                }
                String key1 = "PCD" + keyValue;

                KeyValue<String, String> parsedDataF01 = setParsedData(StrUtils.convertToUpperCase(key1), value + "," + value1);
                values.add(parsedDataF01);

                i += btLength * 2 + 2;
                continue;
            }
            if (btBytes[i] == (byte) 0xCD && btBytes[i + 1] == (byte) 0x28) {
                byte bt = btBytes[i + 2];
                int btLength = 4;
                int ptLength = 4;
                String value = getDCBA(btBytes, i + 3, btLength, ptLength);
                String keyValue = String.format("%02X", bt);

                if (keyValue.length() == 1) {
                    keyValue = "P0" + keyValue;
                }
                String key1 = "CD" + keyValue;
                KeyValue<String, String> parsedDataF01 = setParsedData(StrUtils.convertToUpperCase(key1), value);
                values.add(parsedDataF01);

                i += btLength + 2;
                continue;
            }
            byte bt = btBytes[i];
            int btLength;
            int ptLength;
            String data2 = String.format("%8s", Integer.toBinaryString(btBytes[i + 1] & 0xFF)).replace(' ', '0');
            btLength = Integer.parseInt(data2.substring(0, 5), 2);
            ptLength = Integer.parseInt(data2.substring(5, 8), 2);
            String value = getBcdString(btBytes, i + 2, btLength, true, ptLength);
            String key1 = "P" + Integer.toHexString(bt & 0xFF);
            KeyValue<String, String> parsedDataF01 = setParsedData(StrUtils.convertToUpperCase(key1), value);
            values.add(parsedDataF01);
            i += btLength + 1;
        }
        return new ParsedData(values, deviceCode);
    }

    private static String getBcdString(byte[] bytes, int startIndex, int length, boolean removeHeadZero, int pointPosition) {
        String value = bytesToHexString(bytes, startIndex, length).replace("-", "");
        boolean isNegative = false;

        if ("FFFFFFFF".equals(value)) {
            value = "NaN";
        } else if (value.startsWith("FF")) {
            isNegative = true;
            value = value.replace("FF", "");
        }

        if (value.isEmpty()) {
            return null;
        }

        value = insertDecimalPoint(value, pointPosition);

        if (removeHeadZero) {
            value = value.replaceFirst("^0+", "");
            if (value.startsWith(".")) {
                value = "0" + value;
            }
        }

        if (isNegative) {
            value = "-" + value;
        }

        value = value.replaceAll("\\.$", "");
        return value;
    }

    private static String bytesToHexString(byte[] bytes, int startIndex, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = startIndex; i < startIndex + length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    private static String insertDecimalPoint(String value, int pointPosition) {
        if (pointPosition > 0) {
            value = value.substring(0, value.length() - pointPosition) + "." + value.substring(value.length() - pointPosition);
        }
        return value;
    }

    /**
     * ByteBuffer 类来将字节数组转换为浮点数，类似于C#中的 BitConverter.ToSingle 方法。然后，使用 String.format 方法来格式化浮点数的输出，类似于C#中的字符串插值
     */
    private static String getDCBA(byte[] bytes, int startIndex, int length, int pointPosition) {
        byte[] data = new byte[length];
        System.arraycopy(bytes, startIndex, data, 0, length);
        float value = ByteBuffer.wrap(data).getFloat();
        String formatString = "F" + pointPosition;
        return String.format("%" + formatString, value);
    }

    /**
     * 将字节数组中的指定范围转换为十六进制字符串
     */
    public static String bytesToHex(byte[] bytes, int offset, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
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
