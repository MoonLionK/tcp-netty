package com.cn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cn.common.pojo.BaseDO;
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
 * @since 2024-04-29
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

    @Schema(description = "协议id")
    @TableField("protocol_id")
    private Long protocolId;

    @Schema(description = "ip地址")
    @TableField("ip")
    private String ip;

    @Schema(description = "端口")
    @TableField("port")
    private String port;

    @Schema(description = "设备名称")
    @TableField("device_name")
    private String deviceName;

    @Schema(description = "设备类型")
    @TableField("device_type")
    private String deviceType;

    @Schema(description = "设备描述")
    @TableField("device_description")
    private String deviceDescription;

    @Schema(description = "设备状态： 0:启用 1:禁用")
    @TableField("device_status")
    private Integer deviceStatus;

    @Schema(description = "tcp通信模式,server || client")
    @TableField("tcp_model")
    private String tcpModel;

    @Schema(description = "串口号")
    @TableField("serial_port")
    private String serialPort;

    @Schema(description = "波特率")
    @TableField("baud_rate")
    private String baudRate;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
