/**
 * 初始化博客标签详情对话框
 */


var BlogTagInfoDlg = {
    blogTagInfoData : {},
    validateFields:{
            tagName:{
                     validators: {
                       notEmpty: {message:'标签名不能为空'}
                     }
             },
            account:{
                     validators: {
                       notEmpty: {message:'用户不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogTagInfoDlg.clearData = function() {
    this.blogTagInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogTagInfoDlg.set = function(key, val) {
    this.blogTagInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogTagInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogTagInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogTag.layerIndex);
}

/**
 * 收集数据
 */
BlogTagInfoDlg.collectData = function() {
    this
    .set('id')
    .set('tagName')
    .set('count')
    .set('creatTime')
    .set('account');
}


 /**
  * 验证数据是否为空
  */
 BlogTagInfoDlg.validate = function () {
     $('#blogTagInfoForm').data("bootstrapValidator").resetForm();
     $('#blogTagInfoForm').bootstrapValidator('validate');
     return $("#blogTagInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogTagInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogTag/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogTag.table.refresh();
        BlogTagInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogTagInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogTagInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogTag/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogTag.table.refresh();
        BlogTagInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogTagInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogTagInfoForm", BlogTagInfoDlg.validateFields)

});
