package chc.dts.data.util;

/**
 * 数据解析工具类
 * @author xgy
 * @date 2024/5/15 14:33
 */
public class ParseUtil {
    // 获取两个固定元素中间的子数组
    public static String getMiddleArray(byte[] originalArray, byte firstElement, byte secondElement) {
        int firstIndex = -1;
        int secondIndex = -1;

        // 找到两个固定元素的索引
        for (int i = 0; i < originalArray.length; i++) {
            if (originalArray[i] == firstElement) {
                firstIndex = i;
            } else if (originalArray[i] == secondElement) {
                secondIndex = i;
            }

            if (firstIndex != -1 && secondIndex != -1) {
                break;
            }
        }

        if (firstIndex == -1 || secondIndex == -1 || firstIndex >= secondIndex) {
            throw new IllegalArgumentException("Invalid elements");
        }

        // 计算新数组长度
        int length = secondIndex - firstIndex - 1;
        byte[] middleArray = new byte[length];

        // 截取两个固定元素中间的部分
        for (int i = 0; i < length; i++) {
            middleArray[i] = originalArray[firstIndex + 1 + i];
        }

        return new String(middleArray);
    }

    // 获取指定范围的子数组
    public static String getSubArray(byte[] originalArray, int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= originalArray.length || startIndex > endIndex) {
            throw new IllegalArgumentException("Invalid start or end index");
        }

        int length = endIndex - startIndex + 1;
        byte[] subArray = new byte[length];

        for (int i = 0; i < length; i++) {
            subArray[i] = originalArray[startIndex + i];
        }

        return new String(subArray);
    }

    // 将十六进制字符串转换为字节数组的方法
    public static byte[] hexStringToByteArray(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return byteArray;
    }
}
