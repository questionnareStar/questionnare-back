package generator;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中，逗号分割
 */
public class CodeGenerator {

    /**
     * 生成的代码放到哪个工程中
     */
    public static final String PROJECT_NAME = "questionnaire-generator";

    /**
     * 数据库名称
     */
    public static final String DATABASE_NAME = "questionnaire";

    /**
     * 子包名
     */
    public static final String MODULE_NAME = "modules.question";

    /**
     * 去掉表前缀
     */
    public static final String REMOVE_TABLE_PREFIX = "";

    /**
     * ip地址
     */
    public static final String MY_IP = "47.93.216.213";

    /**
     * 端口号
     */
    public static final String MY_PORT = "3306";

    /**
     * 账号
     */
    public static final String MY_USERNAME = "root";

    /**
     * 密码
     */
    public static final String MY_PASSWORD = "Ques@123456";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://" + MY_IP + ":" + MY_PORT + "/" + DATABASE_NAME + "?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(MY_USERNAME);
        dsc.setPassword(MY_PASSWORD);
        mpg.setDataSource(dsc);

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/";
        gc.setOutputDir(projectPath + PROJECT_NAME + "/src/main/java");
        // 分布式id
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setAuthor("问卷星球团队");
        // 覆盖现有的
        gc.setFileOverride(true);
        // 是否生成后打开
        gc.setOpen(false);
        gc.setDateType(DateType.ONLY_DATE);
        // 实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包名
        pc.setParent("com.questionnaire");
        // com.question.aritcle.controller
        pc.setController(MODULE_NAME + ".controller");
        pc.setService(MODULE_NAME + ".service");
        pc.setServiceImpl(MODULE_NAME + ".service.impl");
        pc.setMapper(MODULE_NAME + ".mapper");
        //实体类存储包名 com.question.entities
        pc.setEntity("entities");
        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //使用lombok
        strategy.setEntityLombokModel(true);
        // 实体类的实现接口Serializable
        strategy.setEntitySerialVersionUID(true);
        // @RestController
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        // 去掉表前缀
        strategy.setTablePrefix(REMOVE_TABLE_PREFIX);
        mpg.setStrategy(strategy);

        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入").append(tip).append("：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}