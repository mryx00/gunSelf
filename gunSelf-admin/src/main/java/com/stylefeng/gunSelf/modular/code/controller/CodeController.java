package com.stylefeng.gunSelf.modular.code.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.cache.CacheKit;
import com.stylefeng.gunSelf.core.datasource.DruidProperties;
import com.stylefeng.gunSelf.generator.action.config.WebGeneratorConfig;
import com.stylefeng.gunSelf.generator.action.model.GenQo;
import com.stylefeng.gunSelf.generator.action.model.TableFieldsVo;
import com.stylefeng.gunSelf.modular.code.factory.DefaultTemplateFactory;
import com.stylefeng.gunSelf.modular.code.model.*;
import com.stylefeng.gunSelf.modular.code.service.TableService;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_ID;
import static org.activiti.editor.constants.ModelDataJsonConstants.MODEL_NAME;

/**
 * 代码生成控制器
 *
 * @author fengshuonan
 * @Date 2017年11月30日16:39:19
 */
@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private static String PREFIX = "/code";

    protected static final Logger LOGGER = LoggerFactory.getLogger(CodeController.class);

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TableService tableService;

    @Autowired
    private DruidProperties druidProperties;

    GlobalConfig globalConfig = new GlobalConfig();

    DataSourceConfig dataSourceConfig = new DataSourceConfig();

    StrategyConfig strategyConfig = new StrategyConfig();

    PackageConfig packageConfig = new PackageConfig();

    /**
     * 跳转到代码生成主页
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        model.addAttribute("tables", tableService.getAllTables());
        model.addAttribute("params", DefaultTemplateFactory.getDefaultParams());
        model.addAttribute("templates", DefaultTemplateFactory.getDefaultTemplates());
        return PREFIX + "/code.html";
    }

    @RequestMapping("tableFieldSetting/{tableName}")
    public String tableFieldSetting(@PathVariable("tableName") String tableName, Model model){
        GenQo genQo = new GenQo();
        genQo.setUrl(druidProperties.getUrl());
        genQo.setUserName(druidProperties.getUsername());
        genQo.setPassword(druidProperties.getPassword());
        genQo.setTableName(tableName);
        MyWebGerneratorConfig myWebGerneratorConfig = new MyWebGerneratorConfig(genQo);
        myWebGerneratorConfig.excute();
        //获取table信息,用于guns代码生成
        TableInfo tableInfo = myWebGerneratorConfig.getTableInfo();
        List<TableField> tableInfoFields = tableInfo.getFields();
        model.addAttribute("fields",tableInfoFields);
        model.addAttribute("tableName",tableName);
       // System.out.println(tableInfo);
        return PREFIX + "/editField.html";
    }

    /**
     * 生成代码
     */
    @ApiOperation("生成代码")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    public Object generate(GenQo genQo) {
        genQo.setUrl(druidProperties.getUrl());
        genQo.setUserName(druidProperties.getUsername());
        genQo.setPassword(druidProperties.getPassword());
        WebGeneratorConfig webGeneratorConfig = new WebGeneratorConfig(genQo);
        webGeneratorConfig.doMpGeneration();
        webGeneratorConfig.doFadpGeneration();
        return SUCCESS_TIP;
    }

    /**
     * 生成自定义的表单字段
     */
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Object test(TableFieldsVo tableFieldsVo){
        /*将表单的样式和字段放入缓存中*/
        CacheKit.put(tableFieldsVo.getTableName(),tableFieldsVo.getTableName(),tableFieldsVo);
        return SUCCESS_TIP;
    }

    @ApiOperation("测试json")
    @RequestMapping(value = "/test1", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Object test(){

        ObjectNode modelNode = null;

        org.activiti.engine.repository.Model model = repositoryService.getModel("30001");

        if (model != null) {
            try {
                if (StringUtils.isNotEmpty(model.getMetaInfo())) {
                    modelNode = (ObjectNode) objectMapper.readTree(model.getMetaInfo());
                } else {
                    modelNode = objectMapper.createObjectNode();
                    modelNode.put(MODEL_NAME, model.getName());
                }
                modelNode.put(MODEL_ID, model.getId());
                ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(
                        new String(repositoryService.getModelEditorSource(model.getId()), "utf-8"));
                modelNode.put("model", editorJsonNode);

            } catch (Exception e) {
                LOGGER.error("Error creating model JSON", e);
                throw new ActivitiException("Error creating model JSON", e);
            }
        }
        return JSON.parseObject(modelNode.toString());
       //return SUCCESS_TIP;
    }
}
