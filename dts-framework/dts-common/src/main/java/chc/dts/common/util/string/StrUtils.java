package chc.dts.common.util.string;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字符串工具类
 *
 * @author xgy
 */
public class StrUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULLSTR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 将字节数组转换为十六进制字符串
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte bytes) {
        StringBuilder hexString = new StringBuilder();
        hexString.append(String.format("%02X", bytes));
        return hexString.toString();
    }

    /**
     * 将16进制字符串转换为byte数组
      */
    public static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULLSTR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULLSTR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    public static String maxLength(CharSequence str, int maxLength) {
        return StrUtil.maxLength(str, maxLength - 3); // -3 的原因，是该方法会补充 ... 恰好
    }

    /**
     * 给定字符串是否以任何一个字符串开始
     * 给定字符串和数组为空都返回 false
     *
     * @param str      给定字符串
     * @param prefixes 需要检测的开始字符串
     * @since 3.0.6
     */
    public static boolean startWithAny(String str, Collection<String> prefixes) {
        if (StrUtil.isEmpty(str) || ArrayUtil.isEmpty(prefixes)) {
            return false;
        }

        for (CharSequence suffix : prefixes) {
            if (StrUtil.startWith(str, suffix, false)) {
                return true;
            }
        }
        return false;
    }

    public static List<Long> splitToLong(String value, CharSequence separator) {
        long[] longs = StrUtil.splitToLong(value, separator);
        return Arrays.stream(longs).boxed().collect(Collectors.toList());
    }

    public static Set<Long> splitToLongSet(String value) {
        return splitToLongSet(value, StrPool.COMMA);
    }

    public static Set<Long> splitToLongSet(String value, CharSequence separator) {
        long[] longs = StrUtil.splitToLong(value, separator);
        return Arrays.stream(longs).boxed().collect(Collectors.toSet());
    }

    public static List<Integer> splitToInteger(String value, CharSequence separator) {
        int[] integers = StrUtil.splitToInt(value, separator);
        return Arrays.stream(integers).boxed().collect(Collectors.toList());
    }

    /**
     * 移除字符串中，包含指定字符串的行
     *
     * @param content  字符串
     * @param sequence 包含的字符串
     * @return 移除后的字符串
     */
    public static String removeLineContains(String content, String sequence) {
        if (StrUtil.isEmpty(content) || StrUtil.isEmpty(sequence)) {
            return content;
        }
        return Arrays.stream(content.split("\n"))
                .filter(line -> !line.contains(sequence))
                .collect(Collectors.joining("\n"));
    }


    /**
     * 修改字符串为小写字符
     * @param input 输入的字符
     * @return 修改为小写后的字符
     */
    public static String convertToLowerCase(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 修改字符串为大写字符
     * @param input 输入的字符
     * @return 修改为大写后的字符
     */
    public static String convertToUpperCase(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
