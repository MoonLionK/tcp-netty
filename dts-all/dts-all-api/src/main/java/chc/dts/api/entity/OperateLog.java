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
 * 操作日志记录 V2 版本
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_operate_log")
@Schema(name = "OperateLog", description = "操作日志记录 V2 版本")
public class OperateLog extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "日志主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "链路追踪编号")
    @TableField("trace_id")
    private String traceId;

    @Schema(description = "用户编号")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "用户类型")
    @TableField("user_type")
    private Byte userType;

    @Schema(description = "操作模块类型")
    @TableField("type")
    private String type;

    @Schema(description = "操作名")
    @TableField("sub_type")
    private String subType;

    @Schema(description = "操作数据模块编号")
    @TableField("biz_id")
    private Long bizId;

    @Schema(description = "操作内容")
    @TableField("action")
    private String action;

    @Schema(description = "拓展字段")
    @TableField("extra")
    private String extra;

    @Schema(description = "请求方法名")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "请求地址")
    @TableField("request_url")
    private String requestUrl;

    @Schema(description = "用户 IP")
    @TableField("user_ip")
    private String userIp;

    @Schema(description = "浏览器 UA")
    @TableField("user_agent")
    private String userAgent;

    @Schema(description = "租户编号")
    @TableField("tenant_id")
    private Long tenantId;
}
