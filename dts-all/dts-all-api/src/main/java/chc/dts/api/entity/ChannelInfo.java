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
 * 通道信息表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("channel_info")
@Schema(name = "ChannelInfo", description = "通道信息表")
public class ChannelInfo extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "ip地址")
    @TableField("ip")
    private String ip;

    @Schema(description = "连接端口")
    @TableField("port")
    private Integer port;

    @Schema(description = "通道编码")
    @TableField("channel_code")
    private String channelCode;

    @Schema(description = "串口号")
    @TableField("serial_port")
    private String serialPort;

    @Schema(description = "串口-波特率")
    @TableField("serial_rate")
    private String serialRate;

    @Schema(description = "串口-数据位")
    @TableField("serial_data_bits")
    private String serialDataBits;

    @Schema(description = "串口-停止位")
    @TableField("serial_stop_bits")
    private String serialStopBits;
}
