package pers.catigeart.notice;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

/**
 * @author Catigeart
 * GitHub: https://github.com/Catigeart
 * Time: 2022/5/18 10:44
 */
public class CodeGen {

    /**
     * 执行 run
     */
    public static void main(String[] args) throws SQLException {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/notice?serverTimezone=Asia/Shanghai",
                        "root",
                        "20000306")
                // 全局配置
                .globalConfig((scanner, builder) -> builder
                        .author("Catigeart")
                       // .enableSwagger()
                        .disableOpenDir()
                        .outputDir(System.getProperty("user.dir") + "/src/main/java"))
                // 包配置
                .packageConfig((scanner, builder) -> builder
                        .parent("pers.catigeart.notice")
                        .xml("mapper")
                        .pathInfo(Collections.singletonMap(
                                OutputFile.xml,
                                System.getProperty("user.dir") + "/src/main/resources/mapper")))
                // 策略配置
                .strategyConfig((scanner, builder) -> builder
                        .addInclude(scanner.apply("请输入表名，多个表名用,隔开"))
                        .entityBuilder()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .serviceBuilder()                                           // 切换至Service层设置
                        .formatServiceFileName("%sService")                         // 设定后缀名
                        .formatServiceImplFileName("%sServiceImpl"))
                // 设定后缀名)
                // 模板引擎配置
                .templateEngine(new FreemarkerTemplateEngine())
                // 执行
                .execute();
    }
}
