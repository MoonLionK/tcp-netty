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

import java.time.LocalDateTime;

/**
 * <p>
 * 系统异常日志
 * </p>
 *
 * @author xgy
 * @since 2024-04-19
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_api_error_log")
@Schema(name = "ApiErrorLog", description = "系统异常日志")
public class ApiErrorLog extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "链路追踪编号")
    @TableField("trace_id")
    private String traceId;

    @Schema(description = "用户编号")
    @TableField("user_id")
    private Integer userId;

    @Schema(description = "用户类型")
    @TableField("user_type")
    private Byte userType;

    @Schema(description = "应用名")
    @TableField("application_name")
    private String applicationName;

    @Schema(description = "请求方法名")
    @TableField("request_method")
    private String requestMethod;

    @Schema(description = "请求地址")
    @TableField("request_url")
    private String requestUrl;

    @Schema(description = "请求参数")
    @TableField("request_params")
    private String requestParams;

    @Schema(description = "用户 IP")
    @TableField("user_ip")
    private String userIp;

    @Schema(description = "浏览器 UA")
    @TableField("user_agent")
    private String userAgent;

    @Schema(description = "异常发生时间")
    @TableField("exception_time")
    private LocalDateTime exceptionTime;

    @Schema(description = "异常名")
    @TableField("exception_name")
    private String exceptionName;

    @Schema(description = "异常导致的消息")
    @TableField("exception_message")
    private String exceptionMessage;

    @Schema(description = "异常导致的根消息")
    @TableField("exception_root_cause_message")
    private String exceptionRootCauseMessage;

    @Schema(description = "异常的栈轨迹")
    @TableField("exception_stack_trace")
    private String exceptionStackTrace;

    @Schema(description = "异常发生的类全名")
    @TableField("exception_class_name")
    private String exceptionClassName;

    @Schema(description = "异常发生的类文件")
    @TableField("exception_file_name")
    private String exceptionFileName;

    @Schema(description = "异常发生的方法名")
    @TableField("exception_method_name")
    private String exceptionMethodName;

    @Schema(description = "异常发生的方法所在行")
    @TableField("exception_line_number")
    private Integer exceptionLineNumber;

    @Schema(description = "处理状态 0:未处理 1:已处理 2:已忽略")
    @TableField("process_status")
    private Integer processStatus;

    @Schema(description = "处理时间")
    @TableField("process_time")
    private LocalDateTime processTime;

    @Schema(description = "处理用户编号")
    @TableField("process_user_id")
    private Integer processUserId;
}
