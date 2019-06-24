package com.stylefeng.gunSelf.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.stylefeng.gunSelf.config.properties.ActivitiProperties;
import com.stylefeng.gunSelf.core.datasource.DruidProperties;

import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {

    @Autowired
    DruidProperties druidProperties;

    @Autowired
    ActivitiProperties activitiProperties;

    public static Logger logger = Logger.getLogger(ActivitiConfig.class);

    /**
     * activiti单独的数据源
     */
    private DruidDataSource activitiDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        activitiProperties.config(dataSource);
        return dataSource;
    }
    //注入数据源和事务管理器
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(
             PlatformTransactionManager transactionManager,
            SpringAsyncExecutor springAsyncExecutor) throws IOException {
        logger.info("activiti启动");
        return this.baseSpringProcessEngineConfiguration(activitiDataSource(), transactionManager, springAsyncExecutor);

    }

}
