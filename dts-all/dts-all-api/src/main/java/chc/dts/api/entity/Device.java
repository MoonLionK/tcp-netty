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
 * 设备信息表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("device")
@Schema(name = "Device", description = "设备信息表")
public class Device extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "协议编码")
    @TableField("protocol_code")
    private String protocolCode;

    @Schema(description = "项目编码")
    @TableField("project_code")
    private String projectCode;

    @Schema(description = "通道编码")
    @TableField("channel_code")
    private String channelCode;

    @Schema(description = "设备名称")
    @TableField("device_name")
    private String deviceName;

    @Schema(description = "设备编码")
    @TableField("device_code")
    private String deviceCode;

    @Schema(description = "设备类型(根据不同的设备类型选择不同的解析和计算方式)")
    @TableField("device_type")
    private String deviceType;

    @Schema(description = "连接方式：0：Tcp，1：Serial，2：MQTT")
    @TableField("connect_type")
    private Integer connectType;

    @Schema(description = "报文采集方式：0：不采集；1：自动间隔采集；2：阈值采集，暂时不做")
    @TableField("collect_type")
    private Integer collectType;

    @Schema(description = "设备描述")
    @TableField("device_description")
    private String deviceDescription;

    @Schema(description = "设备状态： 0:启用 1:禁用")
    @TableField("device_status")
    private Integer deviceStatus;

    @Schema(description = "tcp通信模式,server || client")
    @TableField("tcp_model")
    private String tcpModel;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
