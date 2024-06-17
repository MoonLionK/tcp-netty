package chc.dts.receive.entity;

import lombok.Data;

/**
 * @author xgy
 * @date 2024/05/06
 */
@Data
public class PacketData {
    private int type;
    private byte[] data;

    public PacketData(int type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public PacketData() {
    }
}

