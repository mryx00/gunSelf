package com.stylefeng.gunSelf.modular.activiti.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.activiti.vo.DeploymentResponse;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Deploymodel;
import com.stylefeng.gunSelf.modular.activiti.service.IDeploymodelService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型控制器
 *
 * @author fengshuonan
 * @Date 2018-07-21 09:47:52
 */
@Controller
@RequestMapping("/deploymodel")
public class DeploymodelController extends BaseController {

    private String PREFIX = "/activiti/deploymodel/";

    @Autowired
    private IDeploymodelService deploymodelService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    ObjectMapper objectMapper;



    /**
     * 跳转到模型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "deploymodel.html";
    }

    /**
     * 跳转到添加模型
     */
    @RequestMapping("/deploymodel_add")
    public String deploymodelAdd() {
        return PREFIX + "deploymodel_add.html";
    }

    /**
     * 跳转到修改模型
     */
    @RequestMapping("/deploymodel_update/{deploymodelId}")
    public String deploymodelUpdate(@PathVariable Integer deploymodelId, Model model) {
        Deploymodel deploymodel = deploymodelService.selectById(deploymodelId);
        model.addAttribute("item",deploymodel);
        LogObjectHolder.me().set(deploymodel);
        return PREFIX + "deploymodel_edit.html";
    }

    /**
     * 获取模型列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Deployment> page = new PageFactory<Deployment>().defaultPage();
        List<Deployment> deployments = repositoryService.createDeploymentQuery()
                .listPage(page.getOffset(),page.getLimit());
        Page<DeploymentResponse> page2 = new PageFactory<DeploymentResponse>().defaultPage();
        int count = (int) repositoryService.createDeploymentQuery().count();
        List<DeploymentResponse> list = new ArrayList<>();
        for(Deployment deployment: deployments){
            list.add(new DeploymentResponse(deployment));
        }
        page2.setTotal(count);
        page2.setRecords(list);
        return super.packForBT(page2);
    }

    /**
     * 新增模型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Deploymodel deploymodel) {
        deploymodelService.insert(deploymodel);
        return SUCCESS_TIP;
    }

    /**
     * 删除模型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer deploymodelId) {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery().deploymentId(String.valueOf(deploymodelId)).list();
        if (ToolUtil.isNotEmpty(list)){
            for (ProcessInstance processInstance:list) {
                runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(),"删除");
            }
        }
        repositoryService.deleteDeployment(String.valueOf(deploymodelId));
        return SUCCESS_TIP;
    }

    /**
     * 修改模型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Deploymodel deploymodel) {
        deploymodelService.updateById(deploymodel);
        return SUCCESS_TIP;
    }

    /**
     * 启动部署的流程
     */
    @RequestMapping(value = "/start/{deploymodelId}")
    @ResponseBody
    public Object detail(@PathVariable("deploymodelId") Integer deploymodelId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(String.valueOf(deploymodelId)).singleResult();
        runtimeService.startProcessInstanceById(processDefinition.getId());
        return SUCCESS_TIP;
    }
}
