/**
 * 初始化模型详情对话框
 */


var DeploymodelInfoDlg = {
    deploymodelInfoData : {},
    validateFields:{
     }
};

/**
 * 清除数据
 */
DeploymodelInfoDlg.clearData = function() {
    this.deploymodelInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeploymodelInfoDlg.set = function(key, val) {
    this.deploymodelInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeploymodelInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeploymodelInfoDlg.close = function() {
    parent.layer.close(window.parent.Deploymodel.layerIndex);
}

/**
 * 收集数据
 */
DeploymodelInfoDlg.collectData = function() {
    this
    .set('deployId')
    .set('deployName')
    .set('deployTime');
}




/**
 * 提交添加
 */
DeploymodelInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deploymodel/add", function(data){
        Feng.success("添加成功!");
        window.parent.Deploymodel.table.refresh();
        DeploymodelInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deploymodelInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DeploymodelInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deploymodel/update", function(data){
        Feng.success("修改成功!");
        window.parent.Deploymodel.table.refresh();
        DeploymodelInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deploymodelInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("deploymodelInfoForm", DeploymodelInfoDlg.validateFields)

});
