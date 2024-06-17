package chc.dts.api.service.impl;

import chc.dts.api.dao.ProjectInfoMapper;
import chc.dts.api.entity.ProjectInfo;
import chc.dts.api.service.IProjectInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目信息表 服务实现类
 * </p>
 *
 * @author xgy
 * @since 2024-05-29
 */
@Service
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo> implements IProjectInfoService {

}
