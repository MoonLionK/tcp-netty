package chc.dts.api.entity;

import chc.dts.mybatis.core.dataobject.BaseDO;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 计算后数据记录表
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("calculated_data")
@Schema(name = "CalculatedData", description = "计算后数据记录表")
public class CalculatedData extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "传感器Code")
    @TableField("sensor_code")
    private String sensorCode;

    @Schema(description = "匹配成功的数据")
    @TableField(value = "calculated_data",typeHandler = JacksonTypeHandler.class)
    private JSONObject calculatedData;
}
