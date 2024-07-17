package chc.dts.receive.controller.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xgy
 * @date 2024/7/12 19:14
 */
@Data
public class ClientUpdateReq implements Serializable {

    @NotNull
    @Schema(description = "id")
    private Long id;

    @Schema(description = "ip地址")
    private String ip;

    @Schema(description = "端口")
    private Integer port;

    @Schema(description = "远程端口")
    private Integer remotePort;

    @Schema(description = "是否自启动0:是 1:否")
    private Boolean autoConnect;

}
