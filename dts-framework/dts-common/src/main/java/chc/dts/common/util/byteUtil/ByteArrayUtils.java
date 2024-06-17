package chc.dts.common.util.byteUtil;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:xgy
 * @date:2024/05/10
 */
public class ByteArrayUtils {

    public static boolean containsSequential(byte[] a, byte[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0 || a.length < b.length) {
            return false;
        }
        for (int i = 0; i <= a.length - b.length; i++) {
            if (a[i] == b[0]) {
                int j;
                for (j = 1; j < b.length; j++) {
                    if (a[i + j] != b[j]) {
                        break;
                    }
                }
                if (j == b.length) {
                    return true;
                }
            }
        }
        return false;
    }


    public static int findStartIndex(byte[] superset, byte[] subset) {
        if (superset == null || subset == null || superset.length == 0 || subset.length == 0 || superset.length < subset.length) {
            return -1;
        }

        for (int i = 0; i <= superset.length - subset.length; i++) {
            if (superset[i] == subset[0]) {
                int j;
                for (j = 1; j < subset.length; j++) {
                    if (superset[i + j] != subset[j]) {
                        break;
                    }
                }
                if (j == subset.length) {
                    // 返回 subset 在 superset 中的开始位置
                    return i;
                }
            }
        }
        // 如果未找到，返回 -1
        return -1;
    }

    public static int findEndIndex(byte[] a, byte[] b) {
        if (a == null || b == null || a.length == 0 || b.length == 0 || a.length < b.length) {
            return -1;
        }

        for (int i = 0; i <= a.length - b.length; i++) {
            if (a[i] == b[0]) {
                int j;
                for (j = 1; j < b.length; j++) {
                    if (a[i + j] != b[j]) {
                        break;
                    }
                }
                if (j == b.length) {
                    // 返回 b 在 a 中的结束位置
                    return i + b.length - 1;
                }
            }
        }
        // 如果未找到，返回 -1
        return -1;
    }

    public static byte[] removeElements(byte[] array, int numToRemove) {
        if (array == null || numToRemove <= 0 || numToRemove >= array.length) {
            // 返回空数组
            return new byte[0];
        }

        byte[] result = new byte[array.length - numToRemove];
        System.arraycopy(array, numToRemove, result, 0, result.length);

        return result;
    }


    /**
     * @param array 输入数组
     * @param a 开始位置
     * @param b 结束位置
     * @return 截取后的byte数组
     */
    public static byte[] subArray(byte[] array, int a, int b) {
        if (array == null || a < 0 || b < a || b >= array.length) {
            // 如果数组为 null，或者 a 小于 0，或者 b 小于 a，或者 b 大于等于数组长度，则返回空数组
            return new byte[0];
        }

        // 计算截取元素的长度
        int length = b - a + 1;
        byte[] result = new byte[length];
        System.arraycopy(array, a, result, 0, length);

        return result;
    }


    /** 去除尾0
     * @param head 二进制数据
     * @return 去除尾0后的二进制数组
     */
    public static byte[] removeZero(byte[] head) {
        // 找到最后一个不为0的字节的索引
        int lastIndex = head.length - 1;
        while (lastIndex >= 0 && head[lastIndex] == 0) {
            lastIndex--;
        }

        // 如果找到了不为0的字节，则返回该字节之前的所有字节
        if (lastIndex >= 0) {
            byte[] result = new byte[lastIndex + 1];
            System.arraycopy(head, 0, result, 0, lastIndex + 1);
            return result;
        } else {
            // 如果所有字节都为0，则返回一个空数组
            return new byte[0];
        }
    }

    /**
     * 获取缓存当中的所有byte值，不改变原有的buffer内容
     * @param byteBuf 换从
     * @return byte数组
     */
    public static byte[] getByteArrayFromByteBuf(ByteBuf byteBuf) {
        // 创建一个与原始ByteBuf共享内部数据但具有独立索引的新ByteBuf
        ByteBuf duplicateByteBuf = byteBuf.duplicate();

        // 创建一个新的byte数组，大小为ByteBuf可读字节数
        byte[] bytes = new byte[duplicateByteBuf.readableBytes()];

        // 从复制的ByteBuf中读取数据到目标字节数组
        duplicateByteBuf.readBytes(bytes);

        return bytes;
    }


    /**
     * 将十六进制字符串转换为字节数组
     * @param hexString hex 編碼
     * @return 二进制数组
     */
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    /**
     * 将十六进制字符串转换为hex编码
     * @param byteArray byte数组
     * @return 转换后的编码，String
     */
    public static List<String> convertByteArrayToHex(byte[] byteArray) {
        List<String> hexList = new ArrayList<>();
        for (byte b : byteArray) {
            String hex = String.format("%02X", b);
            hexList.add(hex);
        }
        return hexList;
    }

    /**
     * @param in 父级
     * @param bytes 子集集合
     * @return 子集集合当中第一个在父级出现的位置
     */
    public static int findEndIndex(byte[] in, List<byte[]> bytes) {
        for (byte[] aByte : bytes) {
            int endIndex = findEndIndex(in, aByte);
            if (endIndex != -1) {
                return endIndex;
            }
        }
        return -1;
    }

}
