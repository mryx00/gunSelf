package com.stylefeng.gunSelf.generator.action.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.stylefeng.gunSelf.core.cache.CacheKit;
import com.stylefeng.gunSelf.core.util.FileUtil;
import com.stylefeng.gunSelf.generator.action.model.MyTableField;
import com.stylefeng.gunSelf.generator.action.model.TableFieldsVo;
import com.stylefeng.gunSelf.generator.engine.SimpleTemplateEngine;
import com.stylefeng.gunSelf.generator.engine.base.FadpTemplateEngine;
import com.stylefeng.gunSelf.generator.engine.config.ContextConfig;
import com.stylefeng.gunSelf.generator.engine.config.SqlConfig;

import java.io.File;
import java.util.List;

/**
 * 代码生成的抽象配置
 *
 * @author fengshuonan
 * @date 2017-10-28-下午8:22
 */
public abstract class AbstractGeneratorConfig {

    /**
     * mybatis-plus代码生成器配置
     */
    GlobalConfig globalConfig = new GlobalConfig();

    DataSourceConfig dataSourceConfig = new DataSourceConfig();

    StrategyConfig strategyConfig = new StrategyConfig();

    PackageConfig packageConfig = new PackageConfig();

    TemplateConfig templateConfig = new TemplateConfig();



    TableInfo tableInfo = null;

    /*表的主键信息*/
    TableField keyField = null;

    /*字段封装后的信息*/
    List<MyTableField> myTableFields = null;


    /**
     * fadp代码生成器配置
     */
    ContextConfig contextConfig = new ContextConfig();

    SqlConfig sqlConfig = new SqlConfig();

    protected abstract void config();

    public void init() {
        config();

        packageConfig.setService(contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service");
        packageConfig.setServiceImpl(contextConfig.getProPackage() + ".modular." + contextConfig.getModuleName() + ".service.impl");

        //controller没用掉,生成之后会自动删掉
        packageConfig.setController("TTT");

        if (!contextConfig.getEntitySwitch()) {
            packageConfig.setEntity("TTT");
        }

        if (!contextConfig.getDaoSwitch()) {
            packageConfig.setMapper("TTT");
            packageConfig.setXml("TTT");
        }

        if (!contextConfig.getServiceSwitch()) {
            packageConfig.setService("TTT");
            packageConfig.setServiceImpl("TTT");
        }



    }

    /**
     * 删除不必要的代码
     */
    public void destory() {
        String outputDir = globalConfig.getOutputDir() + "/TTT";
        FileUtil.deleteDir(new File(outputDir));
    }

    public AbstractGeneratorConfig() {
    }

    public void doMpGeneration() {
        init();
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setPackageInfo(packageConfig);
        //自定义了实体生成文件
         autoGenerator.setTemplate(templateConfig);
        autoGenerator.execute();
        destory();

        //获取table信息,用于fadp代码生成
        List<TableInfo> tableInfoList = autoGenerator.getConfig().getTableInfoList();
        if (tableInfoList != null && tableInfoList.size() > 0) {
            this.tableInfo = tableInfoList.get(0);
        }

        //从缓冲中读取具体的字段配置信息
        String tableName = this.tableInfo.getName();
        TableFieldsVo tableFieldsVo = CacheKit.get(tableName, tableName);
        if (tableFieldsVo!=null){
            System.out.println("读取缓存成功");
            this.myTableFields = tableFieldsVo.getTableFields();
        }else{
            System.out.println("读取缓存失败");
        }

        //遍历出table的主键,用于js代码的生成
        List<TableField> fields = this.tableInfo.getFields();
        for (TableField field:fields) {
                if (field.isKeyFlag()){
                    keyField = field;
                }
        }


    }

    public void doFadpGeneration() {
        FadpTemplateEngine fadpTemplateEngine = new SimpleTemplateEngine();
        fadpTemplateEngine.setContextConfig(contextConfig);
        sqlConfig.setConnection(dataSourceConfig.getConn());
        fadpTemplateEngine.setSqlConfig(sqlConfig);

        fadpTemplateEngine.setTableInfo(tableInfo);
        //设置表格的主键信息
        fadpTemplateEngine.setKeyField(keyField);
        //设置封装的字段信息
        fadpTemplateEngine.setMyTableFields(myTableFields);

        fadpTemplateEngine.start();
    }
}
