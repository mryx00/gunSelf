package com.stylefeng.gunSelf.generator.action;


import com.stylefeng.gunSelf.generator.action.config.FadpGeneratorConfig;

/**
 * 代码生成器,可以生成实体,dao,service,controller,html,js
 *
 * @author stylefeng
 * @Date 2017/5/21 12:38
 */
public class FadpCodeGenerator {

    public static void main(String[] args) {

        /**
         * Mybatis-Plus的代码生成器:
         *      mp的代码生成器可以生成实体,mapper,mapper对应的xml,service
         */
        FadpGeneratorConfig fadpGeneratorConfig = new FadpGeneratorConfig();
        fadpGeneratorConfig.doMpGeneration();

        /**
         * Fadp的生成器:
         *      Fadp的代码生成器可以生成controller,html页面,页面对应的js
         */
        fadpGeneratorConfig.doFadpGeneration();
    }

}