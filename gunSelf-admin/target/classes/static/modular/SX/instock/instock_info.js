/**
 * 初始化入库详情对话框
 */


var InstockInfoDlg = {
    instockInfoData : {},
    validateFields:{
            productId:{
                     validators: {
                       notEmpty: {message:'产品名称不能为空'}
                     }
             },
            userId:{
                     validators: {
                       notEmpty: {message:'入库申请人不能为空'}
                     }
             },
            instockNum:{
                     validators: {
                       notEmpty: {message:'入库数量不能为空'}
                     }
             },
            instockTime:{
                     validators: {
                       notEmpty: {message:'入库时间不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
InstockInfoDlg.clearData = function() {
    this.instockInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InstockInfoDlg.set = function(key, val) {
    this.instockInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InstockInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
InstockInfoDlg.close = function() {
    parent.layer.close(window.parent.Instock.layerIndex);
}

/**
 * 收集数据
 */
InstockInfoDlg.collectData = function() {
    this
    .set('instockId')
    .set('productId')
    .set('userId')
    .set('description')
    .set('instockNum')
    .set('instockTime');
}


 /**
  * 验证数据是否为空
  */
 InstockInfoDlg.validate = function () {
     $('#instockInfoForm').data("bootstrapValidator").resetForm();
     $('#instockInfoForm').bootstrapValidator('validate');
     return $("#instockInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
InstockInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/instock/add", function(data){
        Feng.success("添加成功!");
        window.parent.Instock.table.refresh();
        InstockInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.instockInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
InstockInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/instock/update", function(data){
        Feng.success("修改成功!");
        window.parent.Instock.table.refresh();
        InstockInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.instockInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("instockInfoForm", InstockInfoDlg.validateFields)

});
