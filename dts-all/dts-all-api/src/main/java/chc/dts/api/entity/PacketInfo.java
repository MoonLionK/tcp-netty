package chc.dts.api.entity;

import chc.dts.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备拆包方法表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("packet_info")
@Schema(name = "PacketInfo", description = "设备拆包方法表")
public class PacketInfo extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "设备编码")
    @TableField("device_code")
    private String deviceCode;

    @Schema(description = "拆包方式的类型：1.固定包长度模式 2.包头+包尾模式 3.不定长长度的包头位置的模式")
    @TableField("type")
    private Integer type;

    @Schema(description = "包头的标识符")
    @TableField("head")
    private byte[] head;

    @Schema(description = "包尾的标识符-1")
    @TableField("tail_first")
    private byte[] tailFirst;

    @Schema(description = "包尾的标识符-2")
    @TableField("tail_second")
    private byte[] tailSecond;

    @Schema(description = "包尾的标识符-3")
    @TableField("tail_third")
    private byte[] tailThird;

    @Schema(description = "包头的忽略的字节偏移量，从0开始")
    @TableField("offset")
    private Integer offset;

    @Schema(description = "长度位对应的开始下标，从0开始")
    @TableField("length_offset")
    private Integer lengthOffset;

    @Schema(description = "包的长度")
    @TableField("length")
    private Integer length;

    @Schema(description = "包尾后字节数")
    @TableField("end_length")
    private Integer endLength;

    @Schema(description = "长度位的长度")
    @TableField("head_packet_length")
    private Integer headPacketLength;

    @Schema(description = "需要添加的长度")
    @TableField("add_length")
    private Integer addLength;

    @Schema(description = "字节转换类型")
    @TableField("method_type")
    private Integer methodType;
}
