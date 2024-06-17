package chc.dts.api.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xgy
 * @date 2024/5/30 9:38
 */
@Mapper
public interface CodeGenMapper {
    /**
     * 根据表名获取自增主键的下一位
     *
     * @param tableName 表名
     * @return 自增主键的下一位
     */
    Long getNextId(@Param("tableName") String tableName);
}
