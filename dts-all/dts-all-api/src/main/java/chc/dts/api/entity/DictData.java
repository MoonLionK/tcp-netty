package chc.dts.api.entity;


import chc.dts.common.enums.CommonStatusEnum;
import chc.dts.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表
 *
 * @author dts
 */
@TableName("system_dict_data")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictData extends BaseDO {

    /**
     * 字典数据编号
     */
    @TableId
    private Long id;
    /**
     * 字典排序
     */
    private Integer sort;
    /**
     * 字典标签
     */
    private String label;
    /**
     * 字典值
     */
    private String value;
    /**
     * 字典类型
     * <p>
     * 冗余 {@link DictData#getDictType()}
     */
    private String dictType;
    /**
     * 状态
     * <p>
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 颜色类型
     * <p>
     * 对应到 element-ui 为 default、primary、success、info、warning、danger
     */
    private String colorType;
    /**
     * css 样式
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String cssClass;
    /**
     * 备注
     */
    private String remark;

}
