package com.stylefeng.gunSelf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(exclude ={
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
} )
//@ComponentScan(basePackages = {"com.zhangyeyuan.test.modular","com.stylefeng.gunSelf"})
//
//@MapperScan("com.zhangyeyuan.test.modular.system.dao")
//@EnableAutoConfiguration(exclude = {
//        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
//})
public class GunSelfApplication {

    private final static Logger logger = LoggerFactory.getLogger(GunSelfApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GunSelfApplication.class, args);

        logger.info("gunSelf is success!");
    }
}
