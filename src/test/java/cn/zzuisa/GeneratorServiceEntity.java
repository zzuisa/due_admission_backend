package cn.zzuisa;


import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

/**
 * <p>
 * 测试生成代码
 * </p>
 *
 * @author K神
 * @date 2017/12/18
 */
public class GeneratorServiceEntity {

	// 基础包名
	String basePackageName = "cn.zzuisa";
	// 文件名中要去掉的表前缀
	String excludePrefix = "zzu";
	// 模块和包名，与数据库中模块一致
	String moduleName = "";

	// 数据库连接信息
	String dbUrl = "jdbc:mysql://39.108.0.161:3306/diy?useUnicode=true&characterEncoding=UTF-8&tinyInt1isBit=false&useSSL=false";
	String dbUsername = "xbsql";
	String dbPassword = "mysql*5210";
	String dbDriverName = "com.mysql.jdbc.Driver";
	// 生成文件暂存目录
	String outputDir = "E:\\codeGen";//E:\\codeGen

    @Test
    public void generateCode() {
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, basePackageName);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
    	// 要生成的表前缀
    	String tablePrefix = excludePrefix + moduleName;

        GlobalConfig config = new GlobalConfig();
        // 数据源连接
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUrl(dbUrl)
                .setUsername(dbUsername)
                .setPassword(dbPassword)
                .setDriverName(dbDriverName);
        // 代码生成策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true).setRestControllerStyle(true) // 指定为rest
                .setEntityLombokModel(true) // 生成的实体类是否使用lombok模式
                .setNaming(NamingStrategy.underline_to_camel) // 下划线转驼峰
                .setTablePrefix(tablePrefix); // 表前缀
//                .setInclude(tableNames); //修改替换成你需要的表名，多个表名传数组；此处用上一行的表前缀来过滤，所以不用这个
        config.setActiveRecord(true)
                .setAuthor("zzuisa")
                .setOutputDir(outputDir)
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity("/zzu/zzuisa/entity.java");
        // 使用自定义的生成器
        new MyGenerator().prefixFilter(tablePrefix) // 过滤，只有表前缀和指定值一致，才会生成
		.setGlobalConfig(config)
        .setDataSource(dataSourceConfig)
        .setTemplateEngine(new FreemarkerTemplateEngine())
        .setStrategy(strategyConfig)
        .setTemplate(templateConfig)
        .setPackageInfo(
                new PackageConfig()
                        .setParent(packageName) // 包名
                        .setModuleName(moduleName)
                        .setController("controller")
                        .setEntity("entity")
        ).execute();
    }

}
