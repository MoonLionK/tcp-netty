package chc.dts.data.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author fyh
 */
public class CrcTool {

    private static final int[] TABLE = new int[256];

    static {
        // 预先计算 CRC 表
        for (int i = 0; i < 256; i++) {
            int crc = i;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x0001) != 0) {
                    crc = (crc >>> 1) ^ 0xA001;
                } else {
                    crc = crc >>> 1;
                }
            }
            TABLE[i] = crc;
        }
    }

    public static byte[] crc16(byte[] buf, int offset, int len) {

        byte[] crcBytes = new byte[2];
        int crc = 0xFFFF;
        for (int i = offset; i <len ; i++) {
            crc = (crc >>> 8) ^ TABLE[(crc ^ buf[i]) & 0xFF];
        }
        // 低字节
        crcBytes[0] = (byte) (crc & 0xFF);
        // 高字节
        crcBytes[1] = (byte) ((crc >>> 8) & 0xFF);
        return crcBytes;

    }

    public static int getUint16(byte[] bytes, int offset, boolean isLittleEndian) {
        if (isLittleEndian) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, 2);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getShort();
        } else {
            ByteBuffer buffer = ByteBuffer.wrap(getReverseByte(bytes, offset, 2));
            buffer.order(ByteOrder.BIG_ENDIAN);
            return buffer.getShort();
        }
    }

    public static float getFloat(byte[] bytes, int offset, boolean isLittleEndian) {
        if (isLittleEndian) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, 4);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getFloat();
        } else {
            ByteBuffer buffer = ByteBuffer.wrap(getReverseByte(bytes, offset, 4));
            buffer.order(ByteOrder.BIG_ENDIAN);
            return buffer.getFloat();
        }
    }



    public static byte[] getReverseByte(byte[] bytes, int offset, int length) {
        byte[] reversedBytes = new byte[length];
        for (int i = 0; i < length; i++) {
            reversedBytes[i] = bytes[offset + length - 1 - i];
        }
        return reversedBytes;
    }

    public static long getUint32(byte[] bytes, int offset, boolean isLittleEndian) {
        if (isLittleEndian) {
            ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, 4).order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getInt() & 0xFFFFFFFFL;
        } else {
            byte[] reversedBytes = getReverseByte(bytes, offset, 4);
            ByteBuffer buffer = ByteBuffer.wrap(reversedBytes).order(ByteOrder.LITTLE_ENDIAN);
            return buffer.getInt() & 0xFFFFFFFFL;
        }
    }


}
