/**
 * 初始化模型详情对话框
 */


var ActivitimodelInfoDlg = {
    activitimodelInfoData : {},
    validateFields:{
     }
};

/**
 * 清除数据
 */
ActivitimodelInfoDlg.clearData = function() {
    this.activitimodelInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivitimodelInfoDlg.set = function(key, val) {
    this.activitimodelInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivitimodelInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ActivitimodelInfoDlg.close = function() {
    parent.layer.close(window.parent.Activitimodel.layerIndex);
}

/**
 * 收集数据
 */
ActivitimodelInfoDlg.collectData = function() {
    this
    .set('modelId')
    .set('modelName')
    .set('version')
    .set('createTime');
}




/**
 * 提交添加
 */
ActivitimodelInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activitimodel/add", function(data){
        Feng.success("添加成功!");
        window.parent.Activitimodel.table.refresh();
        ActivitimodelInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activitimodelInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ActivitimodelInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activitimodel/update", function(data){
        Feng.success("修改成功!");
        window.parent.Activitimodel.table.refresh();
        ActivitimodelInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activitimodelInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("activitimodelInfoForm", ActivitimodelInfoDlg.validateFields)

});
