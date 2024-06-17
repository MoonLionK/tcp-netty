package chc.dts.api.dao;

import chc.dts.api.entity.InternalResource;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import com.baomidou.dynamic.datasource.annotation.DS;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 国际化资源表 Mapper 接口
 * </p>
 *
 * @author fyh
 * @since 2024-06-13
 */
@Mapper
@DS("system")
public interface InternalResourceMapper extends BaseMapperX<InternalResource> {

    /**
     * 从数据库中获取对应语言
     *
     * @param key  关键字
     * @param lang 语言
     * @return 语言切换结果
     */
    String getResourceByKeyAndLang(String key, String lang);

    /**
     * 获取所有语言资源
     *
     * @return 语言资源
     */
    List<InternalResource> getResourceApp();
}
