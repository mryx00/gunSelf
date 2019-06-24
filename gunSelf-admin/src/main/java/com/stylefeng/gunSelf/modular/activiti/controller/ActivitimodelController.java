package com.stylefeng.gunSelf.modular.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.base.tips.MyTip;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Activitimodel;
import com.stylefeng.gunSelf.modular.activiti.service.IActivitimodelService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 模型控制器
 *
 * @author fengshuonan
 * @Date 2018-07-21 09:46:23
 */
@Controller
@RequestMapping("/activitimodel")
public class ActivitimodelController extends BaseController {

    private String PREFIX = "/activiti/activitimodel/";

    @Autowired
    private IActivitimodelService activitimodelService;

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ObjectMapper objectMapper;


    /**
     * 跳转到模型首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "activitimodel.html";
    }

    /**
     * 跳转到添加模型
     */
    @RequestMapping("/activitimodel_add")
    public String activitimodelAdd() throws UnsupportedEncodingException {
        //初始化一个空模型
        org.activiti.engine.repository.Model model = repositoryService.newModel();

        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));

        return "redirect:/static/modeler.html?modelId="+id;
    }

    /**
     * 跳转到修改模型
     */
    @RequestMapping("/activitimodel_update/{id}")
    public String activitimodelUpdate(@PathVariable Integer id, Model model) {
        return "redirect:/static/modeler.html?modelId="+id;
    }

    /**
     * 获取模型列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<org.activiti.engine.repository.Model> page = new PageFactory<org.activiti.engine.repository.Model>().defaultPage();
        List<org.activiti.engine.repository.Model> list = repositoryService.createModelQuery().listPage(page.getOffset()
                , page.getLimit());
        int count = (int) repositoryService.createModelQuery().count();
        page.setRecords(list);
        page.setTotal(count);
        return packForBT(page);
    }

    /**
     * 新增模型
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Activitimodel activitimodel) {
        activitimodelService.insert(activitimodel);
        return SUCCESS_TIP;
    }

    /**
     * 删除模型
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer activitimodelId) {
        repositoryService.deleteModel(String.valueOf(activitimodelId));
        return SUCCESS_TIP;
    }

    /**
     * 部署模型
     */
    @RequestMapping(value = "/deploy")
    @ResponseBody
    public Object deploy(@RequestParam Integer activitimodelId) throws IOException {
        //获取模型
        org.activiti.engine.repository.Model modelData = repositoryService.getModel(String.valueOf(activitimodelId));
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes == null) {
            return new MyTip(500,"模型数据为空，请先设计流程并成功保存，再进行发布。");
        }

        JsonNode modelNode = new ObjectMapper().readTree(bytes);

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if(model.getProcesses().size()==0){
            return new MyTip(500,"数据模型不符要求，请至少设计一条主线流程。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        return SUCCESS_TIP;
    }

    /**
     * 修改模型
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Activitimodel activitimodel) {
        activitimodelService.updateById(activitimodel);
        return SUCCESS_TIP;
    }

    /**
     * 模型详情
     */
    @RequestMapping(value = "/detail/{activitimodelId}")
    @ResponseBody
    public Object detail(@PathVariable("activitimodelId") Integer activitimodelId) {
        return activitimodelService.selectById(activitimodelId);
    }
}
