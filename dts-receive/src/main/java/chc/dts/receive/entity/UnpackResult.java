package chc.dts.receive.entity;

import lombok.Data;

/**
 * @author xgy
 * @date 2024/05/22
 */
@Data
public class UnpackResult {

    /**
     * 开始位置
     */
    Integer index;

    /**
     * 长度
     */
    Integer length;


    /**
     * @return 拆包结果是否合法
     */
    public boolean isLegal(){
        return index!=null&&index>=0&&length!=null&&length>=0;
    }
}
