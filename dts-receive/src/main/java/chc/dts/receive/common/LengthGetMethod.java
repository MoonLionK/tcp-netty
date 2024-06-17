package chc.dts.receive.common;

/**
 * 解析长度方法类
 *
 * @author xgy
 * @date 2024/05/20
 */
public class LengthGetMethod {

    public static int getLength(Integer name, byte[] in) {
        switch (name) {
            case 1:
                return njgnReceiveDataLength(in);

            case 2:
                return swgyReceiveDataLength(in);
            default:
                return -1;
        }


    }

    /**
     * in[0]为高八位，& 0x0F为获取原子节的低四位，* 256为向左移动八位
     * in[1] & 0xFF获取无符号的8位整数
     *
     * @param in 长度位
     * @return 解析后的长度位
     */
    private static int swgyReceiveDataLength(byte[] in) {
        int size = in[1] & 0xFF;
        size += (in[0] & 0x0F) * 256;
        return size;
    }


    /**
     * 转换为无符号整数后相加
     * << (8 * (bytes.length - 1 - i))为左移对应位置，高字节在高位，低字节在低位
     *
     * @param bytes 长度位
     * @return 长度
     */
    public static int njgnReceiveDataLength(byte[] bytes) {
        int ret = 0;
        for (int i = 0; i < bytes.length; i++) {
            ret |= (bytes[i] & 0xFF) << (8 * (bytes.length - 1 - i));
        }
        if (ret > 1024) {
            ret = 0;
        }
        return ret;
    }
}
