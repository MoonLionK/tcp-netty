package chc.dts.mybatis.generate;

import chc.dts.mybatis.core.dataobject.BaseDO;
import chc.dts.mybatis.core.mapper.BaseMapperX;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * mybatisPlus 代码生成器
 *
 * @author xgy
 */
public class CodeGenerate {
    public static void main(String[] args) {
        HashMap<OutputFile, String> path = new HashMap<>();
        String basePath = "D:\\Project\\backend\\dts-all\\dts-all-api\\src\\main\\";
        String basePath1 = basePath + "java\\chc\\dts\\api\\";
        path.put(OutputFile.entity, basePath1 + "entity");
        path.put(OutputFile.mapper, basePath1 + "dao");
        path.put(OutputFile.service, basePath1 + "service");
        path.put(OutputFile.serviceImpl, basePath1 + "service\\impl");
        path.put(OutputFile.controller, basePath1 + "controller");
        String basePath2 = basePath + "resources\\";
        path.put(OutputFile.xml, basePath2 + "mapper");


        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/data?serverTimezone=Asia/Shanghai", "root", "123456")
                .globalConfig(builder ->
                        builder.author("xgy") // 设置作者
                                .enableSwagger() // 开启 swagger 模式
                                .outputDir(basePath)
                                .disableOpenDir()
                                .enableSpringdoc())
                .packageConfig(builder -> {
                    builder.parent("chc.dts.api")// 设置父包名
                            .controller("controller")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("dao")
                            .pathInfo(path); // 设置mapperXml生成路径
                })
                // 设置需要生成的表名
                .strategyConfig(builder -> builder.addInclude("sensor_calculate_template")
                        .addTablePrefix("system_")// 设置过滤表前缀
                        // 实体文件覆盖
                        .entityBuilder().enableFileOverride()
                        .superClass(BaseDO.class)
                        .addIgnoreColumns("create_time", "update_time")
                        .enableLombok()
                        .enableRemoveIsPrefix()
                        .enableChainModel()
                        .enableTableFieldAnnotation()
                        // Mapper文件覆盖
                        .mapperBuilder()
                        .mapperAnnotation(Mapper.class)
                        .superClass(BaseMapperX.class)
                        // Service文件覆盖
                        .serviceBuilder()
                        // Controller文件覆盖
                        .controllerBuilder()
                        .enableRestStyle())
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
