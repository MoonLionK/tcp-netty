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
 * 连接信息表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("connect_info")
@Schema(name = "ConnectInfo", description = "连接信息表")
public class ConnectInfo extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "ip地址")
    @TableField("ip")
    private String ip;

    @Schema(description = "远程端口")
    @TableField("remote_port")
    private Integer remotePort;

    @Schema(description = "通道id")
    @TableField("channel_id")
    private Long channelId;

    @Schema(description = "状态 0:已连接 1:未连接")
    @TableField("status")
    private Integer status;

    @Schema(description = "是否自启动0:是 1:否")
    @TableField("auto_connect")
    private Boolean autoConnect;
}
