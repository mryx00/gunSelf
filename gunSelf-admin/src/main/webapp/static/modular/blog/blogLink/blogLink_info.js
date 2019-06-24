/**
 * 初始化博客外链接详情对话框
 */


var BlogLinkInfoDlg = {
    blogLinkInfoData : {},
    validateFields:{
            linkName:{
                     validators: {
                       notEmpty: {message:'链接名不能为空'}
                     }
             },
            linkAddr:{
                     validators: {
                       notEmpty: {message:'外链接地址不能为空'}
                     }
             },
            order:{
                     validators: {
                       notEmpty: {message:'排序不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogLinkInfoDlg.clearData = function() {
    this.blogLinkInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogLinkInfoDlg.set = function(key, val) {
    this.blogLinkInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogLinkInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogLinkInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogLink.layerIndex);
}

/**
 * 收集数据
 */
BlogLinkInfoDlg.collectData = function() {
    this
    .set('id')
    .set('linkName')
    .set('linkAddr')
    .set('account')
    .set('count')
    .set('createTime')
    .set('order');
}


 /**
  * 验证数据是否为空
  */
 BlogLinkInfoDlg.validate = function () {
     $('#blogLinkInfoForm').data("bootstrapValidator").resetForm();
     $('#blogLinkInfoForm').bootstrapValidator('validate');
     return $("#blogLinkInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogLinkInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogLink/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogLink.table.refresh();
        BlogLinkInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogLinkInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogLinkInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogLink/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogLink.table.refresh();
        BlogLinkInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogLinkInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogLinkInfoForm", BlogLinkInfoDlg.validateFields)

});
